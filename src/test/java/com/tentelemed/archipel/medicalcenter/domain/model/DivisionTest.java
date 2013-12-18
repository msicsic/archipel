package com.tentelemed.archipel.medicalcenter.domain.model;

import org.junit.Test;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.Matchers.equalTo;

/**
 * Created with IntelliJ IDEA.
 * User: Mael
 * Date: 10/12/13
 * Time: 17:16
 */
public class DivisionTest {
    @Test
    public void testLocationCode() throws Exception {
        // Given
        Set<Sector> sectors = new HashSet<>();
        Sector sector = new Sector(Sector.Type.MED, "Consultations", "CNS");
        Service service1 = new Service(sector, "Generaliste", "CGEN");
        Service service2 = new Service(sector, "Ophtalmo", "COPH");
        FunctionalUnit fu1 = new FunctionalUnit(service1, "FU1", "FU1");
        FunctionalUnit fu2 = new FunctionalUnit(service1, "FU2", "FU2");
        sectors.add(sector);
        Division division = new Division(sectors);

        // When
        List<String> codes = division.getLocationCodes();

        // Then
        assertThat(codes.size(), equalTo(3));
        assertThat(codes.get(0), equalTo("SEC:CNS|SRV:CGEN|FU:FU1"));
        assertThat(codes.get(1), equalTo("SEC:CNS|SRV:CGEN|FU:FU2"));
        assertThat(codes.get(2), equalTo("SEC:CNS|SRV:COPH"));
    }
}
