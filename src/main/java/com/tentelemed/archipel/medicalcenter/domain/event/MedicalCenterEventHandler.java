package com.tentelemed.archipel.medicalcenter.domain.event;

/**
 * Created with IntelliJ IDEA.
 * User: Mael
 * Date: 10/12/13
 * Time: 15:01
 */
public interface MedicalCenterEventHandler {
    void handle(MedicalCenterRegistered event);
    void handle(MedicalCenterUpdated event);
    void handle(MedicalCenterRoomAdded event);
    void handle(MedicalCenterRoomRemoved event);
}
