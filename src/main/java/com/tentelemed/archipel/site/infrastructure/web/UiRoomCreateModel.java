package com.tentelemed.archipel.site.infrastructure.web;

import com.tentelemed.archipel.core.infrastructure.web.BaseViewModel;
import com.tentelemed.archipel.site.application.command.CmdSiteCreateRoom;
import com.tentelemed.archipel.site.application.command.CmdSiteUpdateRoom;
import com.tentelemed.archipel.site.application.command.SiteCmdHandler;
import com.tentelemed.archipel.site.application.service.SiteQueryService;
import com.tentelemed.archipel.site.domain.pub.Location;
import com.tentelemed.archipel.site.domain.pub.RoomQ;
import com.tentelemed.archipel.site.domain.pub.SiteQ;
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
public class UiRoomCreateModel extends BaseViewModel {
    private CmdSiteCreateRoom cmdCreate = beanify(new CmdSiteCreateRoom());
    private CmdSiteUpdateRoom cmdUpdate = beanify(new CmdSiteUpdateRoom());
    private boolean edit;
    @Autowired SiteCmdHandler serviceWrite;
    @Autowired SiteQueryService serviceRead;
    private SiteQ currentCenter;

    public boolean isEdit() {
        return edit;
    }

    public void setRoom(RoomQ room) {
        edit = true;
        cmdUpdate.name = room.getName();
        cmdUpdate.id = room.getEntityId();
    }

    @Valid
    public CmdSiteCreateRoom getCmdCreate() {
        return cmdCreate;
    }

    @Valid
    public CmdSiteUpdateRoom getCmdUpdate() {
        return cmdUpdate;
    }

    public void action_createRoom() {
        /*
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
        */
    }

    public void action_cancel() {
        close();
    }

    public List<Location> getLocations() {
       /* Sector sector = new Sector(Sector.Type.MED, "Consultation", "CNS");
        List res = new ArrayList<>();
        res.add(sector);
        return res;*/
        // TODO
        return null;
    }
}
