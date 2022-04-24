package com.baraka.banking.enums;

import lombok.Getter;

import java.util.HashMap;
import java.util.Map;

@Getter
public enum AccountType {

    CURRENT_ACCOUNT("current_account"),
    SAVING_ACCOUNT("saving_account");

    private String type;

    AccountType(String type) {
        this.type = type;
    }

    private static final Map<String, AccountType> lookup = new HashMap<>();

    static {
        for (AccountType env : AccountType.values()) {
            lookup.put(env.getType(), env);
        }
    }
}
