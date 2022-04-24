package com.baraka.banking.util;

import org.apache.commons.lang3.RandomStringUtils;

public class RandomStringGeneratorUtil {

    public static String generate(Integer length, Boolean letters, Boolean numbers) {
        String value = RandomStringUtils.random(length, letters, numbers);
        return value;
    }
}
