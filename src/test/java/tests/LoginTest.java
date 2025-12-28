package tests;

import base.BaseTest;
import org.testng.Assert;
import org.testng.annotations.Test;
import pages.LoginPage;
import utils.ConfigReader;

public class LoginTest extends BaseTest {

    @Test
    public void verifySuccessfulLogin() {
        LoginPage loginPage = new LoginPage(page);

        // Step 1: Enter credentials
        loginPage.enterUsername(ConfigReader.get("username"));
        loginPage.enterPassword(ConfigReader.get("password"));

        // Step 2: Submit login
        loginPage.clickSubmit();
        

        // Step 3: Verify URL contains logged-in path
        String currentUrl = page.url();
        Assert.assertTrue(
                currentUrl.contains("/logged-in-successfully/"),
                "Expected URL to contain '/logged-in-successfully/' but was " + currentUrl
        );

        // Step 4: Verify logout button is visible
        Assert.assertTrue(loginPage.isLogoutVisible(), "Logout button should be visible after login.");
    }
}
