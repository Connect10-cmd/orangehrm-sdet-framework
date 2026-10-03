package com.sachin.framework.pages;

import com.sachin.framework.driver.DriverFactory;
import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;

import java.time.Duration;

public class DashboardPage {

    private final WebDriver driver;
    private final WebDriverWait wait;

    private final By dashboardHeader =
            By.xpath("//h6[normalize-space()='Dashboard']");

    private final By userDropdown =
            By.cssSelector(".oxd-userdropdown-name");

    private final By logoutLink =
            By.xpath("//a[normalize-space()='Logout']");

    public DashboardPage() {
        this.driver = DriverFactory.getDriver();
        this.wait = new WebDriverWait(driver, Duration.ofSeconds(20));
    }

    public boolean isDashboardDisplayed() {

        return wait.until(
                ExpectedConditions.visibilityOfElementLocated(dashboardHeader)
        ).isDisplayed();
    }

    public String getCurrentUrl() {
        return driver.getCurrentUrl();
    }

    public boolean isDashboardUrl() {
        return driver.getCurrentUrl().contains("dashboard");
    }

    public void clickUserDropdown() {

        wait.until(
                ExpectedConditions.elementToBeClickable(userDropdown)
        ).click();
    }

    public void clickLogout() {

        wait.until(
                ExpectedConditions.elementToBeClickable(logoutLink)
        ).click();
    }

    public void logout() {

        clickUserDropdown();
        clickLogout();
    }
}