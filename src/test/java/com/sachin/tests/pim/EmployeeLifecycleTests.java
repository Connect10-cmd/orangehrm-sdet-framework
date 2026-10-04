package com.sachin.tests.pim;

import com.fasterxml.jackson.databind.JsonNode;
import com.sachin.framework.pages.DashboardPage;
import com.sachin.framework.pages.pim.AddEmployeePage;
import com.sachin.framework.pages.pim.EmployeeListPage;
import com.sachin.framework.pages.pim.PimPage;
import com.sachin.framework.utils.TestDataReader;
import com.sachin.tests.base.AuthenticatedTest;
import org.testng.Assert;
import org.testng.annotations.Test;

import java.util.UUID;

public class EmployeeLifecycleTests extends AuthenticatedTest {

    @Test(groups = {"regression", "critical", "pim"})
    public void createSearchAndDeleteEmployee() {
        DashboardPage dashboard = loginAsConfiguredUser();
        Assert.assertTrue(dashboard.isDashboardDisplayed(), "Dashboard should be visible before PIM work");
        Assert.assertTrue(dashboard.isUserSessionAvailable(), "User session should be authenticated");

        dashboard.sideMenu().navigateToPim();
        PimPage pimPage = new PimPage();
        Assert.assertTrue(pimPage.verifyPimPageLoaded(), "PIM page should load");

        JsonNode employeeData = TestDataReader.readJson("employee-data.json");
        String firstName = employeeData.path("firstName").asText();
        String middleName = employeeData.path("middleName").asText();
        String lastName = employeeData.path("lastNamePrefix").asText()
                + UUID.randomUUID().toString().substring(0, 8);
        String fullName = String.join(" ", firstName, middleName, lastName);

        AddEmployeePage addEmployeePage = pimPage.navigateToAddEmployee();
        addEmployeePage.enterFirstName(firstName)
                .enterMiddleName(middleName)
                .enterLastName(lastName);
        addEmployeePage.saveEmployee();
        Assert.assertTrue(addEmployeePage.isEmployeeCreated(),
                "Personal Details should load after saving the employee");

        EmployeeListPage employeeListPage = new PimPage().navigateToEmployeeList();
        employeeListPage.searchEmployee(fullName);
        Assert.assertTrue(employeeListPage.employeeExists(fullName),
                "New employee should appear in the employee search results");

        employeeListPage.deleteEmployee(fullName);
        Assert.assertFalse(employeeListPage.employeeExists(fullName),
                "Deleted employee should no longer appear in search results");
    }
}
