package com.tentelemed.archipel.medicalcenter.domain.event;

import com.tentelemed.archipel.medicalcenter.domain.model.Division;
import com.tentelemed.archipel.medicalcenter.domain.model.MedicalCenterId;
import com.tentelemed.archipel.medicalcenter.domain.model.MedicalCenterType;

/**
 * Created with IntelliJ IDEA.
 * User: Mael
 * Date: 10/12/13
 * Time: 14:34
 */
public class MedicalCenterRegistered extends MedicalCenterDomainEvent {

    protected MedicalCenterType type;
    protected String name;
    protected String ident;
    protected Division division;

    MedicalCenterRegistered() {
    }

    public MedicalCenterRegistered(MedicalCenterId id, MedicalCenterType type, String name, String ident, Division division) {
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

    public MedicalCenterType getType() {
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
