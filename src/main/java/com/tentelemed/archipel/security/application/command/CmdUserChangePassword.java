package com.tentelemed.archipel.security.application.command;

import com.tentelemed.archipel.core.application.command.Command;
import com.tentelemed.archipel.security.domain.pub.UserId;

import javax.validation.constraints.NotNull;

/**
 * Created with IntelliJ IDEA.
 * User: Mael
 * Date: 18/12/13
 * Time: 21:42
 */
public class CmdUserChangePassword extends Command<UserId> {
    @NotNull public String old;
    @NotNull public String new1;
    @NotNull public String new2;

    public CmdUserChangePassword() {
    }

    public CmdUserChangePassword(UserId id, String old, String new1, String new2) {
        this.id = id;
        this.old = old;
        this.new1 = new1;
        this.new2 = new2;
    }
}
