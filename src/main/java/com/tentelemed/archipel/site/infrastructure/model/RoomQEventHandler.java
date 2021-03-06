package com.tentelemed.archipel.site.infrastructure.model;

import com.tentelemed.archipel.site.domain.pub.*;
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
        room.getBeds().add(evt.getBed());
    }

    @Override
    public void handle(EvtRoomBedRemoved evt) {
        room.getBeds().remove(evt.getBed());
    }

    @Override
    public void handle(EvtRoomRegistered event) {
        room.setId(event.getId().getId());
        room.setMedical(event.isMedical());
        room.setSiteId(event.getSiteId());
        room.setName(event.getName());
        room.setLocationPath(event.getLocationPath().toString());
    }

    @Override
    public void handle(EvtRoomUpdated evt) {
        // TODO
    }

}
