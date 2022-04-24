package com.baraka.banking.enums;

import java.util.HashMap;
import java.util.Map;
import java.util.regex.Pattern;
import java.util.stream.Stream;

public enum ParameterType {

    NUMERIC("numeric", "[^A-z]*"),
    ALPHA("alpha", "[^0-9]*"),
    ALPHANUMERIC("alphanumeric", ".*"),
    DATE("date" , "^(\\d{4})-(\\d{2})-(\\d{2})$"),
    DATE_TIME("dateTime" , "^(\\d{4})-(\\d{2})-(\\d{2})T(\\d{2}):(\\d{2}):(\\d{2})([+|-]([01][0-9]|[2][0-3])([0-5][0-9]))$");

    private static final Map<String, ParameterType> valueTypes = new HashMap<>();

    static {
        Stream.of(ParameterType.values()).forEach(status -> valueTypes.put(status.key, status));
    }

    private String key;
    private Pattern pattern;

    ParameterType(String key, String pattern) {
        this.key = key;
        this.pattern = Pattern.compile(pattern);
    }

    public Pattern getPattern() {
        return pattern;
    }
}
