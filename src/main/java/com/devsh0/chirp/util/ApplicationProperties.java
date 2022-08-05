package com.devsh0.chirp.util;

import java.io.IOException;
import java.util.Properties;

public class ApplicationProperties {
    public static final String CLIENT_ADDRESS = "http://localhost:3000";
    private static final Properties APP_PROPERTIES;

    private ApplicationProperties() {

    }

    static {
        try {
            var properties = new Properties();
            var stream = ApplicationProperties.class.getClassLoader().getResourceAsStream("application.properties");
            properties.load(stream);
            APP_PROPERTIES = properties;
        } catch (IOException exception) {
            throw new RuntimeException(exception);
        }
    }

    public static String get(String propertyName) {
        return APP_PROPERTIES.getProperty(propertyName);
    }
}
