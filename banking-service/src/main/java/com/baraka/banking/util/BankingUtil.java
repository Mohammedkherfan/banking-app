package com.baraka.banking.util;

import com.baraka.banking.enums.RequestParameters;
import com.baraka.banking.exception.BankingException;

import java.util.Map;
import java.util.Optional;

public class BankingUtil {

    public static String getOperation(Map<String, Object> request) {
        return Optional.of(request)
                .map(o -> o.get(RequestParameters.OPERATION.getParameter()))
                .map(Object::toString)
                .orElseThrow((() -> new BankingException("Invalid operation")));
    }
}
