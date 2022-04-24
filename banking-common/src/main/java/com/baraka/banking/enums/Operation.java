package com.baraka.banking.enums;

import lombok.Getter;

import java.util.HashMap;
import java.util.Map;

@Getter
public enum Operation {

    CREATE_ACCOUNT("create_account"),
    DELETE_ACCOUNT("delete_account"),
    MONEY_DEPOSIT("money_deposit"),
    MONEY_WITHDRAWAL("money_withdrawal"),
    MONEY_LOCAL_TRANSFER("money_local_transfer"),
    MONEY_INTERNATIONAL_TRANSFER("money_international_transfer"),
    GET_BALANCE("get_balance"),
    LIST_ACCOUNTS("list_accounts");

    private String code;

    Operation(String code) {
        this.code = code;
    }

    private static final Map<String, Operation> lookup = new HashMap<>();

    static {
        for (Operation env : Operation.values()) {
            lookup.put(env.getCode(), env);
        }
    }

    public static Operation get(String code) {
        return lookup.get(code);
    }
}
