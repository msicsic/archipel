package com.tentelemed.archipel.site.domain.model;

import com.tentelemed.archipel.core.domain.model.BaseVO;

import javax.persistence.Entity;
import javax.persistence.Id;
import javax.validation.constraints.NotNull;

/**
 * Created with IntelliJ IDEA.
 * User: Mael
 * Date: 10/12/13
 * Time: 11:49
 */
@Entity
public class Bank extends BaseVO {
    @NotNull String bankName;
    @Id @NotNull String code;

    Bank() {
    }

    public Bank(String code, String name) {
        this.bankName = name;
        this.code = code;
    }

    public String getBankName() {
        return bankName;
    }

    public String getCode() {
        return code;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (!(o instanceof Bank)) return false;

        Bank bank = (Bank) o;

        if (!bankName.equals(bank.bankName)) return false;
        if (!code.equals(bank.code)) return false;

        return true;
    }

    @Override
    public int hashCode() {
        int result = bankName.hashCode();
        result = 31 * result + code.hashCode();
        return result;
    }
}
