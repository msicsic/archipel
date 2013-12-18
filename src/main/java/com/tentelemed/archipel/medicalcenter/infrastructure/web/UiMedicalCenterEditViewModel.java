package com.tentelemed.archipel.medicalcenter.infrastructure.web;

import com.tentelemed.archipel.core.application.service.Command;
import com.tentelemed.archipel.core.domain.model.Country;
import com.tentelemed.archipel.core.infrastructure.web.BaseViewModel;
import com.tentelemed.archipel.medicalcenter.application.service.BeanCreator;
import com.tentelemed.archipel.medicalcenter.application.service.MedicalCenterCommandService;
import com.tentelemed.archipel.medicalcenter.application.service.MedicalCenterQueryService;
import com.tentelemed.archipel.medicalcenter.domain.model.Bank;
import com.tentelemed.archipel.medicalcenter.domain.model.MedicalCenterInfo;
import com.tentelemed.archipel.medicalcenter.infrastructure.model.MedicalCenterQ;
import com.vaadin.data.fieldgroup.FieldGroup;
import org.apache.commons.beanutils.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Component;

import javax.validation.Valid;
import java.util.List;

/**
 * Created with IntelliJ IDEA.
 * User: Mael
 * Date: 11/12/13
 * Time: 13:55
 */
@Component
@Scope("prototype")
public class UiMedicalCenterEditViewModel extends BaseViewModel {
    private MedicalCenterCommandService.CmdUpdateAdditionalInfo cmd = BeanCreator.createBean(new MedicalCenterCommandService.CmdUpdateAdditionalInfo());
    @Autowired MedicalCenterCommandService serviceWrite;
    @Autowired MedicalCenterQueryService serviceRead;

    public void setCenter(MedicalCenterQ center) {
        if (center.getInfo() != null) {
            try {
                BeanUtils.copyProperties(cmd, center.getInfo());
            } catch (Exception e) {
                log.error(null, e);
            }
            cmd.street = center.getInfo().getAddress().getStreet();
            cmd.town = center.getInfo().getAddress().getTown();
            cmd.postalCode = center.getInfo().getAddress().getPostalCode();
            cmd.countryIso = center.getInfo().getAddress().getCountryIso();
        }
        cmd.id = center.getEntityId();
    }

    @Valid
    public MedicalCenterCommandService.CmdUpdateAdditionalInfo getCmd() {
        return cmd;
    }

    public void action_confirm() {
        try {
            commit();
            boolean valid = getBinder().isValid();
            System.err.println("valid : " + valid);
            serviceWrite.execute(cmd);
            close();
        } catch (FieldGroup.CommitException e) {
            show("Invalid data");
        }
    }

    public void action_cancel() {
        close();
    }

    public List<Country> getCountries() {
        return serviceRead.getCountries();
    }

    public List<Bank> getBanks() {
        return serviceRead.getBanks();
    }


}
