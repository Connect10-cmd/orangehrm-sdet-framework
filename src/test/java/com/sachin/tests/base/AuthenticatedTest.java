package com.sachin.tests.base;

import com.fasterxml.jackson.databind.JsonNode;
import com.sachin.framework.pages.DashboardPage;
import com.sachin.framework.pages.LoginPage;
import com.sachin.framework.utils.TestDataReader;

public abstract class AuthenticatedTest extends BaseTest {

    protected DashboardPage loginAsConfiguredUser() {
        JsonNode credentials = TestDataReader.readJson("login-data.json").path("valid");
        LoginPage loginPage = new LoginPage();
        loginPage.login(credentials.path("username").asText(),
                credentials.path("password").asText());

        return new DashboardPage();
    }
}
