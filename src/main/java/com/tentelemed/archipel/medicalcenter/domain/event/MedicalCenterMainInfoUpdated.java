package com.tentelemed.archipel.medicalcenter.domain.event;


import com.tentelemed.archipel.medicalcenter.domain.model.MedicalCenterId;
import com.tentelemed.archipel.medicalcenter.domain.model.MedicalCenterType;

/**
 * Created with IntelliJ IDEA.
 * User: Mael
 * Date: 10/12/13
 * Time: 14:53
 */
public class MedicalCenterMainInfoUpdated extends MedicalCenterDomainEvent {

    private MedicalCenterType type;
    private String name;
    private String ident;

    public MedicalCenterMainInfoUpdated(MedicalCenterId id, MedicalCenterType type, String name, String ident) {
        super(id);
        this.type = type;
        this.name = name;
        this.ident = ident;
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
}
