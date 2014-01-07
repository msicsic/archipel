package com.tentelemed.archipel.security.application.command;

import com.tentelemed.archipel.core.application.service.Command;
import com.tentelemed.archipel.security.domain.model.Right;
import com.tentelemed.archipel.security.domain.model.RoleId;

import javax.validation.constraints.NotNull;
import java.util.Arrays;
import java.util.HashSet;
import java.util.Set;

/**
 * Created with IntelliJ IDEA.
 * User: Mael
 * Date: 18/12/13
 * Time: 21:42
 */
public class CmdRoleUpdateRights extends Command<RoleId> {
    @NotNull public Set<Right> rights;

    public CmdRoleUpdateRights(RoleId id, Right... rights) {
        super(id);
        this.rights = new HashSet<>(Arrays.asList(rights));
    }
}
