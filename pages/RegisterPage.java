package pages;

import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;

import java.time.Duration;

public class RegisterPage {
    private WebDriver driver;
    private WebDriverWait wait;

    private By firstName = By.cssSelector("input[name='firstname']");
    private By lastName = By.cssSelector("input[name='lastname']");
    private By email = By.cssSelector("input[name='email']");
    private By telephone = By.cssSelector("input[name='telephone']");
    private By password = By.cssSelector("input[name='password']");
    private By confirmPassword = By.cssSelector("input[name='confirm']");
    private By agreementLabel = By.cssSelector("label[for='input-agree']");
    private By agreementCheckbox = By.id("input-agree");
    private By continueButton = By.xpath("//input[@value='Continue']");
    private By successHeading = By.xpath("//h1[normalize-space()='Your Account Has Been Created!']");

    public RegisterPage(WebDriver driver) {
        this.driver = driver;
        this.wait = new WebDriverWait(driver, Duration.ofSeconds(10));
    }

    public void open() {
        driver.get("https://ecommerce-playground.lambdatest.io/" + "index.php?route=account/register");
    }

    public void register(
            String firstNameValue,
            String lastNameValue,
            String emailValue,
            String telephoneValue,
            String passwordValue
    ) {
        wait.until(ExpectedConditions.visibilityOfElementLocated(firstName)).sendKeys(firstNameValue);

        driver.findElement(firstName).sendKeys(firstNameValue);
        driver.findElement(lastName).sendKeys(lastNameValue);
        driver.findElement(email).sendKeys(emailValue);
        driver.findElement(telephone).sendKeys(telephoneValue);
        driver.findElement(password).sendKeys(passwordValue);
        driver.findElement(confirmPassword).sendKeys(passwordValue);    
    }

    public void agreeToPrivacyPolicy() {
        wait.until(
            ExpectedConditions.elementToBeClickable(agreementLabel)).click();
    }

    public boolean isAgreementSelected() {
        return wait.until(
            ExpectedConditions.presenceOfElementLocated(agreementCheckbox)).isSelected(); 
    }

    public void submitRegistration() {
        wait.until(
            ExpectedConditions.elementToBeClickable(continueButton)).click(); 
    }

    public String getSuccessHeading() {
        return wait.until(
            ExpectedConditions.visibilityOfElementLocated(successHeading)).getText().trim();
    }
}