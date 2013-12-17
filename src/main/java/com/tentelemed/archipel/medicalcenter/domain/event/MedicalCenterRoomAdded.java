package com.tentelemed.archipel.medicalcenter.domain.event;


import com.tentelemed.archipel.medicalcenter.domain.model.MedicalCenterId;
import com.tentelemed.archipel.medicalcenter.domain.model.RoomId;

/**
 * Created with IntelliJ IDEA.
 * User: Mael
 * Date: 10/12/13
 * Time: 14:53
 */
public class MedicalCenterRoomAdded extends MedicalCenterDomainEvent {
    RoomId roomId;

    MedicalCenterRoomAdded() {
    }

    public MedicalCenterRoomAdded(MedicalCenterId id, RoomId roomId) {
        super(id);
        this.roomId = roomId;
    }

    public RoomId getRoomId() {
        return roomId;
    }
}
