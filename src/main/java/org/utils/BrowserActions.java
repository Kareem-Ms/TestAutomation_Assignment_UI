package org.utils;

import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebDriverException;

import static org.testng.AssertJUnit.fail;

public class BrowserActions {

    public static void navigateToUrl(WebDriver driver, String url) {
        try {
            driver.manage().window().maximize();
            driver.get(url);
        } catch (Exception e) {
            fail(e.getMessage());
        }
    }

    public static void closeAllBrowserTabs(WebDriver driver) {
        if (driver != null) {
            try {
                driver.quit();
            } catch (WebDriverException rootCauseException) {
                System.out.println(rootCauseException.getMessage());
            }
        } else {
            System.out.println("Driver is already closed and driver object is null");
        }
    }

}
