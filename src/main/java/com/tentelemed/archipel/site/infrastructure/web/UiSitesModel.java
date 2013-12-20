package com.tentelemed.archipel.site.infrastructure.web;

import com.tentelemed.archipel.core.application.event.DomainEvent;
import com.tentelemed.archipel.core.infrastructure.web.BaseViewModel;
import com.tentelemed.archipel.site.application.command.CmdDeleteSite;
import com.tentelemed.archipel.site.application.service.SiteCommandService;
import com.tentelemed.archipel.site.application.service.SiteQueryService;
import com.tentelemed.archipel.site.domain.event.SiteDomainEvent;
import com.tentelemed.archipel.site.domain.model.*;
import com.tentelemed.archipel.site.infrastructure.model.LocationQ;
import com.tentelemed.archipel.site.infrastructure.model.SiteQ;
import com.tentelemed.archipel.site.infrastructure.model.RoomQ;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Component;

import java.util.Comparator;
import java.util.List;

/**
 * Created with IntelliJ IDEA.
 * User: Mael
 * Date: 11/12/13
 * Time: 13:56
 */
@Component
@Scope("prototype")
public class UiSitesModel extends BaseViewModel {

    @Autowired SiteQueryService serviceRead;
    @Autowired SiteCommandService serviceWrite;
    SiteQ currentSite;
    private RoomQ selectedRoom;

    public List<SiteQ> getSites() {
        return sort(serviceRead.getAll(), new Comparator<SiteQ>() {
            @Override
            public int compare(SiteQ o1, SiteQ o2) {
                return o1.getName().compareTo(o2.getName());
            }
        });
    }

    public void setCurrentSite(SiteQ currentSite) {
        this.currentSite = currentSite;
    }

    public SiteQ getCurrentSite() {
        return currentSite;
    }

    public void action_createCenter() {
        UiSiteCreate view = getView(UiSiteCreate.class);
        show(view);
    }

    public void action_deleteCenter() {
        confirm("Medical Center deletion", "Do you really want to delete this Medical Center ?", new Runnable() {
            @Override
            public void run() {
                serviceWrite.execute(new CmdDeleteSite(getCurrentSite().getEntityId()));
            }
        });
    }

    public boolean isDeleteCenterEnabled() {
        return getCurrentSite() != null;
    }

    public void action_editMain() {
        UiSiteCreate view = getView(UiSiteCreate.class);
        view.setCenter(getCurrentSite());
        show(view);
    }

    public void action_createRoom() {
        UiRoomCreate view = getView(UiRoomCreate.class);
        show(view);
    }

    public void action_deleteRoom() {
        // TODO
    }

    public boolean isEditMainEnabled() {
        return getCurrentSite() != null;
    }

    public void action_editAddInfo() {
        UiSiteEdit view = getView(UiSiteEdit.class);
        view.setCenter(getCurrentSite());
        show(view);
    }

    public boolean isEditAddInfoEnabled() {
        return getCurrentSite() != null;
    }

    @Override
    protected void onDomainEventReceived(DomainEvent event) {
        if (event instanceof SiteDomainEvent) {
            // il faut recharcer la version à jour du modele
            currentSite = serviceRead.getCenter((SiteId) event.getId());
        }
    }

    /*public Division createDefaultDivision() {
        Set<Sector> sectors = new HashSet<>();
        Sector sector = new Sector(Sector.Type.MED, "Consultations", "CNS");
        Service service1 = new Service(sector, "Generaliste", "CGEN");
        Service service2 = new Service(sector, "Ophtalmo", "COPH");
        FunctionalUnit fu1 = new FunctionalUnit(service1, "FU1", "FU1");
        FunctionalUnit fu2 = new FunctionalUnit(service1, "FU2", "FU2");

        sectors.add(sector);
        Division div = new Division(sectors);
        return div;
    }*/

    public List<RoomQ> getRooms() {
        return serviceRead.getRooms(getCurrentSite());
    }

    public void setSelectedRoom(RoomQ room) {
        this.selectedRoom = room;
    }

    public RoomQ getSelectedRoom() {
        return selectedRoom;
    }

    public void createService(LocationQ sector) {
        // popup d'edition
        UiSiteCreateService view = getView(UiSiteCreateService.class);
        view.getModel().setSector(currentSite.getEntityId(), sector);
        show(view);
    }

    public void createSector() {
        UiSiteCreateSector view = getView(UiSiteCreateSector.class);
        view.getModel().setCurrentSite(currentSite);
        show(view);
    }
}
