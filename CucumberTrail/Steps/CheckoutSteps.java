package org.Steps;

import io.cucumber.java.After;
import io.cucumber.java.Before;
import io.cucumber.java.en.*;
import org.openqa.selenium.WebDriver;
import org.pages.*;
import org.testng.Assert;
import org.utils.*;

public class CheckoutSteps {

    @Given("I am on the login page")
    public void iAmOnTheLoginPage() {
        BrowserActions.navigateToUrl(driver, PropertiesManager.getProperty("sucedemoUrl"));
    }

    @When("I login with valid credentials")
    public void loginWithValidCredentials() {
        loginPage.login(jsonFileManager.getTestData("userName"), jsonFileManager.getTestData("password"));
    }

    @Then("I should see the product title")
    public void shouldSeeProductTitle() {
        Assert.assertTrue(productsPage.isProductTitleExist());
    }

    @Given("I am logged in")
    public void iAmLoggedIn() {
        loginWithValidCredentials();
    }

    @When("I add the most expensive products to the cart")
    public void addMostExpensiveProductsToCart() {
        productsPage.addMostExpensiveProductsToCart(Integer.parseInt(jsonFileManager.getTestData("numberOfProductsToBeAdded")));
        productsPage.openShoppingCart();
    }

    @Then("the products should be in the cart")
    public void productsShouldBeInTheCart() {
        Assert.assertTrue(shoppingCartPage.isAddedProductsExistInCart(productsPage.getMostExpensiveProductsTitles()));
    }

    @Given("I have products in the cart")
    public void iHaveProductsInTheCart() {
        addMostExpensiveProductsToCart();
    }

    @When("I complete the checkout process")
    public void completeCheckoutProcess() {
        shoppingCartPage.proceedToCheckout();
        Assert.assertEquals(checkoutPage.getCheckoutTitle(), jsonFileManager.getTestData("checkoutTitle"));
        checkoutPage.fillCheckoutInformation(jsonFileManager.getTestData("checkoutInfo.firstName"), jsonFileManager.getTestData("checkoutInfo.lastName"), jsonFileManager.getTestData("checkoutInfo.postalCode"));
        Assert.assertEquals(checkoutPage.getCheckoutTitle(), jsonFileManager.getTestData("checkoutOverviewTitle"));
        Assert.assertTrue(checkoutPage.getSubtotal().contains(checkoutPage.calculateExpectedTotalPrice()));
        Assert.assertEquals(driver.getCurrentUrl(), jsonFileManager.getTestData("checkoutOverviewUrl"));
        checkoutPage.clickFinishBtn();
    }

    @Then("I should see the confirmation message")
    public void shouldSeeConfirmationMessage() {
        Assert.assertEquals(checkoutPage.getConfirmationTitle(), jsonFileManager.getTestData("confirmationTitle"));
    }

}
