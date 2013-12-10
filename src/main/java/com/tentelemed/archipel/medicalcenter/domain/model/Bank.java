package com.tentelemed.archipel.medicalcenter.domain.model;

import com.tentelemed.archipel.core.domain.model.BaseAggregateRoot;

import javax.validation.constraints.NotNull;

/**
 * Created with IntelliJ IDEA.
 * User: Mael
 * Date: 10/12/13
 * Time: 11:49
 */
public class Bank extends BaseAggregateRoot<BankId> {
    @NotNull String name;

    @Override
    protected Class<BankId> getIdClass() {
        return BankId.class;
    }

    public Bank(String name) {
        this.name = name;
    }

    public String getName() {
        return name;
    }
}
