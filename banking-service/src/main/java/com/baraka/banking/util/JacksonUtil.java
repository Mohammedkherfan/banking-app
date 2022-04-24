package com.baraka.banking.util;

import com.fasterxml.jackson.databind.ObjectMapper;

import java.io.IOException;
import java.util.Map;

public class JacksonUtil {

    private static ObjectMapper mapper = new ObjectMapper();

    public static  <T> String toJson(T entity) throws IOException {
        return mapper.writeValueAsString(entity);
    }

    public static <T> T fromJson(String json, Class<T> classType) throws IOException {
        return mapper.readValue(json, classType);
    }

    public static <T> T toObject(Map<String, Object> map, Class<T> classType) {
        return mapper.convertValue(map, classType);
    }
}
