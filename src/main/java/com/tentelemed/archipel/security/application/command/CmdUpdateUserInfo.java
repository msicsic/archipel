package com.tentelemed.archipel.security.application.command;

import com.tentelemed.archipel.core.application.service.Command;
import com.tentelemed.archipel.security.domain.model.UserId;

import javax.validation.constraints.NotNull;
import java.util.Date;

/**
 * Created with IntelliJ IDEA.
 * User: Mael
 * Date: 18/12/13
 * Time: 21:42
 */
public class CmdUpdateUserInfo extends Command<UserId> {
    @NotNull public String firstName;
    @NotNull public String lastName;
    public Date dob;
    @NotNull public String email;

    public CmdUpdateUserInfo() {
    }

    public CmdUpdateUserInfo(UserId id, String firstName, String lastName, Date dob, String email) {
        this.id = id;
        this.firstName = firstName;
        this.lastName = lastName;
        this.dob = dob;
        this.email = email;
    }
}
