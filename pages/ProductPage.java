package pages;

import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;

import java.time.Duration;
import java.util.List;

public class ProductPage {
    private WebDriver driver;
    private WebDriverWait wait;

    private By productName = By.xpath("//h1[normalize-space()='iPhone']");
    private By productImage = By.cssSelector("img[title='iPhone']");
    private By productDescription = By.cssSelector("p.intro");
    private By quantityControl = By.cssSelector(".content-quantity [data-toggle='spinner']");
    private By decreaseButton = By.cssSelector(".content-quantity button[data-spinner='down']");
    private By increaseButton = By.cssSelector(".content-quantity button[data-spinner='up']");
    private By addToCartButton = By.cssSelector(".content-button button.btn-cart[title='Add to Cart']");
    private By successMessage = By.cssSelector("#notification-box-top .toast[role='alert'] p");

    public ProductPage(WebDriver driver) {
        this.driver = driver;
        this.wait = new WebDriverWait(driver, Duration.ofSeconds(10));
    }

    public void open() {
        driver.get("https://ecommerce-playground.lambdatest.io/" + "index.php?route=product/product&product_id=40");
    }

    private WebElement visibleElement(By locator) {
        return wait.until(driver ->
                driver.findElements(locator)
                        .stream()
                        .filter(WebElement::isDisplayed)
                        .findFirst()
                        .orElse(null)
        );
    }

    public String getProductName() {
        return wait.until(
                ExpectedConditions.visibilityOfElementLocated(productName)
        ).getText().trim();
    }

    public boolean isImageDisplayed() {
        return visibleElement(productImage).isDisplayed();
    }

    public String getImageSource() {
        return visibleElement(productImage).getAttribute("src");
    }

    public String getDescription() {
        return visibleElement(productDescription).getText().trim();
    }

    public boolean isQuantityControlDisplayed() {
        return visibleElement(quantityControl).isDisplayed();
    }

    public boolean isDecreaseButtonDisplayed() {
        return visibleElement(decreaseButton).isDisplayed();
    }

    public boolean isIncreaseButtonDisplayed() {
        return visibleElement(increaseButton).isDisplayed();
    }

    public void addToCart() {
        wait.until(driver ->
                driver.findElements(addToCartButton)
                        .stream()
                        .filter(WebElement::isDisplayed)
                        .filter(WebElement::isEnabled)
                        .findFirst()
                        .orElse(null)
        ).click();
    }

    public String getAddToCartMessage() {
        return visibleElement(successMessage).getText();
    }
}
