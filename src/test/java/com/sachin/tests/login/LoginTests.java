package com.sachin.tests.login;

import com.sachin.framework.pages.DashboardPage;
import com.sachin.framework.pages.LoginPage;
import com.sachin.tests.base.BaseTest;
import org.testng.Assert;
import org.testng.annotations.Test;

public class LoginTests extends BaseTest {

    @Test
    public void verifyValidLogin() {

        LoginPage loginPage = new LoginPage();

        loginPage.login(
                "Admin",
                "admin123"
        );

        DashboardPage dashboardPage =
                new DashboardPage();

        Assert.assertTrue(
                dashboardPage.isDashboardDisplayed()
        );
    }
}