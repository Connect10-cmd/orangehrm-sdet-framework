package com.sachin.tests.login;

import com.sachin.framework.pages.LoginPage;
import com.sachin.tests.base.BaseTest;
import org.testng.Assert;
import org.testng.annotations.Test;

public class LoginSmokeTest extends BaseTest {

    @Test(groups = {"smoke", "authentication"})
    public void verifyLoginPageLoads() {
        LoginPage loginPage = new LoginPage();
        Assert.assertTrue(loginPage.isLoginPageDisplayed(), "Login form should be visible");
        Assert.assertTrue(loginPage.isLoginUrl(), "Browser should be on the login URL");
    }
}