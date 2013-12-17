package com.tentelemed.archipel.medicalcenter.infrastructure.web;

import com.tentelemed.archipel.core.application.service.Command;
import com.tentelemed.archipel.core.infrastructure.web.BaseViewModel;
import com.tentelemed.archipel.medicalcenter.application.service.BeanCreator;
import com.tentelemed.archipel.medicalcenter.application.service.MedicalCenterCommandService;
import com.tentelemed.archipel.medicalcenter.infrastructure.model.MedicalCenterQ;
import com.vaadin.data.fieldgroup.FieldGroup;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Component;

import javax.validation.Valid;

/**
 * Created with IntelliJ IDEA.
 * User: Mael
 * Date: 11/12/13
 * Time: 13:55
 */
@Component
@Scope("prototype")
public class UiMedicalCenterCreateViewModel extends BaseViewModel {
    private MedicalCenterCommandService.CmdRegister cmdRegister = BeanCreator.createBean(new MedicalCenterCommandService.CmdRegister());
    private MedicalCenterCommandService.CmdUpdate cmdUpdate = BeanCreator.createBean(new MedicalCenterCommandService.CmdUpdate());
    private boolean edit;
    @Autowired MedicalCenterCommandService serviceWrite;

    public boolean isEdit() {
        return edit;
    }

    public void setCenter(MedicalCenterQ center) {
        edit = true;
        cmdUpdate.ident = center.getIdent();
        cmdUpdate.name = center.getName();
        cmdUpdate.type = center.getType();
        cmdUpdate.id = center.getEntityId();
    }

    @Valid
    public Command getCmd() {
        return isEdit() ? cmdUpdate : cmdRegister;
    }

    public MedicalCenterCommandService.CmdRegister getCmdRegister() {
        return cmdRegister;
    }

    public MedicalCenterCommandService.CmdUpdate getCmdUpdate() {
        return cmdUpdate;
    }

    public void action_createCenter() {
        try {
            commit();
            boolean valid = getBinder().isValid();
            System.err.println("valid : " + valid);
            serviceWrite.execute(isEdit() ? cmdUpdate : cmdRegister);
            close();
        } catch (FieldGroup.CommitException e) {
            show("Invalid data");
        }
    }

    public void action_cancel() {
        close();
    }

}
