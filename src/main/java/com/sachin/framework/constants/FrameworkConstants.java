package com.sachin.framework.constants;

public final class FrameworkConstants {

    public static final int DEFAULT_WAIT_SECONDS = 20;
    public static final int PAGE_READY_WAIT_SECONDS = 5;
    public static final String DEFAULT_ENVIRONMENT = "dev";
    public static final String SCREENSHOT_DIRECTORY = "screenshots";
    public static final String TEST_DATA_DIRECTORY = "testdata/";

    private FrameworkConstants() {
        throw new UnsupportedOperationException("Utility class");
    }
}
