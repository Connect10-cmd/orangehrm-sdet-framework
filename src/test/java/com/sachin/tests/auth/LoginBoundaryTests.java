package com.sachin.tests.auth;

import com.sachin.framework.pages.LoginPage;
import com.sachin.tests.base.BaseTest;
import com.sachin.tests.data.LoginDataProvider;
import org.testng.Assert;
import org.testng.annotations.Test;

public class LoginBoundaryTests extends BaseTest {

    @Test(
            groups = {"regression","boundary"},
            dataProvider = "boundaryLoginData",
            dataProviderClass = LoginDataProvider.class
    )
    public void verifyBoundaryLogin(
            String username,
            String password
    ) {

        LoginPage loginPage = new LoginPage();

        loginPage.login(username, password);

        Assert.assertFalse(
                loginPage.getErrorMessage().isEmpty()
        );
    }
}