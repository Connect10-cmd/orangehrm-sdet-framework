package com.sachin.tests.login;

import com.sachin.framework.driver.DriverFactory;
import com.sachin.tests.base.BaseTest;
import org.testng.Assert;
import org.testng.annotations.Test;

public class LoginSmokeTest extends BaseTest {

    @Test
    public void verifyLoginPageLoads() {

        String currentUrl =
                DriverFactory.getDriver().getCurrentUrl();

        Assert.assertTrue(
                currentUrl.contains("orangehrm")
        );
    }
}