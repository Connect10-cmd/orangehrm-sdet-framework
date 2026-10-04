package com.sachin.tests.login;

import com.fasterxml.jackson.databind.JsonNode;
import com.sachin.framework.pages.DashboardPage;
import com.sachin.framework.pages.LoginPage;
import com.sachin.framework.utils.TestDataReader;
import com.sachin.tests.base.BaseTest;
import org.testng.Assert;
import org.testng.annotations.Test;

public class LoginTests extends BaseTest {

    @Test(groups = {"regression", "authentication"})
    public void verifyLogoutReturnsToLoginPage() {
        JsonNode credentials = TestDataReader.readJson("login-data.json").path("valid");
        LoginPage loginPage = new LoginPage();
        loginPage.login(credentials.path("username").asText(),
                credentials.path("password").asText());

        DashboardPage dashboardPage = new DashboardPage();
        Assert.assertTrue(dashboardPage.isDashboardDisplayed(), "Dashboard should load before logout");
        dashboardPage.logout();

        LoginPage loggedOutPage = new LoginPage();
        Assert.assertTrue(loggedOutPage.isLoginPageDisplayed(), "Login form should return after logout");
        Assert.assertTrue(loggedOutPage.isLoginUrl(), "Logout should clear the authenticated route");
    }
}