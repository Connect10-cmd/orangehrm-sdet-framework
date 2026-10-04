package com.sachin.framework.pages;

import com.sachin.framework.driver.DriverFactory;
import com.sachin.framework.config.ConfigReader;
import com.sachin.framework.constants.FrameworkConstants;
import com.sachin.framework.utils.WaitUtils;
import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.ui.WebDriverWait;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.time.Duration;

public abstract class BasePage {

    private static final Logger LOGGER = LoggerFactory.getLogger(BasePage.class);
    protected final WebDriver driver;
    protected final WebDriverWait wait;
    protected final WaitUtils waitUtils;

    protected BasePage() {
        this(DriverFactory.getDriver());
    }

    protected BasePage(WebDriver driver) {
        if (driver == null) {
            throw new IllegalStateException("WebDriver has not been initialized for this thread");
        }
        this.driver = driver;
        this.wait = new WebDriverWait(
            driver,
            Duration.ofSeconds(ConfigReader.getInstance().getIntProperty(
                        "explicitWait", FrameworkConstants.DEFAULT_WAIT_SECONDS
                ))
        );
        this.waitUtils = new WaitUtils(wait);
    }

    protected void click(By locator) {
        LOGGER.debug("Clicking element located by {}", locator);
        waitUtils.waitForClickable(locator).click();
    }

    protected void type(By locator, String text) {
        LOGGER.debug("Typing into element located by {}", locator);
        WebElement element = waitUtils.waitForVisible(locator);
        element.clear();
        element.sendKeys(text);
    }

    protected String getText(By locator) {
        return waitUtils.waitForVisible(locator).getText();
    }

    protected WebElement waitForVisible(By locator) {
        return waitUtils.waitForVisible(locator);
    }

    protected WebElement waitForClickable(By locator) {
        return waitUtils.waitForClickable(locator);
    }

    protected WebElement waitForPresence(By locator) {
        return waitUtils.waitForPresence(locator);
    }

    protected boolean waitForUrlContains(String urlFragment) {
        return waitUtils.waitForUrl(urlFragment);
    }

    protected boolean isDisplayed(By locator) {
        try {
            return waitUtils.waitForVisible(locator).isDisplayed();
        } catch (org.openqa.selenium.TimeoutException exception) {
            return false;
        }
    }
}
