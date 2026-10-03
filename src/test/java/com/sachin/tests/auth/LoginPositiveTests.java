package com.sachin.tests.auth;

import com.sachin.framework.pages.DashboardPage;
import com.sachin.framework.pages.LoginPage;
import com.sachin.tests.base.BaseTest;
import org.testng.Assert;
import org.testng.annotations.Test;

public class LoginPositiveTests extends BaseTest {

    @Test(groups = {"smoke","positive"})
    public void verifyValidLogin() {

        LoginPage loginPage = new LoginPage();

        loginPage.login("Admin", "admin123");

        DashboardPage dashboardPage = new DashboardPage();

        Assert.assertTrue(
                dashboardPage.isDashboardDisplayed(),
                "Dashboard should be visible after login"
        );
    }
}