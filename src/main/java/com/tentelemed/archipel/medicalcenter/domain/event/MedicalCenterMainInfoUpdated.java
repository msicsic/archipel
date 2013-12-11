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
 * Time: 14:53
 */
public class MedicalCenterMainInfoUpdated extends MedicalCenterDomainEvent {

    private MedicalCenter.Type type;
    private String name;
    private String ident;

    public MedicalCenterMainInfoUpdated(MedicalCenterId id, MedicalCenter.Type type, String name, String ident) {
        super(id);
        this.type = type;
        this.name = name;
        this.ident = ident;
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
}
