package com.sachin.tests.base;

import com.sachin.framework.config.ConfigReader;
import com.sachin.framework.driver.DriverFactory;
import org.testng.annotations.AfterMethod;
import org.testng.annotations.BeforeMethod;

public class BaseTest {

    @BeforeMethod
    public void setUp() {

        DriverFactory.initDriver();

        DriverFactory.getDriver().get(
                ConfigReader.getInstance().getProperty("url")
        );
    }

    @AfterMethod
    public void tearDown() {
        DriverFactory.quitDriver();
    }
}