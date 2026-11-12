package pages;

import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;

import java.time.Duration;

public class CartPage {
    private WebDriver driver;
    private WebDriverWait wait;

    private By quantityInput =By.cssSelector("input[name^='quantity']");
    private By updateButton = By.cssSelector("input[name^='quantity'] + .input-group-append " + "button[title='Update']");
    private By removeButton = By.xpath("//button[@title='Remove']");
    private By productTotal = By.xpath("//form[contains(@action, 'checkout/cart/edit')]" + "//tbody/tr[1]/td[contains(@class, 'text-right')][last()]");
    private By subtotal = By.xpath("//table[contains(@class, 'table-bordered')]" + "/tbody/tr[1]/td[2]/strong");
    private By updateSuccessMessage = By.cssSelector(".alert.alert-success.alert-dismissible");
    private By emptyCartMessage = By.xpath("//div[@id='content']//p[contains(" + "text(),'Your shopping cart is empty!')]");
    private By shoppingCartTitle = By.xpath("//h1[normalize-space()='Shopping Cart']");
    private By continueButton = By.xpath("//a[normalize-space()='Continue']");

    public CartPage(WebDriver driver) {
        this.driver = driver;
        this.wait = new WebDriverWait(driver, Duration.ofSeconds(10));
    }

    public void open() {
        driver.get("https://ecommerce-playground.lambdatest.io/" + "index.php?route=checkout/cart");
    }

    public String getQuantity() {
        return wait.until(
                ExpectedConditions.visibilityOfElementLocated(quantityInput)).getAttribute("value");
    }

    public String getProductTotal() {
        return wait.until(
                ExpectedConditions.visibilityOfElementLocated(productTotal)).getText().trim();
    }

    public void updateQuantity(String quantity) {
        WebElement input = wait.until(ExpectedConditions.visibilityOfElementLocated(quantityInput));

        input.clear();
        input.sendKeys(quantity);

        wait.until(
                ExpectedConditions.elementToBeClickable(updateButton)).click();
    }

    public String getUpdateMessage() {
        return wait.until(
                ExpectedConditions.visibilityOfElementLocated(updateSuccessMessage)).getText();
    }

    public String getSubtotal() {
        return wait.until(
                ExpectedConditions.visibilityOfElementLocated(subtotal)).getText().trim();
    }

    public void removeProduct() {
        wait.until(
                ExpectedConditions.elementToBeClickable(removeButton)).click();
    }

    public String getEmptyCartMessage() {
        return wait.until(
                ExpectedConditions.visibilityOfElementLocated(emptyCartMessage)).getText().trim();
    }

    public String getShoppingCartTitle() {
        return wait.until(
                ExpectedConditions.visibilityOfElementLocated(shoppingCartTitle)).getText().trim();
    }

    public boolean isContinueButtonDisplayed() {
        return wait.until(
                ExpectedConditions.visibilityOfElementLocated(continueButton)).isDisplayed();
    }
}