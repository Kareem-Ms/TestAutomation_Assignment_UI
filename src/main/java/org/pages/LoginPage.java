package org.pages;

import io.qameta.allure.Step;
import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.utils.ElementActions;

public class LoginPage {

    ElementActions elementActions;

    public LoginPage(WebDriver driver){
        elementActions = new ElementActions(driver);
    }

    //Locators
    By userNameInputLocator = By.id("user-name");
    By passwordInputLocator = By.id("password");
    By loginBtnLocator = By.id("login-button");
    By errorLoginMsgLocator = By.xpath("//h3[@data-test = 'error']");
    By userNameXIconLocator = By.xpath("//input[@id = 'user-name']/following-sibling::*[@data-icon = 'times-circle']");
    By passwordXIconLocator = By.xpath("//input[@id = 'password']/following-sibling::*[@data-icon = 'times-circle']");


    //Actions
    @Step("Login with username: {userName} and password {password}")
    public void login(String userName, String password){
        elementActions.type(userNameInputLocator,userName);
        elementActions.type(passwordInputLocator,password);
        elementActions.click(loginBtnLocator);
    }

    @Step("Get login error message")
    public String getLoginErrorMsg(){
        return elementActions.getText(errorLoginMsgLocator);
    }

    @Step("Check if the x icon beside username input appears")
    public boolean isUsernameXiconExist(){
        return elementActions.isElementExist(userNameXIconLocator);
    }

    @Step("Check if the x icon beside password input appears")
    public boolean isPasswordXiconExist(){
        return elementActions.isElementExist(passwordXIconLocator);
    }

}
