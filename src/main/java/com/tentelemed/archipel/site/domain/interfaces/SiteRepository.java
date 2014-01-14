package com.tentelemed.archipel.site.domain.interfaces;

import com.tentelemed.archipel.core.domain.model.Country;
import com.tentelemed.archipel.site.domain.pub.Bank;
import com.tentelemed.archipel.site.domain.pub.RoomQ;
import com.tentelemed.archipel.site.domain.pub.SiteId;
import com.tentelemed.archipel.site.domain.pub.SiteQ;

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
