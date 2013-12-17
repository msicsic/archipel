package com.tentelemed.archipel.medicalcenter.domain.event;


import com.tentelemed.archipel.medicalcenter.domain.model.MedicalCenterId;

/**
 * Created with IntelliJ IDEA.
 * User: Mael
 * Date: 10/12/13
 * Time: 14:53
 */
public class MedicalCenterDeleted extends MedicalCenterDomainEvent {
    MedicalCenterDeleted() {
    }

    @Override
    public Type getCrudType() {
        return Type.DELETE;
    }

    public MedicalCenterDeleted(MedicalCenterId id) {
        super(id);
    }

}
