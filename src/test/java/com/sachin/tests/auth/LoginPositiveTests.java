package com.sachin.tests.auth;

import com.fasterxml.jackson.databind.JsonNode;
import com.sachin.framework.pages.DashboardPage;
import com.sachin.framework.pages.LoginPage;
import com.sachin.framework.utils.TestDataReader;
import com.sachin.tests.base.BaseTest;
import org.testng.Assert;
import org.testng.annotations.Test;

public class LoginPositiveTests extends BaseTest {

        @Test(groups = {"smoke", "sanity", "critical", "authentication"})
    public void verifyValidLogin() {
        JsonNode credentials = TestDataReader.readJson("login-data.json").path("valid");
        LoginPage loginPage = new LoginPage();
        loginPage.login(credentials.path("username").asText(),
            credentials.path("password").asText());
        DashboardPage dashboardPage = new DashboardPage();
        Assert.assertTrue(dashboardPage.isDashboardDisplayed(), "Dashboard UI should be visible");
        Assert.assertTrue(dashboardPage.isDashboardUrl(), "Dashboard URL should be loaded");
        Assert.assertTrue(dashboardPage.isUserSessionAvailable(),
            "User menu should confirm an authenticated session");
    }
}