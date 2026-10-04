package com.sachin.framework.pages.pim;

import com.sachin.framework.components.SideMenuComponent;
import com.sachin.framework.pages.BasePage;
import org.openqa.selenium.By;

public class PimPage extends BasePage {

    // Visible page heading, independent of route implementation details.
    private static final By PIM_HEADING = By.xpath("//h6[normalize-space()='PIM']");
    private static final By ADD_EMPLOYEE_TAB =
            By.xpath("//a[normalize-space()='Add Employee']");
    private static final By EMPLOYEE_LIST_TAB =
            By.xpath("//a[normalize-space()='Employee List']");

    private final SideMenuComponent sideMenu;

    public PimPage() {
        this.sideMenu = new SideMenuComponent(waitUtils);
    }

    public SideMenuComponent sideMenu() {
        return sideMenu;
    }

    public boolean verifyPimPageLoaded() {
        return isDisplayed(PIM_HEADING);
    }

    public boolean isPimUrl() {
        return waitForUrlContains("/pim/");
    }

    public AddEmployeePage navigateToAddEmployee() {
        click(ADD_EMPLOYEE_TAB);
        return new AddEmployeePage();
    }

    public EmployeeListPage navigateToEmployeeList() {
        click(EMPLOYEE_LIST_TAB);
        return new EmployeeListPage();
    }
}
