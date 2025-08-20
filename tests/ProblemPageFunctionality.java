package tests;

import org.openqa.selenium.*;
import org.openqa.selenium.interactions.Actions;
import org.openqa.selenium.support.ui.*;
import org.testng.Assert;
import org.testng.annotations.AfterClass;
import org.testng.annotations.BeforeClass;
import org.testng.annotations.Test;
import pages.ProblemPage;

import java.time.Duration;

public class ProblemPageFunctionality extends testBase{
    private ProblemPage problemPage;
    private final String PROBLEM_URL = "https://leetcode.com/problems/valid-perfect-square/description/";
    private final String EXPECTED_TITLE = "367. Valid Perfect Square";

    @BeforeClass
    public void login() {
        if (driver == null) { throw new IllegalStateException("WebDriver is not initialized.");}
        problemPage = new ProblemPage(driver);
        problemPage.navigateToProblem(PROBLEM_URL);
    }
    @Test (priority = 1)
    public void testProblemTitle() {
        String actualText = problemPage.getProblemTitleText();
        Assert.assertEquals(actualText, EXPECTED_TITLE, "Problem title text does not match expected.");
        System.out.println("Test Passed: Problem title text matches expected.");
    }
    @Test(priority = 2)
    public void checkAccCode() {
        problemPage.navigateToProblem(PROBLEM_URL);
        String code = "class Solution {\npublic:\n  bool isPerfectSquare(int num) {\n  for(int i = 1; i <= num/i; i++) if(i * i == num) return 1;" +
                "\n  return 0;\n    }\n};";
        problemPage.submitCode(code);
        driver.manage().timeouts().implicitlyWait(Duration.ofSeconds(10));
        WebElement resultSpan = driver.findElement(By.cssSelector("span[data-e2e-locator='submission-result']"));
        Assert.assertEquals(resultSpan.getText(), "Accepted", "The submission result is acc");
    }
    @Test(priority = 3)
    public void checkWrongCode() {
        problemPage.navigateToProblem(PROBLEM_URL);
        String code = "class Solution {\npublic:\n  bool isPerfectSquare(int num) {\n  return 0;\n    }\n};";
        driver.manage().timeouts().implicitlyWait(Duration.ofSeconds(5));
        problemPage.submitCode2(code, "Wrong Answer");
    }
    @Test(priority = 4)
    public void checkCompileError() {
        problemPage.navigateToProblem(PROBLEM_URL);
        String code = "class Solution {\npublic:\n  bool isPerfectSquare(int num) {\n  \nreturn ;\n    }\n};";
        problemPage.submitCode2(code, "Compile Error");
    }
    @Test(priority = 5)
    public void checkMemoryLimit() {
        problemPage.navigateToProblem(PROBLEM_URL);
        String code = "class Solution {\npublic:\n  bool isPerfectSquare(int num) {\n vector<int> v(1e9, 1);  \nreturn 0;\n    }\n};";
        problemPage.submitCode2(code, "Memory Limit Exceeded");
    }
    @Test(priority = 6)
    public void checkRuntimeError() {
        problemPage.navigateToProblem(PROBLEM_URL);
        String code = "class Solution {\npublic:\n  bool isPerfectSquare(int num) {\n for(int i=0; i<1e18; i++);  \nreturn 0;\n    }\n};";
        problemPage.submitCode2(code,"Runtime Error" );
    }
    @Test(priority = 7)
    public void checkTimeLimit() {
        problemPage.navigateToProblem(PROBLEM_URL);
        String code = "class Solution {\npublic:\n  bool isPerfectSquare(int num) {\n  for(long long i=0; i<=1e20; i++);\nreturn 0;\n    }\n};";
        problemPage.submitCode2(code, "Time Limit Exceeded");
    }
    // testing languages
    @Test(priority = 8)
    public void javaCode() {
        problemPage.navigateToProblem(PROBLEM_URL);
        problemPage.dropListButton.click();
        problemPage.javaOption.click();
        String code = "class Solution {\npublic boolean isPerfectSquare(int num) {\n  for (int i = 1; i <= num/i; i++) if(i * i == num) return true;" +
                "\n  return false;\n    }\n};";
        problemPage.submitCode(code);
        driver.manage().timeouts().implicitlyWait(Duration.ofSeconds(10));
        WebElement resultSpan = driver.findElement(By.cssSelector("span[data-e2e-locator='submission-result']"));
        Assert.assertEquals(resultSpan.getText(), "Accepted", "The submission result is acc");
    }
    @AfterClass
    public void tearDown() {
        //driver.quit();
    }
}
