package com.tentelemed.archipel.medicalcenter.domain.event;


import com.tentelemed.archipel.medicalcenter.domain.model.MedicalCenterId;
import com.tentelemed.archipel.medicalcenter.domain.model.MedicalCenterInfo;

/**
 * Created with IntelliJ IDEA.
 * User: Mael
 * Date: 10/12/13
 * Time: 14:53
 */
public class MedicalCenterAdditionalInfoUpdated extends MedicalCenterDomainEvent {

    private MedicalCenterInfo info;

    public MedicalCenterAdditionalInfoUpdated(MedicalCenterId id, MedicalCenterInfo info) {
        super(id);
        this.info = info;
    }

    public MedicalCenterInfo getInfo() {
        return info;
    }
}
