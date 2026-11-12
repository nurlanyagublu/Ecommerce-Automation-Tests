package Tests;

import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.chrome.ChromeDriver;

import pages.CartPage;
import pages.HomePage;
import pages.ProductPage;
import pages.RegisterPage;

import static org.junit.jupiter.api.Assertions.*;

public class EcommerceTests {

    private WebDriver driver;

    @BeforeEach
    void setUp() {
        driver = new ChromeDriver();
        driver.manage().window().maximize();
    }

    @AfterEach
    void tearDown() {
        if (driver != null) {
            driver.quit();
        }
    }

    @Test
    void searchForExistingProduct() {
        HomePage homePage = new HomePage(driver);

        homePage.open();
        homePage.searchFor("iPhone");

        assertTrue(driver.getPageSource().contains("iPhone"));
    }

    @Test
    void searchForNonExistingProduct() {
        HomePage homePage = new HomePage(driver);

        homePage.open();
        homePage.searchFor("BMW");

        assertTrue(driver.getPageSource().contains("There is no product that matches the search criteria."));
    }

    @Test
    void browseProductCategory() {
        HomePage homePage = new HomePage(driver);

        homePage.open();
        homePage.openCategories();

        assertTrue(homePage.isTopCategoriesDisplayed());

        homePage.openDesktopsAndMonitors();

        assertEquals("Monitors", driver.findElement(org.openqa.selenium.By.cssSelector("h1")).getText().trim());
    }

    @Test
    void verifyProductDetails() {
        ProductPage productPage = new ProductPage(driver);

        productPage.open();

        assertAll(
                () -> assertEquals("iPhone", productPage.getProductName()),
                () -> assertTrue(productPage.isImageDisplayed()),
                () -> assertFalse(productPage.getImageSource().isBlank()),
                () -> assertFalse(productPage.getDescription().isBlank()),
                () -> assertTrue(productPage.isQuantityControlDisplayed()),
                () -> assertTrue(productPage.isDecreaseButtonDisplayed()),
                () -> assertTrue(productPage.isIncreaseButtonDisplayed())
            );
    }

    @Test
    void addProductToCart() {
        ProductPage productPage = new ProductPage(driver);

        productPage.open();
        productPage.addToCart();

        String message = productPage.getAddToCartMessage();

        assertTrue(message.contains("Success: You have added"));
        assertTrue(message.contains("shopping cart"));
    }

    @Test
    void updateProductQuantityInCart() {
        ProductPage productPage = new ProductPage(driver);
        CartPage cartPage = new CartPage(driver);

        productPage.open();
        productPage.addToCart();

        cartPage.open();

        String totalBefore = cartPage.getProductTotal();

        cartPage.updateQuantity("2");

        assertAll(
                () -> assertEquals("2", cartPage.getQuantity()),
                () -> assertTrue(cartPage.getUpdateMessage().contains("modified your shopping cart")),
                () -> assertNotEquals(totalBefore,cartPage.getProductTotal()),
                () -> assertFalse(cartPage.getSubtotal().isBlank())
        );
    }

    @Test
    void removeProductFromCart() {
        ProductPage productPage = new ProductPage(driver);
        CartPage cartPage = new CartPage(driver);

        productPage.open();
        productPage.addToCart();

        cartPage.open();
        cartPage.removeProduct();

        assertAll(
                () -> assertEquals("Shopping Cart", cartPage.getShoppingCartTitle()),
                () -> assertEquals("Your shopping cart is empty!", cartPage.getEmptyCartMessage()),
                () -> assertTrue(cartPage.isContinueButtonDisplayed())
        );
    }

    @Test
    void registerNewUserAccount() {
        RegisterPage registerPage = new RegisterPage(driver);

        String uniqueEmail = "student" + System.currentTimeMillis() + "@example.com";

        registerPage.open();

        registerPage.register(
                "John",
                "Smith",
                uniqueEmail,
                "+12345678901",
                "Password123!"
        );

        registerPage.agreeToPrivacyPolicy();

        assertTrue(registerPage.isAgreementSelected(), "Privacy Policy checbox was not selected");

        registerPage.submitRegistration();

        assertTrue(registerPage.getSuccessHeading().contains("Your Account Has Been Created"));
    }
}