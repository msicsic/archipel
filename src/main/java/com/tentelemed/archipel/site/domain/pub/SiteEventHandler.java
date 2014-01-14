package com.tentelemed.archipel.site.domain.pub;

/**
 * Created with IntelliJ IDEA.
 * User: Mael
 * Date: 06/01/14
 * Time: 14:00
 */
public interface SiteEventHandler {
    void handle(EvtSiteActivityUnitAdded evt);

    void handle(EvtSiteActivityUnitDeleted evt);

    void handle(EvtSiteAdditionalInfoUpdated evt);

    void handle(EvtSiteDeleted evt);

    void handle(EvtSiteFunctionalUnitAdded evt);

    void handle(EvtSiteFunctionalUnitDeleted evt);

    void handle(EvtSiteMainInfoUpdated evt);

    void handle(EvtSiteRegistered evt);

    void handle(EvtSiteRoomAdded evt);

    void handle(EvtSiteRoomRemoved evt);

    void handle(EvtSiteSectorAdded evt);

    void handle(EvtSiteSectorDeleted evt);

    void handle(EvtSiteServiceAdded evt);

    void handle(EvtSiteServiceDeleted evt);
}
