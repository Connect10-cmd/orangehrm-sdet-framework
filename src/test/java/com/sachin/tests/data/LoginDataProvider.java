package com.sachin.tests.data;

import com.fasterxml.jackson.databind.JsonNode;
import com.sachin.framework.utils.TestDataReader;
import org.testng.annotations.DataProvider;

public class LoginDataProvider {

    @DataProvider(name = "invalidLoginData")
    public Object[][] invalidLoginData() {
        JsonNode cases = TestDataReader.readJson("login-data.json").path("invalid");
        Object[][] data = new Object[cases.size()][2];
        for (int index = 0; index < cases.size(); index++) {
            data[index][0] = cases.get(index).path("username").asText();
            data[index][1] = cases.get(index).path("password").asText();
        }
        return data;
    }

    @DataProvider(name = "boundaryLoginData")
    public Object[][] boundaryLoginData() {
        JsonNode cases = TestDataReader.readJson("login-data.json").path("validation");
        Object[][] data = new Object[cases.size()][3];
        for (int index = 0; index < cases.size(); index++) {
            JsonNode testCase = cases.get(index);
            data[index][0] = testCase.path("username").asText();
            data[index][1] = testCase.path("password").asText();
            data[index][2] = testCase.path("expectedErrorCount").asInt();
        }
        return data;
    }
}