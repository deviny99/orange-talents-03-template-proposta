package com.zup.academy.global.utils;

import com.fasterxml.jackson.databind.ObjectMapper;

public class JsonMapper {

    public static String asJsonString(final Object obj) {
        try {
            final ObjectMapper mapper = new ObjectMapper();
            final String jsonContent = mapper.writeValueAsString(obj);
            return jsonContent;
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
    }

    public static Object asJsonObject(final String obj, Class<?> clazz) {
        try {
            final ObjectMapper mapper = new ObjectMapper();
            final Object object = mapper.readValue(obj,clazz);
            return object;
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
    }
}
