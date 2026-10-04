package com.sachin.framework.components;

import com.sachin.framework.utils.WaitUtils;
import org.openqa.selenium.By;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public final class SideMenuComponent {

    private static final Logger LOGGER = LoggerFactory.getLogger(SideMenuComponent.class);
    // Text-based app navigation is moderately stable: it avoids generated classes and indexes.
    private static final By PIM_LINK = By.xpath("//span[normalize-space()='PIM']");
    private static final By ADMIN_LINK = By.xpath("//span[normalize-space()='Admin']");
    private static final By LEAVE_LINK = By.xpath("//span[normalize-space()='Leave']");
    private static final By RECRUITMENT_LINK = By.xpath("//span[normalize-space()='Recruitment']");

    private final WaitUtils waitUtils;

    public SideMenuComponent(WaitUtils waitUtils) {
        if (waitUtils == null) {
            throw new IllegalArgumentException("Shared WaitUtils is required");
        }
        this.waitUtils = waitUtils;
    }

    public void navigateToPim() {
        navigate("PIM", PIM_LINK);
    }

    public void navigateToAdmin() {
        navigate("Admin", ADMIN_LINK);
    }

    public void navigateToLeave() {
        navigate("Leave", LEAVE_LINK);
    }

    public void navigateToRecruitment() {
        navigate("Recruitment", RECRUITMENT_LINK);
    }

    private void navigate(String destination, By locator) {
        LOGGER.info("Navigating to {} using side menu", destination);
        waitUtils.waitForClickable(locator).click();
    }
}
