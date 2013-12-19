package com.tentelemed.archipel.site.application.service;

import com.tentelemed.archipel.core.application.service.BaseQueryService;
import com.tentelemed.archipel.core.domain.model.Country;
import com.tentelemed.archipel.site.domain.interfaces.SiteRepository;
import com.tentelemed.archipel.site.domain.model.Bank;
import com.tentelemed.archipel.site.domain.model.SiteId;
import com.tentelemed.archipel.site.infrastructure.model.SiteQ;
import com.tentelemed.archipel.site.infrastructure.model.RoomQ;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

/**
 * <p/>
 * Created with IntelliJ IDEA.
 * User: Mael
 * Date: 06/11/13
 * Time: 18:23
 */
@Component
@Transactional
public class SiteQueryService extends BaseQueryService {

    @Autowired
    SiteRepository repo;

    public List<SiteQ> getAll() {
        return repo.getAll();
    }

    public SiteQ getCenter(SiteId id) {
        return repo.load(id);
    }

    public List<Country> getCountries() {
        return repo.getCountries();
    }

    public List<Bank> getBanks() {
        return repo.getBanks();
    }

    public List<RoomQ> getRooms(SiteQ center) {
        return repo.getRooms(center);
    }
}
