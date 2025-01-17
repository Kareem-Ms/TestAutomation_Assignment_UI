package org.pages;

import io.qameta.allure.Step;
import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.utils.ElementActions;

import java.util.ArrayList;
import java.util.Comparator;
import java.util.List;



public class ProductsPage {

    private final WebDriver driver;
    ElementActions elementActions;
    private final List<String> MostExpensiveProductsTitles = new ArrayList<>();

    public ProductsPage(WebDriver driver){
        this.driver = driver;
        elementActions = new ElementActions(driver);
    }

    //Locators
    By productTitleLocator = By.xpath("//span[@data-test = 'title']");
    By shoppingCartIconLocator = By.xpath("//a[@data-test = 'shopping-cart-link']");

    //Main Actions
    @Step("Check if product title appears")
    public boolean isProductTitleExist(){
        return  elementActions.isElementExist(productTitleLocator);
    }

    @Step("Add the most expensive {numberOfProductsToBeAdded} to the cart")
    public void addMostExpensiveProductsToCart(int numberOfProductsToBeAdded){
        List<WebElement> productsPriceList = driver.findElements(By.xpath("//div[@class = 'inventory_item_price']"));
        List<Float> sortedPriceList = getSortedPriceList(productsPriceList);

        for (int i =0; i<numberOfProductsToBeAdded;i++){
            String addToCartBtnXpath = "//div[@class = 'inventory_item_price' and contains(., '"+sortedPriceList.get(i)+"')]/following-sibling::button[contains(@id, 'add-to-cart') ]";
            String productTitleXpath = "//div[@class = 'inventory_item_price' and contains(., '"+sortedPriceList.get(i)+"')]/parent::div/preceding-sibling::div//div[@data-test = 'inventory-item-name']";
            elementActions.click(By.xpath(addToCartBtnXpath));
            MostExpensiveProductsTitles.add( elementActions.getText(By.xpath(productTitleXpath)));
        }
    }

    @Step("Open Shopping cart")
    public void openShoppingCart(){
        elementActions.click(shoppingCartIconLocator);
    }


    //helper methods
    public List<Float> getSortedPriceList(List<WebElement> productsPriceList){
        List<Float> priceListWithoutCurrency = removeCurrencyFromList(productsPriceList);
        return sortPriceList(priceListWithoutCurrency);
    }
    
    public List<Float> removeCurrencyFromList(List<WebElement> productsPriceList){
        List<Float> priceList = new ArrayList<>();
        for (WebElement webElement : productsPriceList) {
            String priceWithoutCurrency = webElement.getText().replace("$", "");
            Float price = Float.parseFloat(priceWithoutCurrency);
            priceList.add(price);
        }
        return priceList;
    }
    
    public List<Float> sortPriceList(List<Float> priceList){
        priceList.sort(Comparator.reverseOrder());
        return priceList;
    }

    public List<String> getMostExpensiveProductsTitles(){
        return MostExpensiveProductsTitles;
    }

}
