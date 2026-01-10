package base;

import com.microsoft.playwright.*;
import org.testng.annotations.AfterMethod;
import org.testng.annotations.BeforeMethod;
import utils.ConfigReader;

public class BaseTest {

    protected Playwright playwright;
    protected Browser browser;
    protected BrowserContext context;
    protected Page page;

    // Expose page for listeners or child classes
    public Page getPage() {
        return page;
    }

    @BeforeMethod
    public void setUp() {

        // Read runtime parameters (UI / Jenkins / CLI)
        String browserName = System.getProperty("browser", "chromium");
        boolean headless = Boolean.parseBoolean(
                System.getProperty("headless", "false")
        );

        System.out.println("Launching browser: " + browserName);
        System.out.println("Headless mode: " + headless);

        // Initialize Playwright
        playwright = Playwright.create();

        // Launch browser based on input
        switch (browserName.toLowerCase()) {

            case "firefox":
                browser = playwright.firefox().launch(
                        new BrowserType.LaunchOptions()
                                .setHeadless(headless)
                );
                break;

            case "webkit":
                browser = playwright.webkit().launch(
                        new BrowserType.LaunchOptions()
                                .setHeadless(headless)
                );
                break;

            default:
                browser = playwright.chromium().launch(
                        new BrowserType.LaunchOptions()
                                .setHeadless(headless)
                );
        }

        // Create isolated browser context
        context = browser.newContext();

        // Create new page
        page = context.newPage();

        // Navigate to application URL
        String appUrl = ConfigReader.get("url");
        page.navigate(appUrl);

        System.out.println("Navigated to URL: " + appUrl);
    }

    @AfterMethod(alwaysRun = true)
    public void tearDown() {

        System.out.println("Closing browser resources...");

        // Safe cleanup to avoid NullPointerException
        if (page != null) {
            page.close();
        }
        if (context != null) {
            context.close();
        }
        if (browser != null) {
            browser.close();
        }
        if (playwright != null) {
            playwright.close();
        }
    }
}
