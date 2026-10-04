package com.sachin.tests.base;

import com.sachin.framework.config.ConfigReader;
import com.sachin.framework.constants.FrameworkConstants;
import com.sachin.framework.driver.DriverFactory;
import com.sachin.framework.utils.WaitUtils;
import com.sachin.tests.listeners.TestListener;
import org.openqa.selenium.JavascriptExecutor;
import org.openqa.selenium.WebDriver;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.testng.annotations.Listeners;
import org.testng.annotations.AfterMethod;
import org.testng.annotations.BeforeMethod;

@Listeners(TestListener.class)
public class BaseTest {

    private static final Logger LOGGER = LoggerFactory.getLogger(BaseTest.class);

    @BeforeMethod(alwaysRun = true)
    public void setUp() {
        DriverFactory.initDriver();
        String url = ConfigReader.getInstance().getProperty("url");
        WebDriver driver = DriverFactory.getDriver();
        try {
            LOGGER.info("Opening application URL {}", url);
            driver.get(url);
            new WaitUtils(driver, FrameworkConstants.PAGE_READY_WAIT_SECONDS)
                .waitForDocumentReady();
            LOGGER.info("Application document loaded: {}", driver.getCurrentUrl());
        } catch (RuntimeException exception) {
            String pageState = describePageState(driver);
            DriverFactory.quitDriver();
            throw new IllegalStateException(
                "Unable to load application page at " + url + " (environment: "
                    + ConfigReader.getInstance().getEnvironment() + "). " + pageState,
                exception);
        }
    }

    @AfterMethod(alwaysRun = true)
    public void tearDown() {
        DriverFactory.quitDriver();
    }

    private String describePageState(WebDriver driver) {
        try {
            Object readyState = ((JavascriptExecutor) driver)
                    .executeScript("return document.readyState");
            return "Current URL: " + driver.getCurrentUrl() + ", title: " + driver.getTitle()
                    + ", document.readyState: " + readyState;
        } catch (RuntimeException diagnosticException) {
            return "Page diagnostics unavailable: " + diagnosticException.getMessage();
        }
    }
}