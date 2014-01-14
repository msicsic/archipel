package com.tentelemed.archipel.site.application.service;

import com.tentelemed.archipel.core.application.service.BaseQueryService;
import com.tentelemed.archipel.core.domain.model.Country;
import com.tentelemed.archipel.site.domain.pub.*;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
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

    @PersistenceContext
    EntityManager em;

    public List<SiteQ> getAll() {
        return em.createQuery("select m from SiteQ m").getResultList();
    }

    public SiteQ getSite(SiteId id) {
        return em.find(SiteQ.class, id.getId());
    }

    public List<Country> getCountries() {
        return em.createQuery("select c from Country c").getResultList();
    }

    public List<Bank> getBanks() {
        return em.createQuery("select c from Bank c").getResultList();
    }

    public List<RoomQ> getRooms(SiteQ center) {
        return em.createQuery("select c from RoomQ c").getResultList();
    }

    public LocationQ findLocation(SiteId siteId, String code) {
        return (LocationQ) em.createQuery("select l from LocationQ l where l.code=:code and l.siteId=:siteId")
                .setParameter("code", code)
                .setParameter("siteId", siteId)
                .getSingleResult();
    }
}
