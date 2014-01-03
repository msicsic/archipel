package com.tentelemed.archipel.site.infrastructure.web;

import com.tentelemed.archipel.core.infrastructure.web.BaseViewModel;
import com.tentelemed.archipel.site.application.command.CmdSiteCreateSector;
import com.tentelemed.archipel.site.application.service.SiteCommandService;
import com.tentelemed.archipel.site.infrastructure.model.LocationQ;
import com.tentelemed.archipel.site.infrastructure.model.SiteQ;
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
public class UiSiteCreateSectorModel extends BaseViewModel {
    @Autowired SiteCommandService serviceWrite;
    private LocationQ sector;
    private SiteQ currentSite;
    private CmdSiteCreateSector cmd = beanify(new CmdSiteCreateSector());

    @Valid
    public CmdSiteCreateSector getCmd() {
        return cmd;
    }

    public SiteQ getCurrentSite() {
        return currentSite;
    }

    public LocationQ getSector() {
        return sector;
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

    public void setCurrentSite(SiteQ currentSite) {
        this.currentSite = currentSite;
        this.cmd.id = currentSite.getEntityId();
    }
}
