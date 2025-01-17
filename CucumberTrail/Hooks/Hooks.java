package org.Hooks;


import io.cucumber.java.After;
import io.cucumber.java.Before;
import org.openqa.selenium.WebDriver;
import org.utils.*;

public class Hooks {
    WebDriver driver;

    @Before
    public void setUp() {
        JsonFileManager jsonFileManager = new JsonFileManager("src\\test\\java\\org\\testData\\CheckoutTestData.json");
        driver = BrowserFactory.getBrowser(PropertiesManager.getProperty("BrowserName"));
    }

    @After
    public void tearDown() { BrowserActions.closeAllBrowserTabs(driver); }

}