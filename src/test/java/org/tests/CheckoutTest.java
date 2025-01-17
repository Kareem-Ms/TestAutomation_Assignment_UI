package org.tests;

import io.qameta.allure.*;
import io.qameta.allure.SeverityLevel;
import org.openqa.selenium.WebDriver;
import org.pages.*;
import org.testng.Assert;
import org.testng.annotations.*;
import org.utils.*;

@Epic("Regression Tests")
@Feature("Checkout Test Scenario")
public class CheckoutTest {

    //Variables
    ThreadLocal<WebDriver> driver = new ThreadLocal<>();
    ThreadLocal<JsonFileManager> jsonFileManager = new ThreadLocal<>();
    LoginPage loginPage;
    ProductsPage productsPage;
    ShoppingCartPage shoppingCartPage;
    CheckoutPage checkoutPage;

    //Tests
    @Test(description = "verify Login with valid credentials")
    @Severity(SeverityLevel.CRITICAL)
    public void loginWithValidCredintials() {
        loginPage.login(jsonFileManager.get().getTestData("userName"),jsonFileManager.get().getTestData("password"));
        Assert.assertTrue(productsPage.isProductTitleExist());
    }

    @Test(dependsOnMethods = "loginWithValidCredintials", description = "verify adding the most expensive n products to the cart")
    public void addProductToCartSuccessfully(){
        productsPage.addMostExpensiveProductsToCart(Integer.parseInt(jsonFileManager.get().getTestData("numberOfProductsToBeAdded")));
        productsPage.openShoppingCart();
        Assert.assertTrue(shoppingCartPage.isAddedProductsExistInCart(productsPage.getMostExpensiveProductsTitles()));
    }

    @Test(dependsOnMethods = "addProductToCartSuccessfully", description = "verify completing checkout process")
    public void checkoutSuccessfully(){
        shoppingCartPage.proceedToCheckout();
        Assert.assertEquals(checkoutPage.getCheckoutTitle(),jsonFileManager.get().getTestData("checkoutTitle"));

        checkoutPage.fillCheckoutInformation(jsonFileManager.get().getTestData("checkoutInfo.firstName")
                                            , jsonFileManager.get().getTestData("checkoutInfo.lastName")
                                            , jsonFileManager.get().getTestData("checkoutInfo.postalCode"));

        Assert.assertEquals(checkoutPage.getCheckoutTitle(),jsonFileManager.get().getTestData("checkoutOverviewTitle"));
        Assert.assertTrue(checkoutPage.getSubtotal().contains(checkoutPage.calculateExpectedTotalPrice()));
        Assert.assertEquals(driver.get().getCurrentUrl(),jsonFileManager.get().getTestData("checkoutOverviewUrl"));

        checkoutPage.clickFinishBtn();
        Assert.assertEquals(checkoutPage.getConfirmationTitle(),jsonFileManager.get().getTestData("confirmationTitle"));
        Assert.assertEquals(checkoutPage.getConfirmationMessage(),jsonFileManager.get().getTestData("confirmationMsg"));
    }

    //Configurations
    @BeforeClass
    public void setUp() {
        jsonFileManager.set(new JsonFileManager("src\\test\\java\\org\\testData\\CheckoutTestData.json"));
        BrowserFactory browserFactory = new BrowserFactory();
        driver.set(browserFactory.getBrowser(PropertiesManager.getProperty("BrowserName")));
        BrowserActions.navigateToUrl(driver.get(), PropertiesManager.getProperty("sucedemoUrl"));
        loginPage = new LoginPage(driver.get());
        productsPage = new ProductsPage(driver.get());
        shoppingCartPage = new ShoppingCartPage(driver.get());
        checkoutPage = new CheckoutPage(driver.get());
    }

    @AfterClass
    public void tearDown() {
        BrowserActions.closeAllBrowserTabs(driver.get());
    }

}
