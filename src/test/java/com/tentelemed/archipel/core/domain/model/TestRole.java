package com.tentelemed.archipel.core.domain.model;

/**
 * Created with IntelliJ IDEA.
 * User: Mael
 * Date: 05/12/13
 * Time: 14:42
 */
public class TestRole extends BaseVO {
    private String name;

    private TestRole() {}

    public TestRole(String name) {
        this.name = name;
    }

    public String getName() {
        return name;
    }
}
