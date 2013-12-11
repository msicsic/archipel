package com.tentelemed.archipel.medicalcenter.infrastructure.web;

import com.tentelemed.archipel.core.infrastructure.web.BaseView;
import com.tentelemed.archipel.core.infrastructure.web.ModuleRoot;
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
@ModuleRoot(value = UiMedicalCentersView.NAME)
public class UiMedicalCentersView extends BaseView<UiMedicalCentersViewModel> {
    public static final String NAME = "medicalCenters";

    @Autowired UiMedicalCentersViewModel model;

    @Override
    public UiMedicalCentersViewModel getModel() {
        return model;
    }

    @Override
    public void postConstruct() {
        //To change body of implemented methods use File | Settings | File Templates.
    }
}
