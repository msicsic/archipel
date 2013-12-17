package com.tentelemed.archipel.medicalcenter.domain.model;

import com.tentelemed.archipel.core.domain.model.BaseVO;

import javax.persistence.Embeddable;
import javax.validation.constraints.NotNull;

/**
 * Created with IntelliJ IDEA.
 * User: Mael
 * Date: 10/12/13
 * Time: 11:49
 */
@Embeddable
public class Bank extends BaseVO {
    @NotNull String bankName;
    @NotNull String code;

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
}
