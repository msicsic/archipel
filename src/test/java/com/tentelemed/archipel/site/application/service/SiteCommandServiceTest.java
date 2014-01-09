package com.tentelemed.archipel.site.application.service;

import com.tentelemed.archipel.core.application.service.CmdHandlerTest;
import com.tentelemed.archipel.core.application.service.CmdRes;
import com.tentelemed.archipel.core.domain.model.DomainException;
import com.tentelemed.archipel.site.application.command.*;
import com.tentelemed.archipel.site.domain.event.*;
import com.tentelemed.archipel.site.domain.model.*;
import com.tentelemed.archipel.site.infrastructure.model.RoomQ;
import com.tentelemed.archipel.site.infrastructure.model.SiteQ;
import org.junit.Before;
import org.junit.Test;

import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.Matchers.*;


/**
 * Created with IntelliJ IDEA.
 * User: Mael
 * Date: 08/01/14
 * Time: 13:44
 */
public class SiteCommandServiceTest extends CmdHandlerTest {

    SiteCmdHandler handler;
    RoomCmdHandler roomHandler;

    @Before
    public void setup() {
        super.setup();
        handler = context.getBean(SiteCmdHandler.class);
        roomHandler = context.getBean(RoomCmdHandler.class);
    }

    @Test
    public void executeCmdSiteCreate() {
        // Given (rien)

        // When (exec commande)
        CmdRes res = handler.execute(new CmdSiteCreate(SiteType.CHU, "SiteName", "AAAA"));

        // Then (evt levé)
        assertThat(res.getEvent(EvtSiteRegistered.class), notNullValue());

        // Then (Site créé)
        Site site = (Site) eventStore.get(new SiteId(0));
        assertThat(site, notNullValue());
        assertThat(site.getType(), equalTo(SiteType.CHU));
        assertThat(site.getName(), equalTo("SiteName"));
        assertThat(site.getIdent(), equalTo("AAAA"));

        // Then (SiteQ créé)
        SiteQ siteQ = pHandler.find(SiteQ.class, site.getEntityId().getId());
        assertThat(siteQ, notNullValue());
        assertThat(siteQ.getType(), equalTo(SiteType.CHU));
        assertThat(siteQ.getName(), equalTo("SiteName"));
        assertThat(siteQ.getIdent(), equalTo("AAAA"));
    }

    @Test
    public void executeCmdSiteDelete() {
        // Given
        handler.execute(new CmdSiteCreate(SiteType.CHU, "SiteName", "AAAA"));

        // When
        CmdRes res = handler.execute(new CmdSiteDelete(new SiteId(0)));

        // Then (evt levé)
        assertThat(res.getEvent(EvtSiteDeleted.class), notNullValue());

        // Then (Site n'est plus dans EventStore)
        assertThat(eventStore.get(new SiteId(0)), nullValue());

        // Then (SiteQ n'est plus dans pHandler)
        assertThat(pHandler.find(SiteQ.class, new SiteId(0).getId()), nullValue());
    }

    @Test
    public void executeCmdSiteUpdate() {
        // Given
        handler.execute(new CmdSiteCreate(SiteType.CHU, "SiteName", "AAAA"));

        // When
        CmdRes res = handler.execute(new CmdSiteUpdate(new SiteId(0), SiteType.CS, "NewName", "newIdent"));

        // Then (evt levé)
        assertThat(res.getEvent(EvtSiteMainInfoUpdated.class), notNullValue());

        // Then (Site modifié)
        Site site = (Site) eventStore.get(new SiteId(0));
        assertThat(site.getType(), equalTo(SiteType.CS));
        assertThat(site.getName(), equalTo("NewName"));
        assertThat(site.getIdent(), equalTo("newIdent"));

        // Then (SiteQ modifié)
        SiteQ siteQ = pHandler.find(SiteQ.class, site.getEntityId().getId());
        assertThat(siteQ.getType(), equalTo(site.getType()));
        assertThat(siteQ.getName(), equalTo(site.getName()));
        assertThat(siteQ.getIdent(), equalTo(site.getIdent()));
    }

    @Test
    public void executeCmdSiteUpdateAdditionalInfo() {
        // Given
        handler.execute(new CmdSiteCreate(SiteType.CHU, "SiteName", "AAAA"));

        // When
        CmdSiteUpdateAdditionalInfo cmd = new CmdSiteUpdateAdditionalInfo();
        cmd.id = new SiteId(0);
        cmd.town = "Paris";
        cmd.street = "102 Vaillant";
        cmd.siret = "Siret";
        cmd.postalCode = "postal";
        cmd.bankCode = "BNP";
        cmd.countryIso = "FRA";
        cmd.directorName = "Durant";
        cmd.drugstoreAvailable = true;
        cmd.emergenciesAvailable = true;
        cmd.privateRoomAvailable = true;
        cmd.phone = "phone";
        cmd.fax = "fax";
        CmdRes res = handler.execute(cmd);

        // Then (evt levé)
        assertThat(res.getEvent(EvtSiteAdditionalInfoUpdated.class), notNullValue());

        // Then (Site modifié)
        Site site = (Site) eventStore.get(new SiteId(0));
        assertThat(site.getInfo(), notNullValue());
        assertThat(site.getInfo().getAddress().getTown(), equalTo(cmd.town));
        assertThat(site.getInfo().getAddress().getCountryIso(), equalTo(cmd.countryIso));
        assertThat(site.getInfo().getAddress().getPostalCode(), equalTo(cmd.postalCode));
        assertThat(site.getInfo().getAddress().getStreet(), equalTo(cmd.street));
        assertThat(site.getInfo().getBankCode(), equalTo(cmd.bankCode));
        assertThat(site.getInfo().getDirectorName(), equalTo(cmd.directorName));
        assertThat(site.getInfo().getFax(), equalTo(cmd.fax));
        assertThat(site.getInfo().getPhone(), equalTo(cmd.phone));
        assertThat(site.getInfo().getSiret(), equalTo(cmd.siret));

        // Then (SiteQ modifié)
        SiteQ siteQ = pHandler.find(SiteQ.class, site.getEntityId().getId());
        assertThat(siteQ.getInfo(), notNullValue());
        assertThat(siteQ.getInfo().getAddress().getTown(), equalTo(cmd.town));
        assertThat(siteQ.getInfo().getAddress().getCountryIso(), equalTo(cmd.countryIso));
        assertThat(siteQ.getInfo().getAddress().getPostalCode(), equalTo(cmd.postalCode));
        assertThat(siteQ.getInfo().getAddress().getStreet(), equalTo(cmd.street));
        assertThat(siteQ.getInfo().getBankCode(), equalTo(cmd.bankCode));
        assertThat(siteQ.getInfo().getDirectorName(), equalTo(cmd.directorName));
        assertThat(siteQ.getInfo().getFax(), equalTo(cmd.fax));
        assertThat(siteQ.getInfo().getPhone(), equalTo(cmd.phone));
        assertThat(siteQ.getInfo().getSiret(), equalTo(cmd.siret));
    }

    @Test
    public void executeCmdSiteCreateSector() {
        // Given
        handler.execute(new CmdSiteCreate(SiteType.CHU, "SiteName", "AAAA"));

        // When
        CmdRes res = handler.execute(new CmdSiteCreateSector(new SiteId(0), Sector.Type.TECH, "SectorTech", "ABC"));

        // Then (evt levé)
        assertThat(res.getEvent(EvtSiteSectorAdded.class), notNullValue());

        // Then (Site modifié)
        Site site = (Site) eventStore.get(new SiteId(0));
        assertThat(site.findLocation("ABC"), notNullValue());
        assertThat(site.findLocation("ABC").getName(), equalTo("SectorTech"));
        assertThat(site.findLocation("ABC").getCode(), equalTo("ABC"));
        assertThat(((Sector)site.findLocation("ABC")).getType(), equalTo(Sector.Type.TECH));

        // Then (SiteQ modifié)
        SiteQ siteQ = pHandler.find(SiteQ.class, site.getEntityId().getId());
        assertThat(siteQ.findLocation("ABC"), notNullValue());
        assertThat(siteQ.findLocation("ABC").getName(), equalTo("SectorTech"));
        assertThat(siteQ.findLocation("ABC").getCode(), equalTo("ABC"));
        assertThat(siteQ.findLocation("ABC").getSectorType(), equalTo(Sector.Type.TECH));
    }

    @Test
    public void executeCmdSiteDeleteSector() {
        // Given (Site avec un second secteur ajouté)
        handler.execute(new CmdSiteCreate(SiteType.CHU, "SiteName", "AAAA"));
        handler.execute(new CmdSiteCreateSector(new SiteId(0), Sector.Type.TECH, "SectorTech", "ABC"));

        // When
        CmdRes res = handler.execute(new CmdSiteDeleteSector(new SiteId(0), "ABC"));

        // Then (evt levé)
        assertThat(res.getEvent(EvtSiteSectorDeleted.class), notNullValue());

        // Then (Site modifié)
        Site site = (Site) eventStore.get(new SiteId(0));
        assertThat(site.findLocation("ABC"), nullValue());

        // Then (SiteQ modifié)
        SiteQ siteQ = pHandler.find(SiteQ.class, site.getEntityId().getId());
        assertThat(siteQ.findLocation("ABC"), nullValue());
    }

    @Test
    public void executeCmdSiteCreateService() {
        // Given (Site avec un second secteur ajouté)
        handler.execute(new CmdSiteCreate(SiteType.CHU, "SiteName", "AAAA"));
        handler.execute(new CmdSiteCreateSector(new SiteId(0), Sector.Type.TECH, "SectorTech", "ABC"));

        // When
        CmdRes res = handler.execute(new CmdSiteCreateService(new SiteId(0), "ABC", "SRV", "ServiceName"));

        // Then (evt levé)
        assertThat(res.getEvent(EvtSiteServiceAdded.class), notNullValue());

        // Then (Site modifié)
        Site site = (Site) eventStore.get(new SiteId(0));
        assertThat(site.findLocation("SRV"), notNullValue());
        assertThat(site.findLocation("SRV").getName(), equalTo("ServiceName"));
        assertThat(site.findLocation("SRV").getCode(), equalTo("SRV"));
        assertThat(site.findLocation("SRV") instanceof Service, equalTo(true));

        // Then (SiteQ modifié)
        SiteQ siteQ = pHandler.find(SiteQ.class, site.getEntityId().getId());
        assertThat(siteQ.findLocation("SRV"), notNullValue());
        assertThat(siteQ.findLocation("SRV").getName(), equalTo("ServiceName"));
        assertThat(siteQ.findLocation("SRV").getCode(), equalTo("SRV"));
    }

    @Test
    public void executeCmdSiteDeleteService() {
        // Given (Site avec un second service ajouté)
        handler.execute(new CmdSiteCreate(SiteType.CHU, "SiteName", "AAAA"));
        handler.execute(new CmdSiteCreateSector(new SiteId(0), Sector.Type.TECH, "SectorTech", "ABC"));
        handler.execute(new CmdSiteCreateService(new SiteId(0), "ABC", "SRV", "ServiceName"));

        // When
        CmdRes res = handler.execute(new CmdSiteDeleteService(new SiteId(0), "SRV"));

        // Then (evt levé)
        assertThat(res.getEvent(EvtSiteServiceDeleted.class), notNullValue());

        // Then (Site modifié)
        Site site = (Site) eventStore.get(new SiteId(0));
        assertThat(site.findLocation("SRV"), nullValue());

        // Then (SiteQ modifié)
        SiteQ siteQ = pHandler.find(SiteQ.class, site.getEntityId().getId());
        assertThat(siteQ.findLocation("SRV"), nullValue());
    }

    @Test
    public void executeCmdSiteCreateFunctionalUnit() {
        // Given (Site avec un second service ajouté)
        handler.execute(new CmdSiteCreate(SiteType.CHU, "SiteName", "AAAA"));
        handler.execute(new CmdSiteCreateSector(new SiteId(0), Sector.Type.TECH, "SectorTech", "ABC"));
        handler.execute(new CmdSiteCreateService(new SiteId(0), "ABC", "SRV", "ServiceName"));

        // When
        CmdRes res = handler.execute(new CmdSiteCreateFunctionalUnit(new SiteId(0), "SRV", "FU1", "FUName"));

        // Then (evt levé)
        assertThat(res.getEvent(EvtSiteFunctionalUnitAdded.class), notNullValue());

        // Then (Site modifié)
        Site site = (Site) eventStore.get(new SiteId(0));
        assertThat(site.findLocation("FU1"), notNullValue());
        assertThat(site.findLocation("FU1").getName(), equalTo("FUName"));
        assertThat(site.findLocation("FU1").getCode(), equalTo("FU1"));
        assertThat(site.findLocation("FU1") instanceof FunctionalUnit, equalTo(true));

        // Then (SiteQ modifié)
        SiteQ siteQ = pHandler.find(SiteQ.class, site.getEntityId().getId());
        assertThat(siteQ.findLocation("FU1"), notNullValue());
        assertThat(siteQ.findLocation("FU1").getName(), equalTo("FUName"));
        assertThat(siteQ.findLocation("FU1").getCode(), equalTo("FU1"));
    }

    @Test
    public void executeCmdSiteDeleteFunctionalUnit() {
        // Given (Site avec un second service ajouté)
        handler.execute(new CmdSiteCreate(SiteType.CHU, "SiteName", "AAAA"));
        handler.execute(new CmdSiteCreateSector(new SiteId(0), Sector.Type.TECH, "SectorTech", "ABC"));
        handler.execute(new CmdSiteCreateService(new SiteId(0), "ABC", "SRV", "ServiceName"));
        handler.execute(new CmdSiteCreateFunctionalUnit(new SiteId(0), "SRV", "FU1", "FUName"));

        // When
        CmdRes res = handler.execute(new CmdSiteDeleteFunctionalUnit(new SiteId(0), "FU1"));

        // Then (evt levé)
        assertThat(res.getEvent(EvtSiteFunctionalUnitDeleted.class), notNullValue());

        // Then (Site modifié)
        Site site = (Site) eventStore.get(new SiteId(0));
        assertThat(site.findLocation("FU1"), nullValue());

        // Then (SiteQ modifié)
        SiteQ siteQ = pHandler.find(SiteQ.class, site.getEntityId().getId());
        assertThat(siteQ.findLocation("FU1"), nullValue());

    }

    @Test
    public void executeCmdSiteCreateActivityUnit() {
        // Given (Site avec un second service ajouté)
        handler.execute(new CmdSiteCreate(SiteType.CHU, "SiteName", "AAAA"));
        handler.execute(new CmdSiteCreateSector(new SiteId(0), Sector.Type.TECH, "SectorTech", "ABC"));
        handler.execute(new CmdSiteCreateService(new SiteId(0), "ABC", "SRV", "ServiceName"));
        handler.execute(new CmdSiteCreateFunctionalUnit(new SiteId(0), "SRV", "FU1", "FUName"));

        // When
        CmdRes res = handler.execute(new CmdSiteCreateActivityUnit(new SiteId(0), "FU1", "AU1", "AUName"));

        // Then (evt levé)
        assertThat(res.getEvent(EvtSiteActivityUnitAdded.class), notNullValue());

        // Then (Site modifié)
        Site site = (Site) eventStore.get(new SiteId(0));
        assertThat(site.findLocation("AU1"), notNullValue());
        assertThat(site.findLocation("AU1").getName(), equalTo("AUName"));
        assertThat(site.findLocation("AU1").getCode(), equalTo("AU1"));
        assertThat(site.findLocation("AU1") instanceof ActivityUnit, equalTo(true));

        // Then (SiteQ modifié)
        SiteQ siteQ = pHandler.find(SiteQ.class, site.getEntityId().getId());
        assertThat(siteQ.findLocation("AU1"), notNullValue());
        assertThat(siteQ.findLocation("AU1").getName(), equalTo("AUName"));
        assertThat(siteQ.findLocation("AU1").getCode(), equalTo("AU1"));
    }

    @Test
    public void executeCmdSiteDeleteActivityUnit() {
        // Given (Site avec un second service ajouté)
        handler.execute(new CmdSiteCreate(SiteType.CHU, "SiteName", "AAAA"));
        handler.execute(new CmdSiteCreateSector(new SiteId(0), Sector.Type.TECH, "SectorTech", "ABC"));
        handler.execute(new CmdSiteCreateService(new SiteId(0), "ABC", "SRV", "ServiceName"));
        handler.execute(new CmdSiteCreateFunctionalUnit(new SiteId(0), "SRV", "FU1", "FUName"));
        handler.execute(new CmdSiteCreateActivityUnit(new SiteId(0), "FU1", "AU1", "AUName"));

        // When
        CmdRes res = handler.execute(new CmdSiteDeleteActivityUnit(new SiteId(0), "AU1"));

        // Then (evt levé)
        assertThat(res.getEvent(EvtSiteActivityUnitDeleted.class), notNullValue());

        // Then (Site modifié)
        Site site = (Site) eventStore.get(new SiteId(0));
        assertThat(site.findLocation("AU1"), nullValue());

        // Then (SiteQ modifié)
        SiteQ siteQ = pHandler.find(SiteQ.class, site.getEntityId().getId());
        assertThat(siteQ.findLocation("AU1"), nullValue());
    }

    @Test
    public void executeCmdRoomCreate() {
        // Given (Site créé)
        handler.execute(new CmdSiteCreate(SiteType.CHU, "SiteName", "AAAA"));
        handler.execute(new CmdSiteCreateSector(new SiteId(0), Sector.Type.TECH, "SectorTech", "ABC"));
        handler.execute(new CmdSiteCreateService(new SiteId(0), "ABC", "SV1", "ServiceName"));
        CmdRes res = handler.execute(new CmdSiteCreateFunctionalUnit(new SiteId(0), "SV1", "FU1", "FUName"));
        Site site = (Site) res.aggregate;

        // When
        res = roomHandler.execute(new CmdRoomCreate(site.getEntityId(), "RoomName", false, new LocationPath("SEC:ABC|SRV:SV1|FU:FU1")));

        // Then (evt levé)
        assertThat(res.getEvent(EvtRoomRegistered.class), notNullValue());

        // Then (Room créé)
        Room room = (Room) eventStore.get(((Room) res.aggregate).getEntityId());
        assertThat(room, notNullValue());
        assertThat(room.getName(), equalTo("RoomName"));
        assertThat(room.getLocationPath(), equalTo(new LocationPath("SEC:ABC|SRV:SV1|FU:FU1")));
        assertThat(room.getSiteId(), equalTo(site.getEntityId()));

        // Then (RoomQ créé)
        RoomQ roomQ = pHandler.find(RoomQ.class, room.getEntityId().getId());
        assertThat(roomQ, notNullValue());
        assertThat(roomQ.getName(), equalTo(room.getName()));
        assertThat(roomQ.getLocationPath(), equalTo(room.getLocationPath().toString()));
        assertThat(roomQ.getSiteId(), notNullValue());
    }

    @Test(expected = DomainException.class)
    public void executeCmdRoomCreateBadLocation() {
        // Given (Site créé)
        CmdRes res = handler.execute(new CmdSiteCreate(SiteType.CHU, "SiteName", "AAAA"));
        Site site = (Site) res.aggregate;

        // When
        roomHandler.execute(new CmdRoomCreate(site.getEntityId(), "RoomName", false, new LocationPath("SEC:ABC|SRV:SV1|FU:FU4")));

        // Then (exception)
    }

    @Test(expected = DomainException.class)
    public void executeCmdRoomCreateBadMedical() {
        // Given (Site créé)
        handler.execute(new CmdSiteCreate(SiteType.CHU, "SiteName", "AAAA"));
        CmdRes res = handler.execute(new CmdSiteCreateSector(new SiteId(0), Sector.Type.TECH, "SectorTech", "ABC"));
        Site site = (Site) res.aggregate;

        // When
        roomHandler.execute(new CmdRoomCreate(site.getEntityId(), "RoomName", true, new LocationPath("SEC:ABC")));

        // Then (exception)
    }

    @Test
    public void executeCmdRoomAddBed() {
        // Given (site créé avec une Room)
        CmdRes res1 = handler.execute(new CmdSiteCreate(SiteType.CHU, "SiteName", "AAAA"));
        Site site = (Site) res1.aggregate;
        CmdRes res2 = roomHandler.execute(new CmdRoomCreate(site.getEntityId(), "RoomName", true, new LocationPath("SEC:MED")));
        Room room = (Room) res2.aggregate;

        // When
        CmdRes res = roomHandler.execute(new CmdRoomAddBed(room.getEntityId(), new Bed("Lit1")));

        // Then (evt levé)
        assertThat(res.getEvent(EvtRoomBedAdded.class), notNullValue());

        // Then (Room modifié)
        room = (Room) eventStore.get(room.getEntityId());
        assertThat(room.getBeds().contains(new Bed("Lit1")), equalTo(true));

        // Then (RoomQ modifié)
        RoomQ roomQ = pHandler.find(RoomQ.class, room.getEntityId().getId());
        assertThat(roomQ, notNullValue());
        assertThat(roomQ.getBeds().contains(new Bed("Lit1")), equalTo(true));
    }

    @Test
    public void executeCmdRoomRemoveBed() {
        // Given (site créé avec une Room)
        CmdRes res1 = handler.execute(new CmdSiteCreate(SiteType.CHU, "SiteName", "AAAA"));
        Site site = (Site) res1.aggregate;
        CmdRes res2 = roomHandler.execute(new CmdRoomCreate(site.getEntityId(), "RoomName", true, new LocationPath("SEC:MED")));
        Room room = (Room) res2.aggregate;
        CmdRes res3 = roomHandler.execute(new CmdRoomAddBed(room.getEntityId(), new Bed("Lit1")));

        // When
        CmdRes res = roomHandler.execute(new CmdRoomRemoveBed(room.getEntityId(), new Bed("Lit1")));

        // Then (evt levé)
        assertThat(res.getEvent(EvtRoomBedRemoved.class), notNullValue());

        // Then (Room modifié)
        room = (Room) eventStore.get(room.getEntityId());
        assertThat(room.getBeds().contains(new Bed("Lit1")), equalTo(false));

        // Then (RoomQ modifié)
        RoomQ roomQ = pHandler.find(RoomQ.class, room.getEntityId().getId());
        assertThat(roomQ, notNullValue());
        assertThat(roomQ.getBeds().contains(new Bed("Lit1")), equalTo(false));
    }

}
