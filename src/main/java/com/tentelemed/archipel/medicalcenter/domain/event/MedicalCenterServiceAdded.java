package com.tentelemed.archipel.medicalcenter.domain.event;


import com.tentelemed.archipel.medicalcenter.domain.model.MedicalCenterId;
import com.tentelemed.archipel.medicalcenter.domain.model.RoomId;
import com.tentelemed.archipel.medicalcenter.domain.model.Service;

/**
 * Created with IntelliJ IDEA.
 * User: Mael
 * Date: 10/12/13
 * Time: 14:53
 */
public class MedicalCenterServiceAdded extends MedicalCenterDomainEvent {
    Service service;

    MedicalCenterServiceAdded() {
    }

    public MedicalCenterServiceAdded(MedicalCenterId id, Service service) {
        super(id);
        this.service = service;
    }

    public Service getService() {
        return service;
    }
}
