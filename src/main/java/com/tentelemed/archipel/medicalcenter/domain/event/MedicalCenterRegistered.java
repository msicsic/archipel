package com.tentelemed.archipel.medicalcenter.domain.event;

import com.tentelemed.archipel.core.application.event.DomainEvent;
import com.tentelemed.archipel.medicalcenter.domain.model.Division;
import com.tentelemed.archipel.medicalcenter.domain.model.MedicalCenter;
import com.tentelemed.archipel.medicalcenter.domain.model.MedicalCenterId;
import com.tentelemed.archipel.medicalcenter.domain.model.MedicalCenterInfo;

/**
 * Created with IntelliJ IDEA.
 * User: Mael
 * Date: 10/12/13
 * Time: 14:34
 */
public class MedicalCenterRegistered extends MedicalCenterDomainEvent {

    protected MedicalCenter.Type type;
    protected String name;
    protected String ident;
    protected Division division;

    MedicalCenterRegistered() {}

    public MedicalCenterRegistered(MedicalCenterId id, MedicalCenter.Type type, String name, String ident, Division division) {
        super(id);
        this.type = type;
        this.name = name;
        this.ident = ident;
        this.division = division;
    }

    @Override
    public Type getCrudType() {
        return Type.CREATE;
    }

    public MedicalCenter.Type getType() {
        return type;
    }

    public String getName() {
        return name;
    }

    public String getIdent() {
        return ident;
    }

    public Division getDivision() {
        return division;
    }
}
