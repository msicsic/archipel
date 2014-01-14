package com.tentelemed.archipel.site.application.command;

import com.tentelemed.archipel.core.application.command.CmdRes;
import com.tentelemed.archipel.core.application.service.CmdHandlerTest;
import com.tentelemed.archipel.site.domain.model.*;
import com.tentelemed.archipel.site.domain.pub.*;
import org.junit.Before;
import org.junit.Test;

import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.Matchers.*;

/**
 * Created with IntelliJ IDEA.
 * User: Mael
 * Date: 10/01/14
 * Time: 14:02
 */
public class SiteCmdHandlerTest extends CmdHandlerTest {
    SiteCmdHandler handler;

    @Before
    public void setup() {
        super.setup();
        handler = context.getBean(SiteCmdHandler.class);
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
        CmdRes res = handler.execute(new CmdSiteCreateSector(new SiteId(0), SectorType.TECH, "SectorTech", "ABC"));

        // Then (evt levé)
        assertThat(res.getEvent(EvtSiteSectorAdded.class), notNullValue());

        // Then (Site modifié)
        Site site = (Site) eventStore.get(new SiteId(0));
        assertThat(site.findLocation("ABC"), notNullValue());
        assertThat(site.findLocation("ABC").getName(), equalTo("SectorTech"));
        assertThat(site.findLocation("ABC").getCode(), equalTo("ABC"));
        assertThat(((Sector) site.findLocation("ABC")).getType(), equalTo(SectorType.TECH));

        // Then (SiteQ modifié)
        SiteQ siteQ = pHandler.find(SiteQ.class, site.getEntityId().getId());
        assertThat(siteQ.findLocation("ABC"), notNullValue());
        assertThat(siteQ.findLocation("ABC").getName(), equalTo("SectorTech"));
        assertThat(siteQ.findLocation("ABC").getCode(), equalTo("ABC"));
        assertThat(siteQ.findLocation("ABC").getSectorType(), equalTo(SectorType.TECH));
    }

    @Test
    public void executeCmdSiteDeleteSector() {
        // Given (Site avec un second secteur ajouté)
        handler.execute(new CmdSiteCreate(SiteType.CHU, "SiteName", "AAAA"));
        handler.execute(new CmdSiteCreateSector(new SiteId(0), SectorType.TECH, "SectorTech", "ABC"));

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
        handler.execute(new CmdSiteCreateSector(new SiteId(0), SectorType.TECH, "SectorTech", "ABC"));

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
        handler.execute(new CmdSiteCreateSector(new SiteId(0), SectorType.TECH, "SectorTech", "ABC"));
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
        handler.execute(new CmdSiteCreateSector(new SiteId(0), SectorType.TECH, "SectorTech", "ABC"));
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
        handler.execute(new CmdSiteCreateSector(new SiteId(0), SectorType.TECH, "SectorTech", "ABC"));
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
        handler.execute(new CmdSiteCreateSector(new SiteId(0), SectorType.TECH, "SectorTech", "ABC"));
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
        handler.execute(new CmdSiteCreateSector(new SiteId(0), SectorType.TECH, "SectorTech", "ABC"));
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

}
