package com.tentelemed.archipel.medicalcenter.infrastructure.web;

import com.tentelemed.archipel.core.infrastructure.web.BasePopup;
import com.tentelemed.archipel.core.infrastructure.web.BaseViewModel;
import com.tentelemed.archipel.medicalcenter.application.service.MedicalCenterCommandService;
import com.tentelemed.archipel.medicalcenter.infrastructure.model.MedicalCenterQ;
import com.vaadin.data.fieldgroup.FieldGroup;
import com.vaadin.navigator.View;
import com.vaadin.ui.*;
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
    private MedicalCenterCommandService.CmdRegister cmd = new MedicalCenterCommandService.CmdRegister();

    @Autowired MedicalCenterCommandService serviceWrite;

    @Valid
    public MedicalCenterCommandService.CmdRegister getCmd() {
        return cmd;
    }

    public void action_createCenter()  {
        try {
            commit();
            boolean valid = getBinder().isValid();
            System.err.println("valid : "+valid);
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
