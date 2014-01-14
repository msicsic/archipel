package com.tentelemed.archipel.security.application.command;

import com.tentelemed.archipel.core.application.command.Command;
import com.tentelemed.archipel.security.domain.pub.UserId;

import javax.validation.constraints.NotNull;
import java.util.Date;

/**
 * Created with IntelliJ IDEA.
 * User: Mael
 * Date: 18/12/13
 * Time: 21:42
 */
public class CmdUserUpdateInfo extends Command<UserId> {
    @NotNull public String firstName;
    @NotNull public String lastName;
    public Date dob;
    @NotNull public String email;

    public CmdUserUpdateInfo() {
    }

    public CmdUserUpdateInfo(UserId id, String firstName, String lastName, Date dob, String email) {
        this.id = id;
        this.firstName = firstName;
        this.lastName = lastName;
        this.dob = dob;
        this.email = email;
    }
}
