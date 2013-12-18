package com.tentelemed.archipel.medicalcenter.domain.event;

/**
 * Created with IntelliJ IDEA.
 * User: Mael
 * Date: 10/12/13
 * Time: 15:01
 */
public interface MedicalCenterEventHandler {
    void handle(MedicalCenterRegistered event);

    void handle(MedicalCenterMainInfoUpdated event);

    void handle(MedicalCenterAdditionalInfoUpdated event);

    void handle(MedicalCenterRoomAdded event);

    void handle(MedicalCenterRoomRemoved event);

    void handle(MedicalCenterDeleted event);

    void handle(MedicalCenterServiceAdded event);
}
