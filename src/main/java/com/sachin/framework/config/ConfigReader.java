package com.sachin.framework.config;

import com.sachin.framework.constants.FrameworkConstants;
import java.io.InputStream;
import java.util.Locale;
import java.util.Properties;

public class ConfigReader {

    private static final String ENVIRONMENT = System.getProperty(
            "env", FrameworkConstants.DEFAULT_ENVIRONMENT
    ).toLowerCase(Locale.ROOT);
    private static final ConfigReader INSTANCE = new ConfigReader();
    private final Properties properties;

    private ConfigReader() {
        properties = new Properties();

        try (InputStream inputStream =
                     getClass().getClassLoader().getResourceAsStream("config/" + ENVIRONMENT + ".properties")) {

            if (inputStream == null) {
                throw new IllegalArgumentException("Configuration not found for environment: " + ENVIRONMENT);
            }

            properties.load(inputStream);

        } catch (Exception e) {
            throw new RuntimeException("Failed to load config.properties", e);
        }
    }

    public static ConfigReader getInstance() {
        return INSTANCE;
    }

    public String getProperty(String key) {
        return System.getProperty(key, properties.getProperty(key));
    }

    public int getIntProperty(String key, int defaultValue) {
        String value = getProperty(key);
        return value == null ? defaultValue : Integer.parseInt(value);
    }

    public String getEnvironment() {
        return ENVIRONMENT;
    }
}