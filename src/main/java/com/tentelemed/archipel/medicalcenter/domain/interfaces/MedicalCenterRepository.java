package com.tentelemed.archipel.medicalcenter.domain.interfaces;

import com.tentelemed.archipel.medicalcenter.domain.model.MedicalCenterId;
import com.tentelemed.archipel.medicalcenter.infrastructure.model.MedicalCenterQ;
import com.tentelemed.archipel.security.domain.model.UserId;
import com.tentelemed.archipel.security.infrastructure.model.RoleQ;
import com.tentelemed.archipel.security.infrastructure.model.UserQ;

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

    void deleteUser(MedicalCenterId id);
}
