package com.tentelemed.archipel.core.domain.model;

import com.tentelemed.archipel.core.application.event.AbstractDomainEvent;
import com.tentelemed.archipel.core.application.event.DomainEvent;
import com.tentelemed.archipel.core.application.service.CmdRes;
import com.tentelemed.archipel.core.infrastructure.model.EventUtil;

import java.lang.reflect.Method;
import java.util.Arrays;

/**
 * Created with IntelliJ IDEA.
 * User: Mael
 * Date: 22/10/13
 * Time: 11:25
 */
public abstract class BaseAggregateRoot<B extends EntityId> extends BaseEntity {
    protected Integer id;
    private Long version = 0L;

    private transient B entityId;

    public B getEntityId() {
        if (entityId == null && id != null || entityId != null && entityId.getId() != id) {
            try {
                entityId = getIdClass().newInstance();
                entityId.setId(id);
            } catch (InstantiationException | IllegalAccessException e) {
                throw new RuntimeException("Cannot instanciate entityId : \"" + getIdClass().getSimpleName() + "\", please provide an empty constructor");
            }
        }
        return entityId;
    }

    protected abstract Class<B> getIdClass();

    protected <E extends AbstractDomainEvent> E handled(E event) {
        event.processed = true;
        return event;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (!(o instanceof BaseEntity)) return false;

        BaseAggregateRoot that = (BaseAggregateRoot) o;

        if (id != null ? !id.equals(that.id) : that.id != null) return false;

        return true;
    }

    @Override
    public int hashCode() {
        return id != null ? id.hashCode() : 0;
    }

    public Long getVersion() {
        return version;
    }

    /**
     * Applique les evts passés en parametre dans une liste en retour, en
     * appliquant prealablement les evts sur l'agregat
     *
     * @param events evts de modification d'etat
     * @return liste des evts passés en param
     */
    protected CmdRes _result(AbstractDomainEvent... events) {
        /*for (DomainEvent event : events) {
            handle(event);
        }*/
        if (events == null || events.length == 0) {
            throw new RuntimeException("'handle' must return at least one event : "+getClass().getSimpleName());
        }
        for (AbstractDomainEvent event : events) {
            if (event == null) {
                throw new RuntimeException("'handle' cannot return null : "+getClass().getSimpleName());
            }
            if (! event.processed) {
                throw new RuntimeException("Only processed events can be returned by a command : " + event);
            }
        }
        return new CmdRes(this, Arrays.asList(events));
    }

    public void _setId(Integer id) {
        this.id = id;
    }

    <M extends DomainEvent> M handle(M event) {
        try {
            Method method = getClass().getMethod("handle", event.getClass());
            method.invoke(this, event);
            // TODO : ajouter un memento de l'objet avant et apres application de l'evt
            // ceci afin de faciliter les operations de persistence et de suivi des modifs
            return event;
        } catch (Exception e) {
            log.error(null, e);
            throw new RuntimeException("Cannot apply event on aggregate : " + event.getClass().getSimpleName());
        }
    }

    protected <E extends AbstractDomainEvent> E apply(E event) {
        EventUtil.autoApply(this, event);
        return handled(event);
    }

    public Memento createMemento() {
        return MementoUtil.createMemento(this);
    }

//    public void applyMemento(Memento memento) {
//        MementoUtil._applyMemento(memento, this);
//    }

}
