package com.tentelemed.archipel.security.infrastructure.web.domain;

import com.tentelemed.archipel.core.infrastructure.web.domain.BaseEntityUI;
import com.tentelemed.archipel.gam.domain.Email;
import com.tentelemed.archipel.security.domain.model.Credentials;
import lombok.Getter;
import lombok.Setter;

import javax.validation.constraints.NotNull;
import java.util.Date;

/**
 * Created with IntelliJ IDEA.
 * User: Mael
 * Date: 01/12/13
 * Time: 01:54
 */
public class UserUI extends BaseEntityUI {

    @Getter @Setter String firstName;
    @Getter @Setter String lastName;
    @Getter @Setter Date dob;
    @Getter @Setter @NotNull Credentials credentials;
    @Getter @Setter Email email;

}
