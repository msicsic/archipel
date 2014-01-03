package com.tentelemed.archipel.security.domain.model;

import com.tentelemed.archipel.core.application.event.DomainEvent;
import com.tentelemed.archipel.core.application.service.CmdRes;
import com.tentelemed.archipel.core.domain.model.BaseAggregateRoot;
import com.tentelemed.archipel.core.domain.model.DomainException;
import com.tentelemed.archipel.site.application.command.CmdSiteCreate;
import com.tentelemed.archipel.site.application.command.CmdSiteCreateSector;
import com.tentelemed.archipel.site.application.command.CmdSiteDelete;
import com.tentelemed.archipel.site.application.command.CmdSiteDeleteSector;
import com.tentelemed.archipel.site.domain.event.SiteRegistered;
import com.tentelemed.archipel.site.domain.event.SiteSectorAdded;
import com.tentelemed.archipel.site.domain.event.SiteSectorDeleted;
import com.tentelemed.archipel.site.domain.model.Sector;
import com.tentelemed.archipel.site.domain.model.Site;
import com.tentelemed.archipel.site.domain.model.SiteType;
import com.tentelemed.archipel.site.infrastructure.model.SiteQ;
import org.junit.Test;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.lang.reflect.Method;
import java.util.Date;
import java.util.List;

import static junit.framework.Assert.assertEquals;
import static org.hamcrest.CoreMatchers.equalTo;
import static org.hamcrest.CoreMatchers.notNullValue;
import static org.hamcrest.MatcherAssert.assertThat;


/**
 * Created with IntelliJ IDEA.
 * User: Mael
 * Date: 13/11/13
 * Time: 16:44
 */
public class SiteTest {
    Logger log = LoggerFactory.getLogger(SiteTest.class);

    private boolean containsEvent(CmdRes res, Class<? extends DomainEvent> eventClass) {
        for (DomainEvent e : res.events) {
            if (e.getClass().equals(eventClass)) {
                return true;
            }
        }
        return false;
    }

    @Test
    public void testCmdRegister() throws Exception {
        Site site = new Site();
        site._setId(0);
        CmdRes res = site.execute(new CmdSiteCreate(SiteType.CHD, "Site1", "CHD"));
        assertThat(site.getType(), equalTo(SiteType.CHD));
        assertThat(site.getName(), equalTo("Site1"));
        assertThat(site.getIdent(), equalTo("CHD"));
        assertThat(site.getSectors().size(), equalTo(1));
        Sector sector = site.getSectors().iterator().next();
        assertThat(sector, notNullValue());
        assertThat(sector.getType(), equalTo(Sector.Type.MED));
        assertThat(containsEvent(res, SiteRegistered.class), equalTo(true));
    }

    @Test
    public void testCmdCreateSector() throws Exception {
        Site site = new Site();
        site._setId(0);
        site.execute(new CmdSiteCreate(SiteType.CHD, "Site1", "CHD"));
        CmdRes res = site.execute(new CmdSiteCreateSector(null, Sector.Type.ADMIN, "Facturation", "ADM"));
        assertThat(containsEvent(res, SiteSectorAdded.class), equalTo(true));
        assertThat(site.getSectors().size(), equalTo(2));
    }

    /**
     * Le sector MED ne peut pas etre retiré
     * @throws Exception
     */
    @Test(expected = DomainException.class)
    public void testCmdDeleteMainSector() throws Exception {
        Site site = new Site();
        site._setId(0);
        site.execute(new CmdSiteCreate(SiteType.CHD, "Site1", "CHD"));
        site.execute(new CmdSiteDeleteSector(null, "MED"));
    }

    /**
     * Test avec un code de sector qui n'existe pas
     */
    @Test(expected = DomainException.class)
    public void testCmdDeleteUnknownSector() {
        Site site = new Site();
        site._setId(0);
        site.execute(new CmdSiteCreate(SiteType.CHD, "Site1", "CHD"));
        site.execute(new CmdSiteDeleteSector(null, "CCC"));
    }

    /**
     * Test avec un code de sector existant
     */
    @Test
    public void testCmdDeleteSectorOk() {
        Site site = new Site();
        site._setId(0);
        site.register(SiteType.CHD, "Site1", "CHD");
        site.createSector(Sector.Type.ADMIN, "ADM", "Facturation");
        CmdRes res = site.deleteSector("ADM");
        assertThat(site.getSectors().size(), equalTo(1));
        assertThat(containsEvent(res, SiteSectorDeleted.class), equalTo(true));
    }
}
