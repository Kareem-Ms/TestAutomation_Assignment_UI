package org.pages;

import io.qameta.allure.Step;
import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.utils.ElementActions;

import java.util.List;

public class ShoppingCartPage {

    ElementActions elementActions;
    WebDriver driver;

    public ShoppingCartPage(WebDriver driver){
        this.driver = driver;
        elementActions = new ElementActions(driver);
    }

    //Locators
    By checkoutBtnLocator = By.id("checkout");


    //Actions
    @Step("Proceed to checkout page")
    public void proceedToCheckout(){
        elementActions.click(checkoutBtnLocator);
    }

    @Step("Check if the added products exist in cart")
    public boolean isAddedProductsExistInCart(List<String> MostExpensiveProductsTitles){
        List<WebElement> productsPriceList = driver.findElements(By.xpath("//div[@data-test = 'inventory-item-name']"));
        boolean flag = true;
        for (int i=0; i<MostExpensiveProductsTitles.size();i++){
            if(!MostExpensiveProductsTitles.get(i).equalsIgnoreCase(productsPriceList.get(i).getText())){
                flag = false;
            }
        }
        return flag;
    }

}
