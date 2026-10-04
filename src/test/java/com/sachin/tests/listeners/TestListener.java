package com.sachin.tests.listeners;

import com.sachin.framework.driver.DriverFactory;
import com.sachin.framework.utils.ScreenshotUtils;
import org.openqa.selenium.HasCapabilities;
import org.openqa.selenium.WebDriver;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.testng.ITestContext;
import org.testng.ITestListener;
import org.testng.ITestResult;

import java.io.IOException;
import java.nio.file.Path;

public class TestListener implements ITestListener {

    private static final Logger LOGGER = LoggerFactory.getLogger(TestListener.class);
    private static final String START_NANOS = "executionStartNanos";
    public static final String SCREENSHOT_PATH = "failureScreenshotPath";
    public static final String FAILURE_URL = "failureUrl";
    public static final String BROWSER_INFO = "browserInfo";
    public static final String FAILURE_EXCEPTION = "failureException";
    public static final String EXECUTION_MILLIS = "executionMillis";

    @Override
    public void onTestStart(ITestResult result) {
        result.setAttribute(START_NANOS, System.nanoTime());
        LOGGER.info("Starting test {}", testName(result));
    }

    @Override
    public void onTestSuccess(ITestResult result) {
        recordDuration(result);
        LOGGER.info("Passed test {} ({} ms)", testName(result),
                result.getAttribute(EXECUTION_MILLIS));
    }

    @Override
    public void onTestFailure(ITestResult result) {
        recordDuration(result);
        Throwable failure = result.getThrowable();
        result.setAttribute(FAILURE_EXCEPTION, failure);
        LOGGER.error("Failed test {} ({} ms): {}", testName(result),
                result.getAttribute(EXECUTION_MILLIS),
                failure == null ? "unknown failure" : failure.getMessage(), failure);

        WebDriver driver = currentDriverOrNull();
        if (driver == null) {
            LOGGER.warn("No active WebDriver; skipping failure diagnostics for {}", testName(result));
            return;
        }

        try {
            result.setAttribute(FAILURE_URL, driver.getCurrentUrl());
        } catch (RuntimeException diagnosticFailure) {
            LOGGER.warn("Unable to read current URL for failed test {}", testName(result),
                    diagnosticFailure);
        }
        try {
            if (driver instanceof HasCapabilities hasCapabilities) {
                result.setAttribute(BROWSER_INFO, hasCapabilities.getCapabilities().getBrowserName()
                        + " " + hasCapabilities.getCapabilities().getBrowserVersion());
            }
        } catch (RuntimeException diagnosticFailure) {
            LOGGER.warn("Unable to read browser capabilities for failed test {}", testName(result),
                    diagnosticFailure);
        }
        try {
            Path screenshot = ScreenshotUtils.captureFailure(driver, testName(result));
            result.setAttribute(SCREENSHOT_PATH, screenshot.toString());
            LOGGER.error("Failure screenshot saved to {}", screenshot);
        } catch (IOException | RuntimeException diagnosticFailure) {
            LOGGER.error("Unable to capture failure diagnostics for {}", testName(result),
                    diagnosticFailure);
        }
    }

    @Override
    public void onTestSkipped(ITestResult result) {
        recordDuration(result);
        LOGGER.warn("Skipped test {}: {}", testName(result), result.getThrowable());
    }

    @Override
    public void onStart(ITestContext context) {
        LOGGER.info("Starting TestNG context {}", context.getName());
    }

    @Override
    public void onFinish(ITestContext context) {
        LOGGER.info("Finished TestNG context {}", context.getName());
    }

    private static String testName(ITestResult result) {
        return result.getTestClass().getName() + "." + result.getMethod().getMethodName();
    }

    private static WebDriver currentDriverOrNull() {
        try {
            return DriverFactory.getDriver();
        } catch (IllegalStateException exception) {
            return null;
        }
    }

    private static void recordDuration(ITestResult result) {
        Object start = result.getAttribute(START_NANOS);
        if (start instanceof Long startNanos) {
            result.setAttribute(EXECUTION_MILLIS, (System.nanoTime() - startNanos) / 1_000_000L);
        }
    }
}
