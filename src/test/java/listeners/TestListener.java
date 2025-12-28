package listeners;

import base.BaseTest;
import io.qameta.allure.Allure;
import com.microsoft.playwright.Page;
import org.testng.ITestListener;
import org.testng.ITestResult;

import java.io.ByteArrayInputStream;
import java.nio.file.Paths;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.nio.file.Files;

public class TestListener implements ITestListener {

    @Override
    public void onTestFailure(ITestResult result) {

        Object testClass = result.getInstance();

        // Access the Page object from BaseTest
        Page page = ((BaseTest) testClass).getPage();

        String testName = result.getMethod().getMethodName();
        String timestamp = new SimpleDateFormat("yyyyMMdd_HHmmss").format(new Date());

        String screenshotPath = "screenshots/" + testName + "_" + timestamp + ".png";

        try {
            // Save screenshot locally
            page.screenshot(new Page.ScreenshotOptions()
                    .setPath(Paths.get(screenshotPath))
                    .setFullPage(true));

            System.out.println("Screenshot saved at location: " + screenshotPath);

            // Attach screenshot to Allure report
            byte[] screenshotBytes = Files.readAllBytes(Paths.get(screenshotPath));
            Allure.addAttachment(testName + "_screenshot", new ByteArrayInputStream(screenshotBytes));

        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}
