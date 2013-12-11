package com.tentelemed.archipel.core.infrastructure.repo;

import com.google.common.eventbus.DeadEvent;
import com.google.common.eventbus.EventBus;
import com.google.common.eventbus.Subscribe;
import com.tentelemed.archipel.core.application.EventRegistry;
import com.tentelemed.archipel.core.application.EventStore;
import com.tentelemed.archipel.core.application.event.DomainEvent;
import com.tentelemed.archipel.core.application.service.EventHandler;
import com.tentelemed.archipel.core.domain.model.BaseAggregateRoot;
import com.tentelemed.archipel.core.domain.model.EntityId;
import com.tentelemed.archipel.core.domain.model.Memento;
import com.tentelemed.archipel.core.domain.model.MementoUtil;
import com.tentelemed.archipel.security.application.event.UserRegistered;
import com.tentelemed.archipel.security.domain.model.User;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Component;

import javax.annotation.PostConstruct;
import java.lang.reflect.Constructor;
import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import java.util.ArrayList;
import java.util.Collection;
import java.util.List;

/**
 * Created with IntelliJ IDEA.
 * User: Mael
 * Date: 15/11/13
 * Time: 16:55
 */
//@EventHandler("storeEventBus")
@Component
public class EventStoreImpl implements EventStore {
    private static final Logger log = LoggerFactory.getLogger(EventStoreImpl.class);

    @Autowired JdbcTemplate jdbcTemplate;
    @Autowired EventBus eventBus;
    @Autowired @Qualifier("storeEventBus") EventBus storeEventBus;
    @Autowired EventRegistry eventRegistry;

    @PostConstruct
    void onConstruct() {
        storeEventBus.register(this);
    }

    @Override
    public <M extends BaseAggregateRoot> M get(Class<M> c) {
        Constructor cst = null;
        try {
            cst = c.getDeclaredConstructor();
            cst.setAccessible(true);
            M res = (M) cst.newInstance();
            res._setId(nextId());
            return res;
        } catch (Exception e) {
            log.error(null, e);
            throw new RuntimeException("Cannot instanciate Aggregate : "+c.getSimpleName());
        } finally {
            if (cst != null) {
                cst.setAccessible(false);
            }
        }
    }

    long count = -1;

    private void initCount() {
        if (count == -1) {
            count = getMaxAggregateId();
        }
    }

    private synchronized Integer nextId() {
        initCount();
        return (int)(count++);
    }

    @Override
    public <M extends EntityId> BaseAggregateRoot<M> get(M id) {
        List<String> eventStrings = jdbcTemplate.queryForList("select e.c_data from T_EVENTS e where e.c_aggregate_id=? order by c_version", String.class, id.getId());
        if (eventStrings == null || eventStrings.size() == 0) return null;
        List<DomainEvent> events = new ArrayList<>();
        for (String s : eventStrings) {
            Memento memento = MementoUtil.mementoFromString(s);
            DomainEvent event = (DomainEvent) MementoUtil.instanciateFromMemento(memento);
            events.add(event);
        }

        DomainEvent firstEvent = events.get(0);
        if (!(firstEvent.isCreate())) {
            throw new RuntimeException("First event must be CreateEvent");
        }
        BaseAggregateRoot target = eventRegistry.newAggregateForEvent(firstEvent);
        applyEvents(target, events);
        return target;
    }

    protected BaseAggregateRoot applyEvents(BaseAggregateRoot aggregate, Collection<? extends DomainEvent> events) {
        for (DomainEvent event : events) {
            if (event.isDelete()) {
                return null;
            }
            try {
                Method m = aggregate.getClass().getMethod("handle", new Class[]{event.getClass()});
                if (event.isCreate()) {
                    aggregate._setId(event.getAggregateId().getId());
                }
                m.invoke(aggregate, event);
            } catch (InvocationTargetException e) {
                throw new RuntimeException((e.getTargetException()));
            } catch (Exception e) {
                log.error("Cant find event handler method : " + aggregate.getClass().getSimpleName() + "." + event.getClass().getSimpleName(), e);
                throw new RuntimeException(e);
            }
        }
        return aggregate;
    }

    /**
     * - Persiste les evts dans le Store
     * - Propage sur le bus du store (pour la partie Query)
     * - Propage sur le bus principal (pour les sous systemes)
     *
     * @param target agregat deja modifié
     * @param events les evts qui ont modifiés l'agregat
     */
    @Override
    public void handleEvents(BaseAggregateRoot target, Collection<DomainEvent> events) {
        // maj de l'agregat
        //Class<? extends BaseAggregateRoot> clazz = target.getClass();
        //EntityId id = target.getEntityId();
        // target = applyEvents(target, events);

        // recup de la derniere version
        Long version = jdbcTemplate.queryForObject("select max(e.c_version) from T_EVENTS e where e.c_aggregate_id=?", Long.class, target.getEntityId().getId());
        if (version == null) {
            // enregistrer l'agregat
            version = 0L;
            jdbcTemplate.update("insert into T_AGGREGATE values (?,?,?)", target.getEntityId().getId(), target.getClass().getName(), version + events.size() - 1);
        } else {
            version++;
            jdbcTemplate.update("update T_AGGREGATE set c_version=? where c_aggregate_id=?", version + events.size() - 1, target.getEntityId().getId());
        }

        // ajout des evts dans le store
        for (DomainEvent event : events) {
            //addEvent(event);
            Memento memento = MementoUtil.createMemento(event);
            String serial = MementoUtil.mementoToString(memento);
            jdbcTemplate.update("insert into T_EVENTS values (?,?,?)", event.getAggregateId().getId(), serial, version++);
        }

        // propagation aux QueryManagers (pour maj des bdd de consultation)
        for (DomainEvent event : events) {
            failed.set(Boolean.FALSE);
            storeEventBus.post(event);
            if (failed.get()) {
                throw new RuntimeException("Event without persistence handler : " + event.getClass().getSimpleName());
            }
        }

        // propagation aux listeners d'evts (les autres modules principalement)
        for (DomainEvent event : events) {
            eventBus.post(event);
        }

    }

    ThreadLocal<Boolean> failed = new ThreadLocal<>();

    @Subscribe
    private void handle(DeadEvent event) {
        failed.set(Boolean.TRUE);
    }

    @Override
    public long getMaxAggregateId() {
        Long res = jdbcTemplate.queryForObject("select max(c_aggregate_id) from T_AGGREGATE", Long.class);
        if (res == null) return 0;
        return res;
    }
}