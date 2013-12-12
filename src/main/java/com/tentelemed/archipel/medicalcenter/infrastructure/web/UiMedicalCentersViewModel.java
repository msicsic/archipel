package com.tentelemed.archipel.medicalcenter.infrastructure.web;

import com.tentelemed.archipel.core.infrastructure.web.BaseViewModel;
import com.tentelemed.archipel.medicalcenter.application.service.MedicalCenterCommandService;
import com.tentelemed.archipel.medicalcenter.application.service.MedicalCenterQueryService;
import com.tentelemed.archipel.medicalcenter.infrastructure.model.MedicalCenterQ;
import com.vaadin.ui.UI;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.ApplicationContext;
import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Component;

import java.util.List;

/**
 * Created with IntelliJ IDEA.
 * User: Mael
 * Date: 11/12/13
 * Time: 13:56
 */
@Component
@Scope("prototype")
public class UiMedicalCentersViewModel extends BaseViewModel {

    @Autowired MedicalCenterQueryService serviceRead;
    MedicalCenterQ currentCenter;

    public List<MedicalCenterQ> getMedicalCenters() {
        return serviceRead.getAll();
    }

    public void setCurrentCenter(MedicalCenterQ currentCenter) {
        this.currentCenter = currentCenter;
    }

    public MedicalCenterQ getCurrentCenter() {
        return currentCenter;
    }

    public void action_createCenter() {
        UiMedicalCenterCreateView view = getView(UiMedicalCenterCreateView.class);
        UI.getCurrent().addWindow(view);
    }

    public void action_deleteCenter() {
        // TODO
    }

    public void action_editMain() {
        // TODO
    }

    public void action_editAddInfo() {
        // TODO
    }
}
