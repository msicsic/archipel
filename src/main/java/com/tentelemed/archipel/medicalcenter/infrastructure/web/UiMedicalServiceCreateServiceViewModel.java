package com.tentelemed.archipel.medicalcenter.infrastructure.web;

import com.tentelemed.archipel.core.application.service.Command;
import com.tentelemed.archipel.core.infrastructure.web.BaseViewModel;
import com.tentelemed.archipel.medicalcenter.application.service.BeanCreator;
import com.tentelemed.archipel.medicalcenter.application.service.MedicalCenterCommandService;
import com.tentelemed.archipel.medicalcenter.application.service.MedicalCenterQueryService;
import com.tentelemed.archipel.medicalcenter.domain.model.Location;
import com.tentelemed.archipel.medicalcenter.domain.model.MedicalCenter;
import com.tentelemed.archipel.medicalcenter.domain.model.Sector;
import com.tentelemed.archipel.medicalcenter.infrastructure.model.MedicalCenterQ;
import com.tentelemed.archipel.medicalcenter.infrastructure.model.RoomQ;
import com.vaadin.data.fieldgroup.FieldGroup;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Component;

import javax.validation.Valid;
import java.util.ArrayList;
import java.util.List;

/**
 * Created with IntelliJ IDEA.
 * User: Mael
 * Date: 11/12/13
 * Time: 13:55
 */
@Component
@Scope("prototype")
public class UiMedicalServiceCreateServiceViewModel extends BaseViewModel {
    @Autowired MedicalCenterCommandService serviceWrite;
    private MedicalCenterCommandService.CmdCreateService cmd = BeanCreator.createBean(new MedicalCenterCommandService.CmdCreateService());
    private Sector sector;
    private MedicalCenterQ center;

    public Sector getSector() {
        return sector;
    }

    public MedicalCenterCommandService.CmdCreateService getCmd() {
        return cmd;
    }

    public void setSector(MedicalCenterQ center, Sector sector) {
        this.center = center;
        this.sector = sector;
        cmd.id = center.getEntityId();
        cmd.sectorCode = sector.getCode();
    }

    public void action_create() {
        try {
            commit();
            serviceWrite.execute(cmd);
            close();
        } catch (FieldGroup.CommitException e) {
            show("Invalid data");
        }
    }

    public void action_cancel() {
        close();
    }
}
