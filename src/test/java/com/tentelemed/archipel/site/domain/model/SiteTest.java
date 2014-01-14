package com.tentelemed.archipel.site.domain.model;

import com.tentelemed.archipel.site.application.command.*;
import com.tentelemed.archipel.site.domain.pub.Location;
import com.tentelemed.archipel.site.domain.pub.LocationPath;
import com.tentelemed.archipel.site.domain.pub.SiteId;
import com.tentelemed.archipel.site.domain.pub.SiteType;
import org.junit.Test;

import java.util.List;
import java.util.Set;

import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.Matchers.*;

/**
 * Created with IntelliJ IDEA.
 * User: Mael
 * Date: 09/01/14
 * Time: 14:01
 */
public class SiteTest {

    private Site getSite() {
        Site site = new Site();
        site._setId(0);
        site.execute(new CmdSiteCreate(SiteType.CHU, "SiteName", "CH1"));
        site.execute(new CmdSiteCreateSector(new SiteId(0), Sector.Type.ADMIN, "SecAdmin", "SC1"));
        site.execute(new CmdSiteCreateService(new SiteId(0), "SC1", "SV1", "Service1"));
        site.execute(new CmdSiteCreateService(new SiteId(0), "SC1", "SV2", "Service2"));
        site.execute(new CmdSiteCreateFunctionalUnit(new SiteId(0), "SV1", "FU1", "Functional1"));
        site.execute(new CmdSiteCreateFunctionalUnit(new SiteId(0), "SV1", "FU2", "Functional2"));
        site.execute(new CmdSiteCreateActivityUnit(new SiteId(0), "FU1", "AU1", "Activity1"));
        site.execute(new CmdSiteCreateActivityUnit(new SiteId(0), "FU1", "AU2", "Activity2"));
        site.execute(new CmdSiteCreateActivityUnit(new SiteId(0), "FU1", "AU3", "Activity3"));
        return site;
    }

    @Test
    public void testGetLocationsCodes() throws Exception {
        // Given (site créé)
        Site site = getSite();

        // When
        Set<String> codes = site.getLocationsCodes();

        // Then
        assertThat(codes.size(), equalTo(9));
        assertThat(codes.contains("SC1"), equalTo(true));
        assertThat(codes.contains("FU1"), equalTo(true));
        assertThat(codes.contains("FU2"), equalTo(true));
        assertThat(codes.contains("AU3"), equalTo(true));
    }

    @Test
    public void testGetLocationFromPath() throws Exception {
        // Given
        Site site = getSite();

        // When
        Location location = site.getLocationFromPath(new LocationPath("SEC:SC1|SRV:SV1|FU:FU1"));

        // Then
        assertThat(location, notNullValue());
        assertThat(location instanceof FunctionalUnit, equalTo(true));
        assertThat(location.getName(), equalTo("Functional1"));

        // When
        location = site.getLocationFromPath(new LocationPath("SEC:SC1|SRV:SV1|FU:FU1|AU:AU3"));

        // Then
        assertThat(location, notNullValue());
        assertThat(location instanceof ActivityUnit, equalTo(true));
        assertThat(location.getName(), equalTo("Activity3"));
    }

    @Test
    public void testGetLocationPaths() throws Exception {
        // Given (site créé)
        Site site = getSite();

        // When
        List<LocationPath> paths = site.getLocationPaths();

        // Then
        assertThat(paths.size(), equalTo(9));
        assertThat(paths.contains(new LocationPath("SEC:SC1")), equalTo(true));
        assertThat(paths.contains(new LocationPath("SEC:SC1|SRV:SV1")), equalTo(true));
        assertThat(paths.contains(new LocationPath("SEC:SC1|SRV:SV2")), equalTo(true));
        assertThat(paths.contains(new LocationPath("SEC:SC1|SRV:SV1|FU:FU1")), equalTo(true));
        assertThat(paths.contains(new LocationPath("SEC:SC1|SRV:SV1|FU:FU1|AU:AU3")), equalTo(true));
    }

    @Test
    public void testFindLocation() throws Exception {
        // Given (site créé)
        Site site = getSite();

        // When
        Location location = site.findLocation("AU2");

        // Then
        assertThat(location, notNullValue());
        assertThat(location instanceof ActivityUnit, equalTo(true));
        assertThat(location.getName(), equalTo("Activity2"));
        assertThat(location.getParent(), notNullValue());
        assertThat(location.getParent().getName(), equalTo("Functional1"));
    }
}
