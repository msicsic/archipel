package com.tentelemed.archipel.security.application.command;

import com.tentelemed.archipel.core.application.service.Command;
import com.tentelemed.archipel.security.domain.model.RoleId;
import com.tentelemed.archipel.security.domain.model.UserId;
import org.hibernate.validator.constraints.Email;

import javax.validation.constraints.NotNull;
import java.util.Date;

/**
 * Created with IntelliJ IDEA.
 * User: Mael
 * Date: 18/12/13
 * Time: 21:42
 */
public final class CmdCreateUser extends Command<UserId> {
    @NotNull public RoleId roleId;
    @NotNull public String firstName;
    @NotNull public String lastName;
    public Date dob;
    @Email public String email;
    @NotNull public String login;

    public CmdCreateUser() {
    }

    public CmdCreateUser(RoleId roleId, String firstName, String lastName, Date dob, String email, String login) {
        this.roleId = roleId;
        this.firstName = firstName;
        this.lastName = lastName;
        this.dob = dob;
        this.email = email;
        this.login = login;
    }
}
