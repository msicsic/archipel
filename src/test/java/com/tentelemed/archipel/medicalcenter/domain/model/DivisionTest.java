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
        List<Service> services = new ArrayList<>();
        List<FunctionalUnit> units = new ArrayList<>();
        Set<Sector> sectors = new HashSet<>();
        units.add(new FunctionalUnit("unit1", "UNIT1", new ArrayList<ActivityUnit>()));
        services.add(new Service("Generaliste", "GEN", units));
        services.add(new Service("Ophtalmo", "OPH", new ArrayList<FunctionalUnit>()));
        Sector sector = new Sector(Sector.Type.MED, "consultation", "CONSULT", services);
        sectors.add(sector);
        Division division = new Division(sectors);

        // When
        List<LocationCode> codes = division.getLocationCodes();

        // Then
        assertThat(codes.size(), equalTo(2));
        assertThat(codes.get(0).getCode(), equalTo("SEC:CONSULT|SRV:GEN|FU:UNIT1"));
        assertThat(codes.get(1).getCode(), equalTo("SEC:CONSULT|SRV:OPH"));
    }
}
