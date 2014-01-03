package com.tentelemed.archipel.site.infrastructure.model;

import com.tentelemed.archipel.site.domain.event.SiteRegistered;
import com.tentelemed.archipel.site.domain.event.SiteSectorAdded;
import com.tentelemed.archipel.site.domain.model.Sector;
import com.tentelemed.archipel.site.domain.model.SiteId;
import com.tentelemed.archipel.site.domain.model.SiteType;
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
        SiteSectorAdded event = new SiteSectorAdded(site.getEntityId(), Sector.Type.ADMIN, "ADM", "Facturation");
        site.applyEvent(event);
        // TODO
    }

    private SiteQ createSite() {
        SiteQ site = new SiteQ();
        SiteId id = new SiteId();
        id.setId(11);
        LocationQ sector = new LocationQ(id, LocationQ.Type.SECTOR, "Sector1", "SE1");
        SiteRegistered event = new SiteRegistered(id, SiteType.CHD, "Site1", "CH1", sector);
        site.applyEvent(event);
        return site;
    }

    // TODO : tester les autres evts
}
