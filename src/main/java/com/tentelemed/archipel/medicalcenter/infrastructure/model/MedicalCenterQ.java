package com.tentelemed.archipel.medicalcenter.infrastructure.model;

import com.tentelemed.archipel.core.infrastructure.model.BaseEntityQ;
import com.tentelemed.archipel.medicalcenter.domain.model.MedicalCenterId;
import com.tentelemed.archipel.medicalcenter.domain.model.MedicalCenterInfo;
import com.tentelemed.archipel.medicalcenter.domain.model.MedicalCenterType;

import javax.persistence.Embedded;
import javax.persistence.Entity;
import javax.validation.Valid;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;
import java.util.ArrayList;
import java.util.List;

/**
 * Created with IntelliJ IDEA.
 * User: Mael
 * Date: 12/12/13
 * Time: 14:25
 */
@Entity
public class MedicalCenterQ extends BaseEntityQ<MedicalCenterId> {
    @NotNull @Size(min = 3) String name;
    @NotNull @Size(min = 3) String ident;
    @NotNull MedicalCenterType type;
    @Valid @Embedded MedicalCenterInfo info;
    List<LocationQ> sectors = new ArrayList<>();

    @Override
    protected Class<MedicalCenterId> getIdClass() {
        return MedicalCenterId.class;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getIdent() {
        return ident;
    }

    public void setIdent(String ident) {
        this.ident = ident;
    }

    public MedicalCenterType getType() {
        return type;
    }

    public void setType(MedicalCenterType type) {
        this.type = type;
    }

    public MedicalCenterInfo getInfo() {
        return info;
    }

    public void setInfo(MedicalCenterInfo info) {
        this.info = info;
    }

    public List<LocationQ> getSectors() {
        return sectors;
    }

    public void setSectors(List<LocationQ> sectors) {
        this.sectors = sectors;
    }
}
