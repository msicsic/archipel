package com.tentelemed.archipel.medicalcenter.domain.event;


import com.tentelemed.archipel.medicalcenter.domain.model.MedicalCenterInfo;
import com.tentelemed.archipel.medicalcenter.domain.model.RoomId;

/**
 * Created with IntelliJ IDEA.
 * User: Mael
 * Date: 10/12/13
 * Time: 14:53
 */
public class MedicalCenterRoomAdded extends MedicalCenterDomainEvent {
    RoomId roomId;

    MedicalCenterRoomAdded() {}

    public MedicalCenterRoomAdded(RoomId roomId) {
        this.roomId = roomId;
    }

    public RoomId getRoomId() {
        return roomId;
    }
}
