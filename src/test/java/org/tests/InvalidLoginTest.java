package org.tests;

import io.qameta.allure.Epic;
import io.qameta.allure.Feature;
import org.openqa.selenium.WebDriver;
import org.pages.LoginPage;
import org.testng.Assert;
import org.testng.annotations.*;
import org.testng.annotations.Test;
import org.utils.*;

@Epic("Regression Tests")
@Feature("Invalid Login Tests")
public class InvalidLoginTest {

    //Variables
    ThreadLocal<WebDriver> driver = new ThreadLocal<>();
    ThreadLocal<JsonFileManager> jsonFileManager = new ThreadLocal<>();
    LoginPage loginPage;

    //Tests
    @Test(description = "Verify login with invalid credentials")
    public void loginWithInvalidCredintials() {
        loginPage.login(jsonFileManager.get().getTestData("userName"),jsonFileManager.get().getTestData("password"));
        Assert.assertTrue(loginPage.getLoginErrorMsg().contains(jsonFileManager.get().getTestData("InvalidLoginErrorMsg")));
        Assert.assertTrue(loginPage.isUsernameXiconExist());
        Assert.assertTrue(loginPage.isPasswordXiconExist());
    }

    //Configurations
    @BeforeClass
    public void setUp() {
        jsonFileManager.set(new JsonFileManager("src\\test\\java\\org\\testData\\InvalidLoginTestData.json"));
        BrowserFactory browserFactory = new BrowserFactory();
        driver.set(browserFactory.getBrowser(PropertiesManager.getProperty("BrowserName")));
        BrowserActions.navigateToUrl(driver.get(), PropertiesManager.getProperty("sucedemoUrl"));
        loginPage = new LoginPage(driver.get());
    }

    @AfterClass
    public void tearDown() {
        BrowserActions.closeAllBrowserTabs(driver.get());
    }

}