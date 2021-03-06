package com.tentelemed.archipel.site.infrastructure.model;

import com.tentelemed.archipel.site.domain.pub.*;
import org.junit.Test;

import static org.hamcrest.CoreMatchers.equalTo;
import static org.hamcrest.MatcherAssert.assertThat;

/**
 * Created with IntelliJ IDEA.
 * User: Mael
 * Date: 02/01/14
 * Time: 18:36
 */
public class SiteQTest {

    @Test
    public void testSiteRegistered() {
        SiteQ site = createSite();
        assertThat(site.getId(), equalTo(11));
        assertThat(site.getSectors().size(), equalTo(1));
        assertThat(site.getType(), equalTo(SiteType.CHD));
        assertThat(site.getName(), equalTo("Site1"));
    }

    public void testSiteXXX() {
        SiteQ site = createSite();
        EvtSiteSectorAdded event = new EvtSiteSectorAdded(site.getEntityId(), SectorType.ADMIN, "ADM", "Facturation");
        //site.handle(event);
        // TODO
    }

    private SiteQ createSite() {
        SiteQ site = new SiteQ();
        SiteId id = new SiteId();
        id.setId(11);
        LocationQ sector = new LocationQ(id, LocationQ.Type.SECTOR, "Sector1", "SE1", null);
        EvtSiteRegistered event = new EvtSiteRegistered(id, SiteType.CHD, "Site1", "CH1", sector);
        SiteQEventHandler handler = new SiteQEventHandler();
        handler.setObject(site);
        handler.handle(event);
        return site;
    }

    // TODO : tester les autres evts
}
