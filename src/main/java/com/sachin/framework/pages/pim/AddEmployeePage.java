package com.sachin.framework.pages.pim;

import com.sachin.framework.pages.BasePage;
import org.openqa.selenium.By;

public class AddEmployeePage extends BasePage {

    // Name attributes are application form semantics and are more stable than generated IDs.
    private static final By FIRST_NAME = By.name("firstName");
    private static final By MIDDLE_NAME = By.name("middleName");
    private static final By LAST_NAME = By.name("lastName");
    private static final By SAVE_BUTTON = By.cssSelector("button[type='submit']");
    private static final By PERSONAL_DETAILS_HEADING =
            By.xpath("//h6[normalize-space()='Personal Details']");

    public AddEmployeePage() {
        waitForVisible(FIRST_NAME);
    }

    public AddEmployeePage enterFirstName(String firstName) {
        type(FIRST_NAME, firstName);
        return this;
    }

    public AddEmployeePage enterMiddleName(String middleName) {
        type(MIDDLE_NAME, middleName);
        return this;
    }

    public AddEmployeePage enterLastName(String lastName) {
        type(LAST_NAME, lastName);
        return this;
    }

    public void saveEmployee() {
        click(SAVE_BUTTON);
    }

    public boolean isEmployeeCreated() {
        return isDisplayed(PERSONAL_DETAILS_HEADING);
    }
}
