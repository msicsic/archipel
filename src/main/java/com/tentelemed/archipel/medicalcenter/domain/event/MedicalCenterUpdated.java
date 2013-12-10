package com.tentelemed.archipel.medicalcenter.domain.event;


import com.tentelemed.archipel.core.application.event.DomainEvent;
import com.tentelemed.archipel.medicalcenter.domain.model.Division;
import com.tentelemed.archipel.medicalcenter.domain.model.MedicalCenter;
import com.tentelemed.archipel.medicalcenter.domain.model.MedicalCenterInfo;

/**
 * Created with IntelliJ IDEA.
 * User: Mael
 * Date: 10/12/13
 * Time: 14:53
 */
public class MedicalCenterUpdated extends MedicalCenterRegistered {

    public MedicalCenterUpdated(MedicalCenter.Type type, String name, String ident, Division division, MedicalCenterInfo info) {
        this.type = type;
        this.name = name;
        this.ident = ident;
        this.division = division;
        this.info = info;
    }

    @Override
    public Type getCrudType() {
        return Type.UPDATE;
    }
}
