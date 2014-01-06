package com.tentelemed.archipel.site.infrastructure.model;

import com.tentelemed.archipel.site.application.service.SiteQueryService;
import com.tentelemed.archipel.site.domain.event.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Component;

/**
 * Created with IntelliJ IDEA.
 * User: Mael
 * Date: 06/01/14
 * Time: 14:55
 */
@Component
@Scope("prototype")
public class SiteQEventHandler implements SiteEventHandler {

    public SiteQ site;

    @Autowired SiteQueryService siteService;

    public void setObject(Object object) {
        this.site = (SiteQ) object;
    }

    @Override
    public void handle(EvtSiteServiceAdded event) {
        // retrouver le parent à partir de son code
        LocationQ parent = siteService.findLocation(event.getId(), event.getParent());

        LocationQ service = new LocationQ(event.getId(), LocationQ.Type.SERVICE, event.getName(), event.getCode(), parent);
        site.addLocation(0, 1, null, site.sectors, service);
    }

    @Override
    public void handle(EvtSiteServiceDeleted event) {
        site.removeLocation(0, 1, site.sectors, event.getServiceCode());
    }

    @Override
    public void handle(EvtSiteSectorAdded event) {
        LocationQ sector = new LocationQ(event.getId(), LocationQ.Type.SECTOR, event.getSectorName(), event.getSectorCode(), null);
        sector.setSectorType(event.getSectorType());
        site.addLocation(0, 0, null, site.sectors, sector);
    }

    @Override
    public void handle(EvtSiteSectorDeleted event) {
        site.removeLocation(0, 0, site.sectors, event.getSectorCode());
    }

    @Override
    public void handle(EvtSiteFunctionalUnitAdded event) {
        LocationQ parent = siteService.findLocation(event.getId(), event.getParent());
        LocationQ location = new LocationQ(event.getId(), LocationQ.Type.FU, event.getName(), event.getCode(), parent);
        site.addLocation(0, 2, null, site.sectors, location);
    }

    @Override
    public void handle(EvtSiteFunctionalUnitDeleted event) {
        site.removeLocation(0, 2, site.sectors, event.getCode());
    }

    @Override
    public void handle(EvtSiteActivityUnitAdded event) {
        LocationQ parent = siteService.findLocation(event.getId(), event.getParent());
        LocationQ location = new LocationQ(event.getId(), LocationQ.Type.FU, event.getName(), event.getCode(), parent);
        site.addLocation(0, 3, null, site.sectors, location);
    }

    @Override
    public void handle(EvtSiteActivityUnitDeleted event) {
        site.removeLocation(0, 3, site.sectors, event.getCode());
    }

    @Override
    public void handle(EvtSiteRegistered event) {
        site.setId(event.getId().getId());
        site.ident = event.getIdent();
        site.name = event.getName();
        site.type = event.getType();
        site.sectors.add(event.getDefaultSector());
    }

    @Override
    public void handle(EvtSiteRoomAdded evt) {
        // TODO
    }

    @Override
    public void handle(EvtSiteRoomRemoved evt) {
        // TODO
    }

    @Override
    public void handle(EvtSiteAdditionalInfoUpdated event) {
        site.info = event.getInfo();
    }

    @Override
    public void handle(EvtSiteDeleted event) {
        // ras
    }

    @Override
    public void handle(EvtSiteMainInfoUpdated event) {
        site.name = event.getName();
        site.type = event.getType();
        site.ident = event.getIdent();
    }

}
