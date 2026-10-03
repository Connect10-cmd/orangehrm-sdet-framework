package com.sachin.tests.auth;

import com.sachin.framework.pages.LoginPage;
import com.sachin.tests.base.BaseTest;
import com.sachin.tests.data.LoginDataProvider;
import org.testng.Assert;
import org.testng.annotations.Test;

public class LoginNegativeTests extends BaseTest {

    @Test(
            groups = {"regression","negative"},
            dataProvider = "invalidLoginData",
            dataProviderClass = LoginDataProvider.class
    )
    public void verifyInvalidLogin(
            String username,
            String password
    ) {

        LoginPage loginPage = new LoginPage();

        loginPage.login(username, password);

        Assert.assertTrue(
                loginPage.getErrorMessage().contains("Invalid"),
                "Expected invalid credentials message"
        );
    }
}