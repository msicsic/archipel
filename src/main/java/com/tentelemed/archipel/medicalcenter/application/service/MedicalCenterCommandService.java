package com.tentelemed.archipel.medicalcenter.application.service;

import com.tentelemed.archipel.core.application.service.BaseCommandService;
import com.tentelemed.archipel.core.application.service.CmdRes;
import com.tentelemed.archipel.core.application.service.Command;
import com.tentelemed.archipel.core.domain.model.Address;
import com.tentelemed.archipel.core.domain.model.Country;
import com.tentelemed.archipel.core.domain.model.PhoneNumber;
import com.tentelemed.archipel.medicalcenter.domain.model.*;
import org.springframework.stereotype.Component;

import javax.validation.Valid;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;

/**
 * Dans cette implémentation, la couche Application joue ces roles :
 * - orchestration des services applicatifs
 * - gestion des transactions
 * - manipulation du repo
 * - adapter pour la couche supérieure (infra ou client)
 * - reception des commandes clients -> params = DTO et non pas objets métier
 * - ouverture de la TX
 * - chargement de l'agregat concerné
 * - appel aux méthodes metier sur l'agregat
 * - fermeture de la TX
 * - envoi des EVTs (contient des DTO et non pas des objets métiers)
 * <p/>
 * Created with IntelliJ IDEA.
 * User: Mael
 * Date: 06/11/13
 * Time: 18:23
 */
@Component
public class MedicalCenterCommandService extends BaseCommandService {

    public static class CmdRegister extends Command<MedicalCenterId> {
        @NotNull public MedicalCenterType type;
        @NotNull @Size(min = 3) public String name;
        @NotNull @Size(min = 3) public String ident;
    }

    public static class CmdUpdate extends Command<MedicalCenterId> {
        @NotNull public MedicalCenterType type;
        @NotNull @Size(min = 3) public String name;
        @NotNull @Size(min = 3) public String ident;
    }

    public static class CmdUpdateAdditionalInfo extends Command<MedicalCenterId> {
        @NotNull public String siret;
        public String street;
        public String town;
        public String postalCode;
        public Country country;
        public String phone;
        public String fax;
        public String directorName;
        public Bank bank;
        public boolean emergenciesAvailable;
        public boolean drugstoreAvailable;
        public boolean privateRoomAvailable;
    }

    public static class CmdDelete extends Command<MedicalCenterId> {
        public CmdDelete(MedicalCenterId id) {
            super(id);
        }
    }


    CmdRes handle(CmdRegister cmd) {
        MedicalCenter center = get(MedicalCenter.class);
        return center.register(cmd.type, cmd.name, cmd.ident);
    }

    CmdRes handle(CmdUpdate cmd) {
        MedicalCenter center = (MedicalCenter) get(cmd.id);
        return center.updateMainInfo(cmd.type, cmd.name, cmd.ident);
    }

    CmdRes handle(CmdDelete cmd) {
        MedicalCenter center = (MedicalCenter) get(cmd.id);
        return center.delete();
    }

    CmdRes handle(CmdUpdateAdditionalInfo cmd) {
        Address address = new Address(cmd.street, cmd.postalCode, cmd.town, cmd.country);
        MedicalCenterInfo info = new MedicalCenterInfo(
                cmd.siret,
                address,
                cmd.phone,
                cmd.fax,
                cmd.directorName,
                cmd.bank,
                cmd.emergenciesAvailable,
                cmd.drugstoreAvailable,
                cmd.privateRoomAvailable
        );

        MedicalCenter center = (MedicalCenter) get(cmd.id);
        return center.updateAdditionalInfo(info);
    }
}
