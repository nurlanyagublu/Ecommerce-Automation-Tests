package pages;

import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;

import java.time.Duration;

public class HomePage {
    private WebDriver driver;
    private WebDriverWait wait;

    private By searchInput = By.cssSelector("input[placeholder='Search For Products']");
    private By searchButton = By.xpath("//button[normalize-space()='Search']");
    private By shopByCategory = By.xpath("//a[normalize-space()='Shop by Category']");
    private By topCategories = By.xpath("//h5[normalize-space()='Top categories']");
    private By desktopsAndMonitors = By.xpath("//span[normalize-space()='Desktops and Monitors']");

    public HomePage(WebDriver driver) {
        this.driver = driver;
        this.wait = new WebDriverWait(driver, Duration.ofSeconds(10));
    }

    public void open() {
        driver.get("https://ecommerce-playground.lambdatest.io/");
    }

    public void searchFor(String product) {
        wait.until(ExpectedConditions.visibilityOfElementLocated(searchInput)).sendKeys(product);

        wait.until(ExpectedConditions.elementToBeClickable(searchButton)).click();
    }

    public void openCategories() {
        wait.until(ExpectedConditions.elementToBeClickable(shopByCategory)).click();
    }

    public boolean isTopCategoriesDisplayed() {
        return wait.until(ExpectedConditions.visibilityOfElementLocated(topCategories)).isDisplayed();
    }

    public void openDesktopsAndMonitors() {
        wait.until(ExpectedConditions.elementToBeClickable(desktopsAndMonitors)).click();
    }
}