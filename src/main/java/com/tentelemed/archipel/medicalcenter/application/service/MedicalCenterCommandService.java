package com.tentelemed.archipel.medicalcenter.application.service;

import com.tentelemed.archipel.core.application.event.DomainEvent;
import com.tentelemed.archipel.core.application.service.BaseCommandService;
import com.tentelemed.archipel.medicalcenter.domain.model.MedicalCenter;
import com.tentelemed.archipel.medicalcenter.domain.model.MedicalCenterId;
import com.tentelemed.archipel.medicalcenter.domain.model.MedicalCenterType;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;

import javax.validation.ConstraintViolation;
import javax.validation.ConstraintViolationException;
import javax.validation.Validation;
import javax.validation.Validator;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;
import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import java.util.List;
import java.util.Set;

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
@Transactional
public class MedicalCenterCommandService extends BaseCommandService {

    public static class CmdRegister extends Command<MedicalCenterId> {
        @NotNull public MedicalCenterType type;
        @NotNull @Size(min = 3) public String name;
        @NotNull @Size(min = 3) public String ident;

        public void setType(MedicalCenterType type) {
            this.type = type;
        }

        public void setName(String name) {
            this.name = name;
        }

        public void setIdent(String ident) {
            this.ident = ident;
        }
    }

    private Validator validator;

    protected Validator getValidator() {
        if (validator == null) {
            this.validator = Validation.buildDefaultValidatorFactory().getValidator();
        }
        return this.validator;
    }

    protected <M extends Command> M validate(M command) {
        Set violations = getValidator().validate(command);
        if (!violations.isEmpty()) {
            for (Object oviolation : violations) {
                ConstraintViolation violation = (ConstraintViolation) oviolation;
                log.warn("constraint violation : " + command.getClass().getSimpleName() + "." + violation.getPropertyPath() + " " + violation.getMessage());
            }
            throw new ConstraintViolationException(violations);
        }
        return command;
    }

    public void execute(Command command) {
        Method m;
        try {
            // vérifier si handle existe bien
            m = getClass().getMethod("handle", command.getClass());
        } catch (NoSuchMethodException e) {
            throw new RuntimeException("Handle method not found for command : " + command.getClass().getSimpleName());
        }

        // valider la commande
        validate(command);

        try {
            // executer la commande
            m.invoke(this, command);
        } catch (InvocationTargetException e) {
            throw new RuntimeException("Error while executing command", e.getTargetException());
        } catch (IllegalAccessException e) {
            throw new RuntimeException("Cannot execute handle on command : " + command.getClass().getSimpleName());
        }
    }

    public Object handle(CmdRegister command) {
        MedicalCenter center = get(MedicalCenter.class);
        List<DomainEvent> events = center.register(command.type, command.name, command.ident);
        return post(center, events);
    }

    public CmdRegister cmdRegister() {
        return new CmdRegister();
    }

//    public MedicalCenterId registerCenter(MedicalCenterType type, String name, String ident) {
////        MedicalCenter center = get(MedicalCenter.class);
////        List<DomainEvent> events = center.register(type, name, ident);
////        return post(center, events);
//        CmdRegister cmd = new CmdRegister();
//        cmd.ident = ident;
//        cmd.name = name;
//        cmd.type = type;
//        execute(command);
//    }

}
