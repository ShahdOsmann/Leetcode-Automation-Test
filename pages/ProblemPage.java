package pages;

import org.openqa.selenium.By;
import org.openqa.selenium.Keys;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.interactions.Actions;
import org.openqa.selenium.support.FindBy;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.FluentWait;
import org.openqa.selenium.support.ui.Wait;

import java.time.Duration;
import java.util.NoSuchElementException;

public class ProblemPage extends PageBase {

    @FindBy(linkText = "367. Valid Perfect Square")
    WebElement problemTitle;

    @FindBy(className = "mtk4")
    WebElement codeEditorLine;

    @FindBy(xpath = "//*[@id=\"ide-top-btns\"]/div[1]/div/div/div[2]/div/div[2]/div/div[3]/div[3]/div/button/span")
    WebElement submitButton;

    @FindBy(xpath = "//button[.//text()='C++' and contains(@class, 'inline-flex')]")
    public WebElement dropListButton;

    @FindBy(xpath = "//div[text()='Java']")
    public WebElement javaOption;

    @FindBy(xpath = "//div[text()='C#']")
    public WebElement cSharpOption;

    @FindBy(xpath = "//div[contains(text(),'Accepted')]")
    WebElement acceptedMessage;

    @FindBy(xpath = "//div[contains(text(),'Wrong Answer')]")
    WebElement wrongAnswerMessage;

    @FindBy(xpath = "//div[contains(text(),'Time Limit Exceeded')]")
    WebElement timeLimitMessage;

    @FindBy(xpath = "//div[contains(text(),'Compile Error')]")
    WebElement compileErrorMessage;

    @FindBy(xpath = "//div[contains(text(),'Memory Limit Exceeded')]")
    WebElement memoryLimitMessage;

    @FindBy(xpath = "//div[contains(text(),'Runtime Error')]")
    WebElement runtimeErrorMessage;

    public Wait<WebDriver> fluentWait;

    public ProblemPage(WebDriver driver) {
        super(driver);
        this.driver = driver;
        this.actions = new org.openqa.selenium.interactions.Actions(driver);
        this.fluentWait = new FluentWait<WebDriver>(driver)
                .withTimeout(Duration.ofSeconds(90))
                .pollingEvery(Duration.ofMillis(500))
                .ignoring(NoSuchElementException.class);
    }

    public void navigateToProblem(String problemUrl) {
        driver.get(problemUrl);
        driver.manage().timeouts().implicitlyWait(Duration.ofSeconds(5));
    }

    public String getProblemTitleText() {
        return problemTitle.getText();
    }

    public void enterCode(String code) {
        try {
            fluentWait.until(ExpectedConditions.presenceOfElementLocated(By.className("mtk4")));
            WebElement editorLine = driver.findElement(By.className("mtk4"));
            editorLine.click();

            Actions actions = new Actions(driver);
            actions.keyDown(Keys.CONTROL)
                    .sendKeys("a")
                    .keyUp(Keys.CONTROL)
                    .sendKeys(Keys.BACK_SPACE)
                    .perform();

            driver.manage().timeouts().implicitlyWait(Duration.ofSeconds(5));
            actions.sendKeys(code)
                    .sendKeys(Keys.ARROW_DOWN)
                    .sendKeys(Keys.BACK_SPACE)
                    .perform();
            System.out.println("Code successfully entered into the editor.");
        } catch (Exception e) {
            System.out.println("Failed to enter code: " + e.getMessage());
            throw new RuntimeException("Editor interaction failed.", e);
        }
    }

    public void clickSubmit() {
        WebElement submitButton = driver.findElement(By.xpath("//*[@id=\"ide-top-btns\"]/div[1]/div/div/div[2]/div/div[2]/div/div[3]/div[3]/div/button/span"));
        submitButton.click();
        System.out.println("Submit button clicked successfully.");
    }
    public void submitResult(String expectedResult) {
        driver.manage().timeouts().implicitlyWait(Duration.ofSeconds(50));
        Wait<WebDriver> resultWait = fluentWait;
        WebElement Message = resultWait.until(driver -> {
            WebElement result = driver.findElement(By.xpath("//div[contains(text(),'" + expectedResult + "')]"));
            if (result.isDisplayed() && result.getText().contains(expectedResult)) {
                System.out.println("Submission was " + expectedResult + " and result is visible.");
                return result;
            }
            return null;
        });
        if (Message == null) {
            throw new RuntimeException("The return message is null");
        }
    }

    public void submitCode(String code) {
        enterCode(code);
        clickSubmit();
    }
    public void submitCode2(String code, String expectedResult) {
        enterCode(code);
        clickSubmit();
        submitResult(expectedResult);
    }

}
