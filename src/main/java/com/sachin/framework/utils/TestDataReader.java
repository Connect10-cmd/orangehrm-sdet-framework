package com.sachin.framework.utils;

import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;

import java.io.IOException;
import java.io.InputStream;

public final class TestDataReader {

    private static final ObjectMapper JSON = new ObjectMapper();

    private TestDataReader() {
        throw new UnsupportedOperationException("Utility class");
    }

    public static JsonNode readJson(String resourceName) {
        String resourcePath = resourceName.startsWith("testdata/")
                ? resourceName : "testdata/" + resourceName;
        try (InputStream input = TestDataReader.class.getClassLoader()
                .getResourceAsStream(resourcePath)) {
            if (input == null) {
                throw new IllegalArgumentException("Test data resource not found: " + resourcePath);
            }
            return JSON.readTree(input);
        } catch (IOException exception) {
            throw new IllegalStateException("Unable to read test data resource: " + resourcePath,
                    exception);
        }
    }
}
