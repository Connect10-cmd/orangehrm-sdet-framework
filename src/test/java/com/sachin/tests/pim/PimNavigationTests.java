package com.sachin.tests.pim;

import com.sachin.framework.pages.DashboardPage;
import com.sachin.framework.pages.pim.PimPage;
import com.sachin.tests.base.AuthenticatedTest;
import org.testng.Assert;
import org.testng.annotations.Test;

public class PimNavigationTests extends AuthenticatedTest {

    @Test(groups = {"smoke", "sanity", "pim"})
    public void authenticatedUserCanNavigateToPim() {
        DashboardPage dashboard = loginAsConfiguredUser();
        Assert.assertTrue(dashboard.isDashboardDisplayed(), "Dashboard should be visible after login");
        Assert.assertTrue(dashboard.isDashboardUrl(), "Dashboard URL should be active before navigation");

        dashboard.sideMenu().navigateToPim();
        PimPage pimPage = new PimPage();
        Assert.assertTrue(pimPage.verifyPimPageLoaded(), "PIM page heading should be visible");
        Assert.assertTrue(pimPage.isPimUrl(), "PIM route should be active");
    }
}
