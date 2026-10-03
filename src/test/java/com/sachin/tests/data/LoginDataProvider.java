package com.sachin.tests.data;

import org.testng.annotations.DataProvider;

public class LoginDataProvider {

    @DataProvider(name = "invalidLoginData")
    public Object[][] invalidLoginData() {

        return new Object[][]{

                {"Admin", "wrongPassword"},
                {"WrongUser", "admin123"},
                {"WrongUser", "wrongPassword"}

        };
    }

    @DataProvider(name = "boundaryLoginData")
    public Object[][] boundaryLoginData() {

        return new Object[][]{

                {"", ""},
                {"Admin", ""},
                {"", "admin123"},
                {" Admin", "admin123"},
                {"Admin ", "admin123"}

        };
    }
}