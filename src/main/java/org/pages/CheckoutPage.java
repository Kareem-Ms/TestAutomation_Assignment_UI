package org.pages;

import io.qameta.allure.Step;
import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.utils.ElementActions;

import java.util.ArrayList;
import java.util.List;

public class CheckoutPage {
    ElementActions elementActions;
    WebDriver driver;

    public CheckoutPage(WebDriver driver){
        this.driver = driver;
        elementActions = new ElementActions(driver);
    }

    //Locators
    By firstNameInputLocator = By.id("first-name");
    By lastNameInputLocator = By.id("last-name");
    By zipCodeInputLocator = By.id("postal-code");
    By continueBtnLocator = By.id("continue");
    By checkOutOverviewTitleLocator = By.xpath("//span[@data-test = 'title']");
    By subTotalLocator = By.xpath("//div[@data-test = 'subtotal-label']");
    By finishBtnLocator = By.id("finish");
    By orderConfirmationTitleLocator = By.xpath("//h2[@data-test = 'complete-header']");
    By orderConfirmationMsg = By.xpath("//div[@data-test = 'complete-text']");

    //Actions
    @Step("Fill checkout information with First name: {firstName} , Last name: {lastName} and Postal Code: {postalCode}")
    public void fillCheckoutInformation(String firstName, String lastName, String postalCode){
        elementActions.type(firstNameInputLocator,firstName);
        elementActions.type(lastNameInputLocator,lastName);
        elementActions.type(zipCodeInputLocator,postalCode);
        elementActions.click(continueBtnLocator);
    }

    @Step("Get checkout overview title")
    public String getCheckoutTitle(){
        return  elementActions.getText(checkOutOverviewTitleLocator);
    }

    @Step("Calculate the expected total price without vat")
    public String calculateExpectedTotalPrice(){
        List<WebElement> productsPriceList = driver.findElements(By.xpath("//div[@class = 'inventory_item_price']"));
        List<Float> PriceListWithoutCurrency = removeCurrencyFromList(productsPriceList);
        float total = 0 ;
        for (Float price: PriceListWithoutCurrency){
            total = total + price;
        }
      return "$"+total;
    }

    @Step("get subtotal from overview page")
    public String getSubtotal(){
        return  elementActions.getText(subTotalLocator);
    }

    @Step("Click on Finish button")
    public void clickFinishBtn(){
        elementActions.click(finishBtnLocator);
    }

    @Step("Get confirmation title")
    public String getConfirmationTitle(){
        return  elementActions.getText(orderConfirmationTitleLocator);
    }

    @Step("Get confirmation message")
    public String getConfirmationMessage(){
        return  elementActions.getText(orderConfirmationMsg);
    }

    //helper methods
    public List<Float> removeCurrencyFromList(List<WebElement> productsPriceList){
        List<Float> priceList = new ArrayList<>();
        for (WebElement webElement : productsPriceList) {
            String priceWithoutCurrency = webElement.getText().replace("$", "");
            Float price = Float.parseFloat(priceWithoutCurrency);
            priceList.add(price);
        }
        return priceList;
    }

}
