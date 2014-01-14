package com.tentelemed.archipel.security.domain.pub;

/**
 * Created with IntelliJ IDEA.
 * User: Mael
 * Date: 15/11/13
 * Time: 16:10
 */
public class EvtUserPasswordUpdated extends EvtUserDomainEvent {
    private String password;

    EvtUserPasswordUpdated() {
    }

    public EvtUserPasswordUpdated(UserId id, String newPassword) {
        super(id);
        this.password = newPassword;
    }

    public String getPassword() {
        return password;
    }
}
