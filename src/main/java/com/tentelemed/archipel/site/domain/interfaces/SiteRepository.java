package com.tentelemed.archipel.site.domain.interfaces;

import com.tentelemed.archipel.core.domain.model.Country;
import com.tentelemed.archipel.site.domain.model.Bank;
import com.tentelemed.archipel.site.domain.model.SiteId;
import com.tentelemed.archipel.site.infrastructure.model.RoomQ;
import com.tentelemed.archipel.site.infrastructure.model.SiteQ;

import java.util.List;

/**
 * Created with IntelliJ IDEA.
 * User: Mael
 * Date: 07/11/13
 * Time: 12:19
 */
public interface SiteRepository {

    SiteQ save(SiteQ user);

    List<SiteQ> getAll();

    SiteQ load(SiteId id);

    void deleteCenter(SiteId id);

    List<Country> getCountries();

    List<Bank> getBanks();

    List<RoomQ> getRooms(SiteQ center);
}
