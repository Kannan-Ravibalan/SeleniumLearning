package com.automation.utils;

import org.openqa.selenium.By;
import org.openqa.selenium.JavascriptExecutor;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.interactions.Actions;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.Select;
import org.openqa.selenium.support.ui.WebDriverWait;
import java.time.Duration;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;
import java.util.Set;

public class SeleniumActions {
    private WebDriver driver;
    private WebDriverWait wait;
    private Actions actions;
    private JavascriptExecutor js;

    public SeleniumActions(WebDriver driver) {
        this.driver = driver;
        this.wait = new WebDriverWait(driver, Duration.ofSeconds(10));
        this.actions = new Actions(driver);
        this.js = (JavascriptExecutor) driver;
    }

    public void clickElement(By locator) {
        wait.until(ExpectedConditions.elementToBeClickable(locator)).click();
    }

    public void enterText(By locator, String text) {
        wait.until(ExpectedConditions.visibilityOfElementLocated(locator)).sendKeys(text);
    }

    public String getText(By locator) {
        return wait.until(ExpectedConditions.visibilityOfElementLocated(locator)).getText();
    }

    public boolean isElementDisplayed(By locator) {
        return wait.until(ExpectedConditions.visibilityOfElementLocated(locator)).isDisplayed();
    }

    public void waitForElementToBeVisible(By locator) {
        wait.until(ExpectedConditions.visibilityOfElementLocated(locator));
    }
    
    public List<WebElement> getElements(By locator) {
        return wait.until(ExpectedConditions.presenceOfAllElementsLocatedBy(locator));
    }
    
    public void clearText(By locator) {
        wait.until(ExpectedConditions.visibilityOfElementLocated(locator)).clear();
    }
    
    public void selectCheckbox(By locator) {
        WebElement checkbox = wait.until(ExpectedConditions.elementToBeClickable(locator));
        if (!checkbox.isSelected()) {
            checkbox.click();
        }
    }

    public void deselectCheckbox(By locator) {
        WebElement checkbox = wait.until(ExpectedConditions.elementToBeClickable(locator));
        if (checkbox.isSelected()) {
            checkbox.click();
        }
    }

    public boolean isRadioButtonSelected(By locator) {
        WebElement radioButton = wait.until(ExpectedConditions.visibilityOfElementLocated(locator));
        return radioButton.isSelected();
    }

    public void selectRadioButton(By locator) {
        WebElement radioButton = wait.until(ExpectedConditions.elementToBeClickable(locator));
        if (!radioButton.isSelected()) {
            radioButton.click();
        }
    }

    public void switchToFrame(By locator) {
        driver.switchTo().frame(wait.until(ExpectedConditions.visibilityOfElementLocated(locator)));
    }

    public void switchToDefaultContent() {
        driver.switchTo().defaultContent();
    }
    
    public void acceptAlert() {
        wait.until(ExpectedConditions.alertIsPresent()).accept();
    }
    
    public void dismissAlert() {
        wait.until(ExpectedConditions.alertIsPresent()).dismiss();
    }
    
    public String getAlertText() {
        return wait.until(ExpectedConditions.alertIsPresent()).getText();
    }
    
    public void switchToWindowByTitle(String title) {
        Set<String> windowHandles = driver.getWindowHandles();
        for (String handle : windowHandles) {
            driver.switchTo().window(handle);
            if (driver.getTitle().equals(title)) {
                break;
            }
        }
    }

    public void switchToWindowByIndex(int index) {
        List<String> windowHandles = new ArrayList<>(driver.getWindowHandles());
        if (index < windowHandles.size()) {
            driver.switchTo().window(windowHandles.get(index));
        }
    }
    
    public void closeCurrentWindow() {
        driver.close();
    }
    
    public void switchToParentWindow() {
        String parentWindow = driver.getWindowHandles().iterator().next();
        driver.switchTo().window(parentWindow);
    }

    public void switchToNewTab() {
        List<String> windowHandles = new ArrayList<>(driver.getWindowHandles());
        driver.switchTo().window(windowHandles.get(windowHandles.size() - 1));
    }
    
    public void scrollToBottom() {
        js.executeScript("window.scrollTo(0, document.body.scrollHeight)");
    }
    
    public void scrollToTop() {
        js.executeScript("window.scrollTo(0, 0)");
    }

    public void moveToElementAndClick(By locator) {
        WebElement element = wait.until(ExpectedConditions.visibilityOfElementLocated(locator));
        actions.moveToElement(element).click().perform();
    }

    public void selectDropdownByIndex(By locator, int index) {
        Select dropdown = new Select(wait.until(ExpectedConditions.visibilityOfElementLocated(locator)));
        dropdown.selectByIndex(index);
    }

    public void selectDropdownByText(By locator, String text) {
        Select dropdown = new Select(wait.until(ExpectedConditions.visibilityOfElementLocated(locator)));
        dropdown.selectByVisibleText(text);
    }

    public List<WebElement> getAllDropdownOptions(By locator) {
        Select dropdown = new Select(wait.until(ExpectedConditions.visibilityOfElementLocated(locator)));
        return dropdown.getOptions();
    }

    public String getSelectedDropdownOption(By locator) {
        Select dropdown = new Select(wait.until(ExpectedConditions.visibilityOfElementLocated(locator)));
        return dropdown.getFirstSelectedOption().getText();
    }
    
    public String getWebTableValue(By tableLocator, int rowIndex, int colIndex) {
        WebElement table = wait.until(ExpectedConditions.visibilityOfElementLocated(tableLocator));
        List<WebElement> rows = table.findElements(By.tagName("tr"));
        if (rowIndex < rows.size()) {
            List<WebElement> columns = rows.get(rowIndex).findElements(By.tagName("td"));
            if (colIndex < columns.size()) {
                return columns.get(colIndex).getText();
            }
        }
        return "";
    }
    
    public void waitForPageLoad() {
        wait.until(webDriver -> js.executeScript("return document.readyState").equals("complete"));
    }
    
    public boolean isElementPresent(By locator) {
        return driver.findElements(locator).size() > 0;
    }
    
    public List<String> getAllWindowHandles() {
        return new ArrayList<>(driver.getWindowHandles());
    }
}