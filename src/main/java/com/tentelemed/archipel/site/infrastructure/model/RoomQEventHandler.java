package com.tentelemed.archipel.site.infrastructure.model;

import com.tentelemed.archipel.site.domain.event.*;
import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Component;

/**
 * Created with IntelliJ IDEA.
 * User: Mael
 * Date: 06/01/14
 * Time: 15:30
 */
@Component
@Scope("prototype")
public class RoomQEventHandler implements RoomEventHandler {
    RoomQ room;

    public void setObject(Object object) {
        this.room = (RoomQ) object;
    }

    @Override
    public void handle(EvtRoomBedAdded evt) {
        // TODO
    }

    @Override
    public void handle(EvtRoomBedRemoved evt) {
        // TODO
    }

    @Override
    public void handle(EvtRoomRegistered event) {
        room.setLocationCode(event.getLocation().getCode());
        room.setBeds(event.getBeds());
        room.setMedical(event.isMedical());
        room.setName(event.getName());
    }

    @Override
    public void handle(EvtRoomUpdated evt) {
        // TODO
    }

}
