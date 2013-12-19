package com.tentelemed.archipel.site.infrastructure.web;

import com.tentelemed.archipel.core.infrastructure.web.BaseViewModel;
import com.tentelemed.archipel.site.application.command.CmdCreateService;
import com.tentelemed.archipel.site.application.service.SiteCommandService;
import com.tentelemed.archipel.site.domain.model.SiteId;
import com.tentelemed.archipel.site.infrastructure.model.LocationQ;
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
public class UiSiteCreateServiceModel extends BaseViewModel {
    @Autowired SiteCommandService serviceWrite;
    private LocationQ sector;
    private CmdCreateService cmd = beanify(new CmdCreateService());

    @Valid
    public CmdCreateService getCmd() {
        return cmd;
    }

    public LocationQ getSector() {
        return sector;
    }

    public void setSector(SiteId centerId, LocationQ sector) {
        this.sector = sector;
        cmd.id = centerId;
        //cmd.sectorId = new SectorId(sector.getEntityId().getId());
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
