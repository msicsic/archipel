package com.tentelemed.archipel.medicalcenter.application.service;

import com.tentelemed.archipel.core.application.service.BaseQueryService;
import com.tentelemed.archipel.core.domain.model.Country;
import com.tentelemed.archipel.medicalcenter.domain.interfaces.MedicalCenterRepository;
import com.tentelemed.archipel.medicalcenter.domain.model.Bank;
import com.tentelemed.archipel.medicalcenter.domain.model.MedicalCenterId;
import com.tentelemed.archipel.medicalcenter.infrastructure.model.MedicalCenterQ;
import com.tentelemed.archipel.medicalcenter.infrastructure.model.RoomQ;
import org.springframework.beans.factory.annotation.Autowired;
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
public class MedicalCenterQueryService extends BaseQueryService {

    @Autowired
    MedicalCenterRepository repo;

    public List<MedicalCenterQ> getAll() {
        return repo.getAll();
    }

    public MedicalCenterQ getCenter(MedicalCenterId id) {
        return repo.load(id);
    }

    public List<Country> getCountries() {
        return repo.getCountries();
    }

    public List<Bank> getBanks() {
        return repo.getBanks();
    }

    public List<RoomQ> getRooms(MedicalCenterQ center) {
        return repo.getRooms(center);
    }
}
