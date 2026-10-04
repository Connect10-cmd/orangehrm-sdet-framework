package com.sachin.framework.pages;

import org.openqa.selenium.By;
import org.openqa.selenium.JavascriptExecutor;
import org.openqa.selenium.TimeoutException;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.ui.ExpectedConditions;

import java.util.List;

public class LoginPage extends BasePage {

    private final By usernameInput = By.name("username");
    private final By passwordInput = By.name("password");
    private final By loginButton = By.cssSelector("button[type='submit']");
    private final By errorMessage = By.cssSelector(".oxd-alert-content-text");
    private final By validationMessages = By.cssSelector(".oxd-input-field-error-message");

    public LoginPage() {
        try {
            wait.until(ExpectedConditions.and(
                ExpectedConditions.urlContains("/auth/login"),
                ExpectedConditions.visibilityOfElementLocated(usernameInput),
                ExpectedConditions.visibilityOfElementLocated(passwordInput)
            ));
        } catch (TimeoutException exception) {
            throw new TimeoutException(
                "The URL may have loaded, but the login page did not become ready within "
                    + "the configured explicit wait: the username field was not visible "
                    + "(and the password field may also be unavailable). The public demo "
                    + "may be slow or the application may be temporarily unavailable. "
                    + "Expected /auth/login with visible username and password fields. "
                    + describePageState(),
                exception);
        }
    }

    public LoginPage enterUsername(String username) {
        type(usernameInput, username);
        return this;
    }

    public LoginPage enterPassword(String password) {
        type(passwordInput, password);
        return this;
    }

    public void clickLogin() {
        click(loginButton);
    }

    public void login(String username, String password) {

        enterUsername(username);
        enterPassword(password);
        clickLogin();
    }

    public String getErrorMessage() {
        return getText(errorMessage);
    }

    public List<String> getValidationMessages() {
        waitForVisible(validationMessages);
        return driver.findElements(validationMessages).stream()
                .map(WebElement::getText)
                .toList();
    }

    public boolean isLoginPageDisplayed() {
        return isDisplayed(usernameInput) && isDisplayed(passwordInput);
    }

    public boolean isLoginUrl() {
        return driver.getCurrentUrl().contains("/auth/login");
    }

    private String describePageState() {
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