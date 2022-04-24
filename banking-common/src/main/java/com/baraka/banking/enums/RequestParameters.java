package com.baraka.banking.enums;

import lombok.Getter;

@Getter
public enum RequestParameters {

    OPERATION("operation");

    private String parameter;

    RequestParameters(String parameter) {
        this.parameter = parameter;
    }
}
