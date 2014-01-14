package com.tentelemed.archipel.site.application.command;

import com.tentelemed.archipel.core.application.command.Command;
import com.tentelemed.archipel.site.domain.pub.SiteId;

import javax.validation.constraints.NotNull;

/**
 * Created with IntelliJ IDEA.
 * User: Mael
 * Date: 18/12/13
 * Time: 21:36
 */
public class CmdSiteUpdateAdditionalInfo extends Command<SiteId> {
    @NotNull public String siret;
    public String street;
    public String town;
    public String postalCode;
    public String countryIso;
    public String phone;
    public String fax;
    public String directorName;
    public String bankCode;
    public boolean emergenciesAvailable;
    public boolean drugstoreAvailable;
    public boolean privateRoomAvailable;


}
