package com.tentelemed.archipel.site.domain.pub;


/**
 * Created with IntelliJ IDEA.
 * User: Mael
 * Date: 10/12/13
 * Time: 14:53
 */
public class EvtSiteFunctionalUnitDeleted extends EvtSiteDomainEvent {
    String code;

    EvtSiteFunctionalUnitDeleted() {
    }

    public EvtSiteFunctionalUnitDeleted(SiteId id, String code) {
        super(id);
        this.code = code;
    }

    public String getCode() {
        return code;
    }
}
