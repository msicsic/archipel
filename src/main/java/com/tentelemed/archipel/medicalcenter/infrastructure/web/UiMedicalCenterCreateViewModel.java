package com.tentelemed.archipel.medicalcenter.infrastructure.web;

import com.tentelemed.archipel.core.infrastructure.web.BasePopup;
import com.tentelemed.archipel.core.infrastructure.web.BaseViewModel;
import com.tentelemed.archipel.medicalcenter.application.service.MedicalCenterCommandService;
import com.tentelemed.archipel.medicalcenter.infrastructure.model.MedicalCenterQ;
import com.vaadin.navigator.View;
import com.vaadin.ui.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Component;

/**
 * Created with IntelliJ IDEA.
 * User: Mael
 * Date: 11/12/13
 * Time: 13:55
 */
@Component
@Scope("prototype")
public class UiMedicalCenterCreateViewModel extends BaseViewModel {
    private MedicalCenterQ medicalCenter;

    @Autowired MedicalCenterCommandService serviceWrite;

    public MedicalCenterQ getMedicalCenter() {
        if (medicalCenter == null) {
            medicalCenter = new MedicalCenterQ();
        }
        return medicalCenter;
    }

    public void setMedicalCenter(MedicalCenterQ medicalCenter) {
        this.medicalCenter = medicalCenter;
    }

    public void action_createCenter()  {
        serviceWrite.registerCenter(medicalCenter.getType(), medicalCenter.getName(), medicalCenter.getIdent());
        close();
    }

    public void action_cancel() {
        close();
    }

}
