package org.utils;

import org.openqa.selenium.By;
import org.openqa.selenium.JavascriptExecutor;
import org.openqa.selenium.TimeoutException;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.interactions.Actions;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;

import java.time.Duration;

import static org.testng.Assert.fail;

public class ElementActions {
    private WebDriver driver;

    public ElementActions(WebDriver driver) {
        this.driver = driver;
    }

    public WebDriverWait getExplicitWait(WebDriver driver) {
        return new WebDriverWait(driver, Duration.ofSeconds(30));
    }

    public  void click(By elementLocator) {
        mouseHover(elementLocator);

        try {
            getExplicitWait(driver).until(ExpectedConditions.elementToBeClickable(elementLocator));
        } catch (TimeoutException toe) {
            fail(toe.getMessage());
        }

        try {
            driver.findElement(elementLocator).click();
        } catch (Exception exception) {
            // Click on element using JavascriptExecutor in case of the click is not performed
            try {
                ((JavascriptExecutor) driver).executeScript("arguments[0].click();",
                        driver.findElement(elementLocator));
            } catch (Exception e) {
                fail("Couldn't click on the element:" + elementLocator, e);
            }
        }
    }

    public  void mouseHover(By elementLocator) {
        locatingElement(elementLocator);
        try {
            new Actions(driver)
                    .moveToElement(driver.findElement(elementLocator))
                    .perform();
        } catch (Exception e) {
            fail(e.getMessage());
        }
    }

    public void type(By elementLocator, String text) {
        locatingElement(elementLocator);
        try {
            driver.findElement(elementLocator).clear();

            driver.findElement(elementLocator).sendKeys(text);

            if (!driver.findElement(elementLocator).getAttribute("value").contains(text)) {
                // If it wasn't written successfully we try to type using JavascriptExecutor
                ((JavascriptExecutor) driver).executeScript(
                        "arguments[0].setAttribute('value', '" + text + "')",
                        driver.findElement(elementLocator));
            }

        } catch (Exception e) {
            fail(e.getMessage());
        }

    }


    public String getText(By elementLocator) {
        locatingElement(elementLocator);
        try {
            return driver.findElement(elementLocator).getText();
        } catch (Exception e) {
            fail(e.getMessage());
        }
        return null;
    }

    private void locatingElement(By elementLocator) {
        try {
            getExplicitWait(driver).until(ExpectedConditions.visibilityOfElementLocated(elementLocator));
            if (!driver.findElement(elementLocator).isDisplayed()) {
                fail("The element [" + elementLocator.toString() + "] is not Displayed");
            }
        } catch (TimeoutException toe) {
            fail(toe.getMessage());
        }
    }

    public boolean isElementExist(By elementLocator) {
        getExplicitWait(driver).until(ExpectedConditions.visibilityOfElementLocated(elementLocator));
        return driver.findElement(elementLocator).isDisplayed();
    }

}