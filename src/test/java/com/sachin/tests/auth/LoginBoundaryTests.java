package com.sachin.tests.auth;

import com.sachin.framework.pages.LoginPage;
import com.sachin.tests.base.BaseTest;
import com.sachin.tests.data.LoginDataProvider;
import org.testng.Assert;
import org.testng.annotations.Test;

public class LoginBoundaryTests extends BaseTest {

    @Test(
            groups = {"regression", "authentication"},
            dataProvider = "boundaryLoginData",
            dataProviderClass = LoginDataProvider.class
    )
    public void verifyBoundaryLogin(
            String username,
            String password,
            int expectedErrorCount
    ) {

        LoginPage loginPage = new LoginPage();

        loginPage.login(username, password);

        Assert.assertEquals(loginPage.getValidationMessages().size(), expectedErrorCount,
                "Required-field errors should correspond to the empty inputs");
    }
}