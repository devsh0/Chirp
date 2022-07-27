package com.devsh0.chirp.util;

import com.fasterxml.jackson.databind.ObjectMapper;

import java.util.HashMap;
import java.util.Map;

public class Utils {
    private static final ObjectMapper jsonMapper = new ObjectMapper();
    public static class MapBuilder<T, V> {
        private final Map<T, V> map = new HashMap<>();

        MapBuilder(T key, V value) {
            this.map.put(key, value);
        }

        public MapBuilder<T, V> put(T key, V value) {
            this.map.put(key, value);
            return this;
        }

        public Map<T, V> get() {
            return map;
        }
    }

    public static <T, V> Map<T, V> emptyMap() {
        return new HashMap<>();
    }

    public static <T, V> MapBuilder<T, V> mapOf(T key, V value) {
        return new MapBuilder<>(key, value);
    }

    public static String asJson(Object object) throws Exception {
        return jsonMapper.writeValueAsString(object);
    }

    public static Object fromJson(String json, Class<?> klass) throws Exception {
        return jsonMapper.readValue(json, klass);
    }
}
