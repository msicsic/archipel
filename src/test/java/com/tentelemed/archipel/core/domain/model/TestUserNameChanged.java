package com.tentelemed.archipel.core.domain.model;

import com.tentelemed.archipel.core.domain.pub.AbstractDomainEvent;

/**
 * Created with IntelliJ IDEA.
 * User: Mael
 * Date: 05/12/13
 * Time: 16:57
 */
public class TestUserNameChanged extends AbstractDomainEvent<TestUserId> {
    private String firstName;
    private String lastName;

    TestUserNameChanged() {
    }

    public TestUserNameChanged(String firstName, String lastName) {
        this.firstName = firstName;
        this.lastName = lastName;
    }

    public String getFirstName() {
        return firstName;
    }

    public String getLastName() {
        return lastName;
    }
}
