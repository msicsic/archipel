package com.tentelemed.archipel.security.application.command;

import com.tentelemed.archipel.core.application.service.Command;
import com.tentelemed.archipel.security.domain.model.Right;
import com.tentelemed.archipel.security.domain.model.RoleId;

import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;

/**
 * Created with IntelliJ IDEA.
 * User: Mael
 * Date: 18/12/13
 * Time: 21:42
 */
public class CmdCreateRole extends Command<RoleId> {
    @NotNull @Size(min = 3, max = 32) public String name;
    @NotNull public Right[] rights;

    public CmdCreateRole(String name, Right... rights) {
        this.name = name;
        this.rights = rights;
    }
}
