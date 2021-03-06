package com.tentelemed.archipel.site.infrastructure.web;

import com.tentelemed.archipel.core.infrastructure.web.BaseViewModel;
import com.tentelemed.archipel.site.application.command.CmdSiteCreate;
import com.tentelemed.archipel.site.application.command.CmdSiteUpdate;
import com.tentelemed.archipel.site.application.command.SiteCmdHandler;
import com.tentelemed.archipel.site.domain.pub.SiteQ;
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
public class UiSiteCreateModel extends BaseViewModel {
    private CmdSiteCreate cmdCreate = beanify(new CmdSiteCreate());
    private CmdSiteUpdate cmdUpdate = beanify(new CmdSiteUpdate());
    private boolean edit;
    @Autowired SiteCmdHandler serviceWrite;

    public boolean isEdit() {
        return edit;
    }

    public void setCenter(SiteQ center) {
        edit = true;
        cmdUpdate.ident = center.getIdent();
        cmdUpdate.name = center.getName();
        cmdUpdate.type = center.getType();
        cmdUpdate.id = center.getEntityId();
    }


    @Valid
    public CmdSiteCreate getCmdCreate() {
        return cmdCreate;
    }

    @Valid
    public CmdSiteUpdate getCmdUpdate() {
        return cmdUpdate;
    }

    public void action_createCenter() {
        try {
            commit();
            boolean valid = getBinder().isValid();
            System.err.println("valid : " + valid);
            if (isEdit()) {
                serviceWrite.execute(cmdUpdate);
            } else {
                serviceWrite.execute(cmdCreate);
            }
            close();
        } catch (FieldGroup.CommitException e) {
            show("Invalid data");
        }
    }

    public void action_cancel() {
        close();
    }

}
