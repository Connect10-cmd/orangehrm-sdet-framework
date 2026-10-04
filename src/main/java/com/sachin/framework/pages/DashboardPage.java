package com.sachin.framework.pages;

import com.sachin.framework.components.SideMenuComponent;
import org.openqa.selenium.By;

public class DashboardPage extends BasePage {

    private final SideMenuComponent sideMenu;

    private final By dashboardHeader =
            By.xpath("//h6[normalize-space()='Dashboard']");

    private final By userDropdown =
            By.cssSelector(".oxd-userdropdown-name");

    private final By logoutLink =
            By.xpath("//a[normalize-space()='Logout']");

    public DashboardPage() {
        this.sideMenu = new SideMenuComponent(waitUtils);
    }

    public SideMenuComponent sideMenu() {
        return sideMenu;
    }

    public boolean isDashboardDisplayed() {
        return isDisplayed(dashboardHeader);
    }

    public String getCurrentUrl() {
        return driver.getCurrentUrl();
    }

    public boolean isDashboardUrl() {
        return waitForUrlContains("dashboard");
    }

    public boolean isUserSessionAvailable() {
        return isDisplayed(userDropdown);
    }

    public void clickUserDropdown() {
        click(userDropdown);
    }

    public void clickLogout() {
        click(logoutLink);
    }

    public void logout() {

        clickUserDropdown();
        clickLogout();
    }
}