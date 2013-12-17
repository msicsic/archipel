package com.tentelemed.archipel.core.application;

import com.tentelemed.archipel.core.application.service.CmdRes;
import com.tentelemed.archipel.core.domain.model.BaseAggregateRoot;
import com.tentelemed.archipel.core.domain.model.EntityId;

/**
 * Created with IntelliJ IDEA.
 * User: Mael
 * Date: 11/12/13
 * Time: 14:57
 */
public interface EventStore {
    /**
     * Retourne un objet présent dans le store
     *
     * @param id
     * @param <M>
     * @return
     */
    <M extends EntityId> BaseAggregateRoot<M> get(M id);

    /**
     * Instancie un nouvel Aggregate et lui attribu un id
     *
     * @param aggregateClass
     * @param <M>
     * @return
     */
    <M extends BaseAggregateRoot> M get(Class<M> aggregateClass);

    void handleEvents(CmdRes res);
//    void handleEvents(BaseAggregateRoot target, Collection<DomainEvent> events);

    long getMaxAggregateId();
}
