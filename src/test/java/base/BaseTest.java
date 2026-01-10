package base;

import com.microsoft.playwright.*; // Playwright classes for browser automation
import org.testng.annotations.AfterMethod; // TestNG annotation: runs after each test method
import org.testng.annotations.BeforeMethod; // TestNG annotation: runs before each test method
import utils.ConfigReader; // Utility to read configuration properties like URL

public class BaseTest {

    // Playwright instance to manage browser automation
    protected Playwright playwright;

    // Browser instance (Chromium, Firefox, WebKit)
    protected Browser browser;

    // Browser context for isolated sessions (like separate browser profiles)
    protected BrowserContext context;

    // Page object representing a tab in the browser
    protected Page page;

    // Getter method to allow access to 'page' from other classes or listeners
    public Page getPage() {
        return page;
    }

    // Runs before each TestNG test method
    @BeforeMethod
    public void setUp() {
        // Initialize Playwright
        playwright = Playwright.create();

        // Launch Chromium browser in non-headless mode
        browser = playwright.chromium().launch(
                new BrowserType.LaunchOptions().setHeadless(false)
        );

        // Create a new browser context (isolated session)
        context = browser.newContext();

        // Open a new page (tab) in the context
        page = context.newPage();

        // Navigate to the URL specified in the configuration file
        page.navigate(ConfigReader.get("url"));
    }

    // Runs after each TestNG test method
    @AfterMethod
    public void tearDown() {
        // Close the browser context
        context.close();

        // Close the browser
        browser.close();

        // Close the Playwright instance
        playwright.close();
    }
}
