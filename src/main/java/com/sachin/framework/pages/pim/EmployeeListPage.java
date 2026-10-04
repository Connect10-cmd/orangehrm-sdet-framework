package com.sachin.framework.pages.pim;

import com.sachin.framework.pages.BasePage;
import org.openqa.selenium.By;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.StaleElementReferenceException;

import java.util.List;
import java.util.Locale;

public class EmployeeListPage extends BasePage {

    // Placeholder and OrangeHRM table classes are moderately stable UI contracts.
    private static final By EMPLOYEE_NAME_INPUT =
            By.cssSelector("input[placeholder='Type for hints...']");
    private static final By AUTOCOMPLETE_OPTION = By.cssSelector(".oxd-autocomplete-option");
    private static final By SEARCH_BUTTON = By.xpath("//button[normalize-space()='Search']");
    private static final By EMPLOYEE_ROWS = By.cssSelector(".oxd-table-body .oxd-table-card");
    private static final By NO_RECORDS = By.xpath("//*[normalize-space()='No Records Found']");
    private static final By DELETE_ICON = By.cssSelector("i.bi-trash");
    private static final By CONFIRM_DELETE = By.xpath("//button[normalize-space()='Yes, Delete']");

    public EmployeeListPage() {
        waitForVisible(SEARCH_BUTTON);
    }

    public void searchEmployee(String employeeName) {
        type(EMPLOYEE_NAME_INPUT, employeeName);
        click(AUTOCOMPLETE_OPTION);
        click(SEARCH_BUTTON);
    }

    public boolean employeeExists(String employeeName) {
        return waitForSearchResult(employeeName) == SearchResult.FOUND;
    }

    public void deleteEmployee(String employeeName) {
        if (!employeeExists(employeeName)) {
            throw new IllegalStateException(
                    "Employee was not found in the current search results: " + employeeName);
        }

        wait.until(driver -> {
            try {
                for (WebElement row : driver.findElements(EMPLOYEE_ROWS)) {
                    if (matchesEmployee(row.getText(), employeeName)) {
                        row.findElement(DELETE_ICON).click();
                        return true;
                    }
                }
            } catch (StaleElementReferenceException exception) {
                return false;
            }
            return false;
        });

        click(CONFIRM_DELETE);
        wait.until(driver -> currentSearchResult(employeeName) == SearchResult.NOT_FOUND);
    }

    private SearchResult waitForSearchResult(String employeeName) {
        return wait.until(driver -> currentSearchResult(employeeName));
    }

    private SearchResult currentSearchResult(String employeeName) {
        try {
            List<WebElement> rows = driver.findElements(EMPLOYEE_ROWS);
            for (WebElement row : rows) {
                if (matchesEmployee(row.getText(), employeeName)) {
                    return SearchResult.FOUND;
                }
            }

            if (!rows.isEmpty() || isNoRecordsMessageDisplayed()) {
                return SearchResult.NOT_FOUND;
            }
            return null;
        } catch (StaleElementReferenceException exception) {
            return null;
        }
    }

    private boolean isNoRecordsMessageDisplayed() {
        for (WebElement message : driver.findElements(NO_RECORDS)) {
            try {
                if (message.isDisplayed()) {
                    return true;
                }
            } catch (StaleElementReferenceException ignored) {
                return false;
            }
        }
        return false;
    }

    private boolean matchesEmployee(String rowText, String employeeName) {
        return normalize(rowText).contains(normalize(employeeName));
    }

    private String normalize(String text) {
        return text.replaceAll("\\s+", "").toLowerCase(Locale.ROOT);
    }

    private enum SearchResult {
        FOUND,
        NOT_FOUND
    }
}
