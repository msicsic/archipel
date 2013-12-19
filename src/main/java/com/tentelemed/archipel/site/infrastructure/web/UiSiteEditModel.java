package com.tentelemed.archipel.site.infrastructure.web;

import com.tentelemed.archipel.core.domain.model.Country;
import com.tentelemed.archipel.core.infrastructure.web.BaseViewModel;
import com.tentelemed.archipel.site.application.command.CmdUpdateAdditionalInfo;
import com.tentelemed.archipel.site.application.service.SiteCommandService;
import com.tentelemed.archipel.site.application.service.SiteQueryService;
import com.tentelemed.archipel.site.domain.model.Bank;
import com.tentelemed.archipel.site.infrastructure.model.SiteQ;
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
public class UiSiteEditModel extends BaseViewModel {
    private CmdUpdateAdditionalInfo cmd = beanify(new CmdUpdateAdditionalInfo());
    @Autowired SiteCommandService serviceWrite;
    @Autowired SiteQueryService serviceRead;

    public void setCenter(SiteQ center) {
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
    public CmdUpdateAdditionalInfo getCmd() {
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
