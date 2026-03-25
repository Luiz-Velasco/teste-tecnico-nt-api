package com.dogapi.config;

import java.io.IOException;
import java.io.InputStream;
import java.util.Arrays;
import java.util.List;
import java.util.Properties;
import java.util.stream.Collectors;

public final class TestConfig {
    private static final Properties PROPERTIES = new Properties();

    static {
        try (InputStream input = TestConfig.class.getClassLoader().getResourceAsStream("test-data.properties")) {
            if (input == null) {
                throw new IllegalStateException("Could not find test-data.properties in classpath");
            }
            PROPERTIES.load(input);
        } catch (IOException e) {
            throw new RuntimeException("Failed to load test-data.properties", e);
        }
    }

    private TestConfig() {
    }

    public static String baseUrl() {
        return PROPERTIES.getProperty("base.url");
    }

    public static String validBreed() {
        return PROPERTIES.getProperty("valid.breed");
    }

    public static List<String> validBreeds() {
        return Arrays.stream(PROPERTIES.getProperty("valid.breeds", validBreed()).split(","))
                .map(String::trim)
                .filter(value -> !value.isEmpty())
                .collect(Collectors.toList());
    }

    public static String invalidBreed() {
        return PROPERTIES.getProperty("invalid.breed");
    }

    public static long maxResponseTimeMs() {
        return Long.parseLong(PROPERTIES.getProperty("max.response.time.ms"));
    }

    public static String expectedContentType() {
        return PROPERTIES.getProperty("expected.content.type");
    }
}
