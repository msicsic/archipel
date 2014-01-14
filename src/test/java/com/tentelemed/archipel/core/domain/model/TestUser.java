package com.tentelemed.archipel.core.domain.model;

import java.util.ArrayList;
import java.util.List;

/**
 * Created with IntelliJ IDEA.
 * User: Mael
 * Date: 05/12/13
 * Time: 14:40
 */
public class TestUser extends BaseAggregateRoot<TestUserId> {
    private String firstName;
    private String lastName;
    private String login;
    private TestRole role;
    private TestAddress address;
    private TestUser tutor;
    private List<TestUser> children = new ArrayList<>();

    TestUser() {
    }

    public TestUser(TestUserId id, TestRole role, String firstName, String lastName, String login) {
        this.id = id.getId();
        this.firstName = firstName;
        this.lastName = lastName;
        this.login = login;
        this.role = role;
    }

    public TestRole getRole() {
        return role;
    }

    public TestAddress getAddress() {
        return address;
    }

    public void setAddress(TestAddress address) {
        this.address = address;
    }

    public TestUser getTutor() {
        return tutor;
    }

    public void setTutor(TestUser tutor) {
        this.tutor = tutor;
    }

    public List<TestUser> getChildren() {
        return children;
    }

    public void setChildren(List<TestUser> children) {
        this.children = children;
    }

    public String getFirstName() {
        return firstName;
    }

    public String getLastName() {
        return lastName;
    }

    public String getLogin() {
        return login;
    }

    @Override
    protected Class<TestUserId> getIdClass() {
        return TestUserId.class;
    }

    public void addChild(TestUser user) {
        if (!children.contains(user)) {
            children.add(user);
            user.setTutor(this);
        }
    }

    public void changeName(String firstName, String lastName) {
        this.firstName = firstName;
        this.lastName = lastName;
    }
}
