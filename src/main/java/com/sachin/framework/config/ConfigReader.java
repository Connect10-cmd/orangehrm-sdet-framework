package com.sachin.framework.config;

import java.io.InputStream;
import java.util.Properties;

public class ConfigReader {

    private static ConfigReader instance;
    private final Properties properties;

    private ConfigReader() {
        properties = new Properties();

        try (InputStream inputStream =
                     getClass().getClassLoader().getResourceAsStream("config.properties")) {

            if (inputStream == null) {
                throw new RuntimeException("config.properties not found");
            }

            properties.load(inputStream);

        } catch (Exception e) {
            throw new RuntimeException("Failed to load config.properties", e);
        }
    }

    public static ConfigReader getInstance() {
        if (instance == null) {
            instance = new ConfigReader();
        }
        return instance;
    }

    public String getProperty(String key) {
        return properties.getProperty(key);
    }
}