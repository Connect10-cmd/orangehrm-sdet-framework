package com.sachin.framework.utils;

import org.openqa.selenium.By;
import org.openqa.selenium.JavascriptExecutor;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;

import java.time.Duration;

public final class WaitUtils {

    private final WebDriverWait wait;

    public WaitUtils(WebDriver driver, int timeoutSeconds) {
        this(new WebDriverWait(driver, Duration.ofSeconds(timeoutSeconds)));
    }

    public WaitUtils(WebDriverWait wait) {
        this.wait = wait;
    }

    public WebElement waitForVisible(By locator) {
        return wait.until(ExpectedConditions.visibilityOfElementLocated(locator));
    }

    public WebElement waitForClickable(By locator) {
        return wait.until(ExpectedConditions.elementToBeClickable(locator));
    }

    public WebElement waitForPresence(By locator) {
        return wait.until(ExpectedConditions.presenceOfElementLocated(locator));
    }

    public boolean waitForUrl(String urlFragment) {
        return wait.until(ExpectedConditions.urlContains(urlFragment));
    }

    public boolean waitForDocumentReady() {
        return wait.until(driver -> "complete".equals(
                ((JavascriptExecutor) driver).executeScript("return document.readyState")));
    }
}
