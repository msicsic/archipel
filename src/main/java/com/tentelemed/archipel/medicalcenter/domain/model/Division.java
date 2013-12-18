package com.tentelemed.archipel.medicalcenter.domain.model;

import com.tentelemed.archipel.core.domain.model.BaseVO;

import javax.validation.constraints.AssertTrue;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;
import java.util.*;

/**
 * Découpage du centre médical en sous entités
 * <p/>
 * Created with IntelliJ IDEA.
 * User: Mael
 * Date: 10/12/13
 * Time: 11:52
 */
public class Division extends BaseVO {

    @NotNull @Size(min = 1) Set<Sector> sectors = new HashSet<>();

    Division() {
    }

    public Division(Set<Sector> sectors) {
        // par default, MED est toujours present
        this.sectors = sectors;
        validate();
    }

    @AssertTrue(message = "MED sector must be present")
    private boolean isSectorsValid() {
        for (Sector sector : sectors) {
            if (sector.getType() == Sector.Type.MED) {
                return true;
            }
        }
        return false;
    }

    public List<String> getLocationCodes() {
        List<String> result = new ArrayList<>();
        for (Sector sector : sectors) {
            result.addAll(sector.getLocationCodes());
        }
        return result;
    }

    public void addSector(Sector sector) {
        sectors.add(sector);
    }

    public Set<Sector> getSectors() {
        return Collections.unmodifiableSet(sectors);
    }
}
