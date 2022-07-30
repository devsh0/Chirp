package com.devsh0.chirp.util;

import com.fasterxml.jackson.databind.ObjectMapper;
import org.springframework.core.io.ClassPathResource;
import org.springframework.web.context.request.RequestContextHolder;
import org.springframework.web.context.request.ServletRequestAttributes;

import java.io.IOException;
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

    public static <T, V> MapBuilder<T, V> mapFrom(T key, V value) {
        return new MapBuilder<>(key, value);
    }

    public static String asJson(Object object) {
        try {
            return jsonMapper.writeValueAsString(object);
        } catch (IOException exc) {
            throw new RuntimeException(exc);
        }
    }

    public static Object fromJson(String json, Class<?> klass) {
        try {
            return jsonMapper.readValue(json, klass);
        } catch (IOException exc) {
            throw new RuntimeException(exc);
        }
    }

    public static String getRequestUrl() {
        var request = ((ServletRequestAttributes) RequestContextHolder.currentRequestAttributes()).getRequest();
        return request.getRequestURL().toString();
    }

    public static String getTemplateResource(String templateName) {
        try {
            String path = "templates/" + templateName;
            var templateResource = new ClassPathResource(path);
            return new String(templateResource.getInputStream().readAllBytes());
        } catch (IOException exc) {
            throw new RuntimeException(exc);
        }
    }
}
