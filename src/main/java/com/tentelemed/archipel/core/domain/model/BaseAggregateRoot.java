package com.tentelemed.archipel.core.domain.model;

import com.tentelemed.archipel.core.application.event.DomainEvent;

import java.lang.reflect.Method;
import java.util.Arrays;
import java.util.List;

/**
 * Created with IntelliJ IDEA.
 * User: Mael
 * Date: 22/10/13
 * Time: 11:25
 */
public abstract class BaseAggregateRoot<M extends EntityId> extends BaseEntity<M> {

    /**
     * Applique les evts passés en parametre dans une liste en retour, en
     * appliquant prealablement les evts sur l'agregat
     *
     * @param events evts de modification d'etat
     * @return liste des evts passés en param
     */
    protected List<DomainEvent> list(DomainEvent... events) {
        for (DomainEvent event : events) {
            handle(event);
        }
        return Arrays.asList(events);
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
            return null;
        }
    }

    public Memento createMemento() {
        return MementoUtil.createMemento(this);
    }

//    public void applyMemento(Memento memento) {
//        MementoUtil._applyMemento(memento, this);
//    }

}
