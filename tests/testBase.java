package tests;

import org.openqa.selenium.*;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.interactions.Actions;
import org.openqa.selenium.support.ui.WebDriverWait;
import org.testng.ITestResult;
import org.testng.annotations.AfterMethod;
import org.testng.annotations.BeforeClass;
import org.testng.annotations.BeforeTest;
import org.apache.commons.io.FileUtils;

import java.io.File;
import java.io.IOException;
import java.time.Duration;

public class testBase {
    protected WebDriver driver;
    public WebDriverWait wait;
    public Actions actions;

    @BeforeClass
    public void openUI() {
        driver = new ChromeDriver();

        // Navigate to LeetCode (to set domain)
        driver.get("https://leetcode.com");

        // Add LeetCode session cookie
        Cookie sessionCookie = new Cookie(
                "LEETCODE_SESSION",
                "value",
                ".leetcode.com",
                "/",
                null
        );
        driver.manage().addCookie(sessionCookie);

        // Add CSRF token if needed
        Cookie csrfCookie = new Cookie(
                "csrftoken",
                "value",
                ".leetcode.com",
                "/",
                null
        );
        driver.manage().addCookie(csrfCookie);
        // Refresh to apply cookies
        driver.navigate().refresh();
        driver.manage().timeouts().implicitlyWait(Duration.ofSeconds(5));
    }
    /*@AfterMethod
    public void take_screenShot(ITestResult result) throws IOException {
        if (ITestResult.FAILURE == result.getStatus()) {
            System.out.println("Failed!");
            System.out.println("Taking Screenshot...");
            TakesScreenshot sc = (TakesScreenshot) driver;
            File photo = sc.getScreenshotAs(OutputType.FILE);
            FileUtils.copyFile(photo, new File("./screenshots/" + result.getName() + ".png"));
        }
    }*/

}
