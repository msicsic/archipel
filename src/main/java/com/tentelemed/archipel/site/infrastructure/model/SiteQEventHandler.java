package com.tentelemed.archipel.site.infrastructure.model;

import com.tentelemed.archipel.site.domain.pub.*;
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

    public void setObject(Object object) {
        this.site = (SiteQ) object;
    }

    @Override
    public void handle(EvtSiteServiceAdded event) {
        // retrouver le parent à partir de son code
        //LocationQ parent = siteService.findLocation(event.getId(), event.getParent());
        LocationQ parent = site.findLocation(event.getParent());

        LocationQ service = new LocationQ(event.getId(), LocationQ.Type.SERVICE, event.getName(), event.getCode(), parent);
        site.addLocation(0, 1, null, site.getSectors(), service);
    }

    @Override
    public void handle(EvtSiteServiceDeleted event) {
        site.removeLocation(0, 1, site.getSectors(), event.getServiceCode());
    }

    @Override
    public void handle(EvtSiteSectorAdded event) {
        LocationQ sector = new LocationQ(event.getId(), LocationQ.Type.SECTOR, event.getSectorName(), event.getSectorCode(), null);
        sector.setSectorType(event.getSectorType());
        site.addLocation(0, 0, null, site.getSectors(), sector);
    }

    @Override
    public void handle(EvtSiteSectorDeleted event) {
        site.removeLocation(0, 0, site.getSectors(), event.getSectorCode());
    }

    @Override
    public void handle(EvtSiteFunctionalUnitAdded event) {
        LocationQ parent = site.findLocation(event.getParent());
        LocationQ location = new LocationQ(event.getId(), LocationQ.Type.FU, event.getName(), event.getCode(), parent);
        site.addLocation(0, 2, null, site.getSectors(), location);
    }

    @Override
    public void handle(EvtSiteFunctionalUnitDeleted event) {
        site.removeLocation(0, 2, site.getSectors(), event.getCode());
    }

    @Override
    public void handle(EvtSiteActivityUnitAdded event) {
        LocationQ parent = site.findLocation(event.getParent());
        LocationQ location = new LocationQ(event.getId(), LocationQ.Type.AU, event.getName(), event.getCode(), parent);
        site.addLocation(0, 3, null, site.getSectors(), location);
    }

    @Override
    public void handle(EvtSiteActivityUnitDeleted event) {
        site.removeLocation(0, 3, site.getSectors(), event.getCode());
    }

    @Override
    public void handle(EvtSiteRegistered event) {
        site.setId(event.getId().getId());
        site.setIdent(event.getIdent());
        site.setName(event.getName());
        site.setType(event.getType());
        site.getSectors().add(event.getDefaultSector());
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
        site.setInfo(event.getInfo());
    }

    @Override
    public void handle(EvtSiteDeleted event) {
        // ras
    }

    @Override
    public void handle(EvtSiteMainInfoUpdated event) {
        site.setName(event.getName());
        site.setType(event.getType());
        site.setIdent(event.getIdent());
    }

}
