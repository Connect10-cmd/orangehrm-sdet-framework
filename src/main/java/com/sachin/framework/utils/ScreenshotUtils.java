package com.sachin.framework.utils;

import com.sachin.framework.constants.FrameworkConstants;
import org.openqa.selenium.OutputType;
import org.openqa.selenium.TakesScreenshot;
import org.openqa.selenium.WebDriver;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.StandardCopyOption;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.UUID;

public final class ScreenshotUtils {

    private static final DateTimeFormatter TIMESTAMP_FORMAT =
            DateTimeFormatter.ofPattern("yyyyMMdd_HHmmss_SSS");

    private ScreenshotUtils() {
        throw new UnsupportedOperationException("Utility class");
    }

    public static Path captureFailure(WebDriver driver, String testName) throws IOException {
        return capture(driver, testName, "failures");
    }

    public static Path capture(WebDriver driver, String testName, String category) throws IOException {
        if (!"failures".equals(category) && !"passes".equals(category)) {
            throw new IllegalArgumentException("Unsupported screenshot category: " + category);
        }
        String safeTestName = testName.replaceAll("[^a-zA-Z0-9._-]", "_");
        String fileName = safeTestName + "_" + LocalDateTime.now().format(TIMESTAMP_FORMAT)
                + "_" + UUID.randomUUID().toString().substring(0, 8) + ".png";
        Path directory = Path.of(FrameworkConstants.SCREENSHOT_DIRECTORY, category);
        Files.createDirectories(directory);

        Path destination = directory.resolve(fileName);
        Files.copy(((TakesScreenshot) driver).getScreenshotAs(OutputType.FILE).toPath(),
                destination, StandardCopyOption.REPLACE_EXISTING);
        return destination.toAbsolutePath();
    }
}
