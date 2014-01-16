package com.tentelemed.archipel.site.infrastructure.web;

import com.tentelemed.archipel.core.domain.pub.DomainEvent;
import com.tentelemed.archipel.core.infrastructure.web.BaseViewModel;
import com.tentelemed.archipel.site.application.command.*;
import com.tentelemed.archipel.site.application.service.SiteQueryService;
import com.tentelemed.archipel.site.domain.pub.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Component;

import java.util.ArrayList;
import java.util.Collection;
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
    @Autowired SiteCmdHandler serviceWrite;
    SiteQ currentSite;
    private RoomQ selectedRoom;
    private Bed selectedBed;

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
                serviceWrite.execute(new CmdSiteDelete(getCurrentSite().getEntityId()));
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

    public boolean isDeleteRoomEnabled() {
        return getSelectedRoom() != null;
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

    public void action_createBed() {

    }

    public boolean isCreateBedEnabled() {
        return getSelectedRoom() != null;
    }

    public void action_deleteBed() {

    }

    public boolean isDeleteBedEnabled() {
        return getSelectedBed() != null;
    }


    @Override
    protected void onDomainEventReceived(DomainEvent event) {
        if (event instanceof EvtSiteDomainEvent) {
            // il faut recharcer la version à jour du modele
            currentSite = serviceRead.getSite((SiteId) event.getId());
        }
    }

    public List<RoomQ> getRooms() {
        return serviceRead.getRooms(getCurrentSite());
    }

    public void setSelectedRoom(RoomQ room) {
        this.selectedRoom = room;
        this.selectedBed = null;
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

    public void deleteSector(final LocationQ loc) {
        confirm("Delete Sector", "Do you really want to delete this sector ? (cannot be undone)", new Runnable() {
            @Override
            public void run() {
                serviceWrite.execute(new CmdSiteDeleteSector(currentSite.getEntityId(), loc.getCode()));
            }
        });
    }

    public void deleteService(final LocationQ loc) {
        confirm("Delete Sector", "Do you really want to delete this service ? (cannot be undone)", new Runnable() {
            @Override
            public void run() {
                serviceWrite.execute(new CmdSiteDeleteService(currentSite.getEntityId(), loc.getCode()));
            }
        });
    }

    public void createFunctionalUnit(LocationQ loc) {
        UiSiteCreateFunctionalUnit view = getView(UiSiteCreateFunctionalUnit.class);
        view.getModel().setSector(currentSite.getEntityId(), loc);
        show(view);
    }

    public void deleteFunctionalUnit(final LocationQ loc) {
        confirm("Delete Sector", "Do you really want to delete this functional unit ? (cannot be undone)", new Runnable() {
            @Override
            public void run() {
                serviceWrite.execute(new CmdSiteDeleteFunctionalUnit(currentSite.getEntityId(), loc.getCode()));
            }
        });
    }

    public void createActivityUnit(final LocationQ loc) {
        UiSiteCreateActivityUnit view = getView(UiSiteCreateActivityUnit.class);
        view.getModel().setSector(currentSite.getEntityId(), loc);
        show(view);
    }

    public void deleteActivityUnit(final LocationQ loc) {
        confirm("Delete Sector", "Do you really want to delete this activity unit ? (cannot be undone)", new Runnable() {
            @Override
            public void run() {
                serviceWrite.execute(new CmdSiteDeleteActivityUnit(currentSite.getEntityId(), loc.getCode()));
            }
        });
    }

    public List<Bed> getBeds() {
        if (getSelectedRoom() == null) {
            return new ArrayList<>();
        }
        return getSelectedRoom().getBeds();
    }

    public Bed getSelectedBed() {
        return selectedBed;
    }

    public void setSelectedBed(Bed bed) {
        this.selectedBed = bed;
    }
}
