package com.tentelemed.archipel.core.application;

import com.tentelemed.archipel.core.domain.model.BaseAggregateRoot;
import com.tentelemed.archipel.core.domain.model.EntityId;
import com.tentelemed.archipel.core.domain.pub.DomainEvent;

import java.util.Collection;

/**
 * Created with IntelliJ IDEA.
 * User: Mael
 * Date: 18/11/13
 * Time: 12:12
 */
public class QueryUpdateEvent {

    /**
     * id de l'agregat modifié
     */
    public final EntityId id;

    /**
     * agregat dans sa version deja modifiée
     */
    public final BaseAggregateRoot aggregate;

    /**
     * liste des evts de modification sur l'agregat
     */
    public final Collection<? extends DomainEvent> events;

    /**
     * type de l'agregat (utile dans le cas ou l'agregat est null, comme dans le cas d'une suppression
     */
    //public final Class<? extends BaseAggregateRoot> clazz;

    // Class<? extends BaseAggregateRoot> clazz
    public QueryUpdateEvent(EntityId id, BaseAggregateRoot aggregate, Collection<? extends DomainEvent> events) {
        //this.clazz = clazz;
        this.id = id;
        this.aggregate = aggregate;
        this.events = events;
    }
}
