package com.tentelemed.archipel.medicalcenter.domain.interfaces;

import com.tentelemed.archipel.core.domain.model.Country;
import com.tentelemed.archipel.medicalcenter.domain.model.Bank;
import com.tentelemed.archipel.medicalcenter.domain.model.MedicalCenterId;
import com.tentelemed.archipel.medicalcenter.infrastructure.model.MedicalCenterQ;

import java.util.List;

/**
 * Created with IntelliJ IDEA.
 * User: Mael
 * Date: 07/11/13
 * Time: 12:19
 */
public interface MedicalCenterRepository {

    MedicalCenterQ save(MedicalCenterQ user);

    List<MedicalCenterQ> getAll();

    MedicalCenterQ load(MedicalCenterId id);

    void deleteCenter(MedicalCenterId id);

    List<Country> getCountries();

    List<Bank> getBanks();
}
