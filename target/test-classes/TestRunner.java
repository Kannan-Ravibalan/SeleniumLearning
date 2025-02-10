package com.automation.framework;

import org.testng.annotations.AfterMethod;
import org.testng.annotations.BeforeMethod;
import org.testng.annotations.Test;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.chrome.ChromeDriver;
import com.automation.utils.ConfigReader;
import com.automation.utils.ExcelReader;
import com.automation.utils.JsonReader;
import com.automation.utils.ReportManager;
import com.automation.pages.LoginPage;
import com.automation.utils.SeleniumActions;
import io.cucumber.testng.CucumberOptions;
import io.cucumber.testng.AbstractTestNGCucumberTests;

import java.io.IOException;

@CucumberOptions(
        features = "src/test/resources/features",
        glue = "com.automation.stepdefinitions",
        plugin = {"pretty", "html:target/cucumber-reports.html"}
)
public class TestRunner extends AbstractTestNGCucumberTests {
    private WebDriver driver;
    private LoginPage loginPage;
    private SeleniumActions seleniumActions;

    @BeforeMethod
    public void setUp() {
        System.setProperty("webdriver.chrome.driver", "drivers/chromedriver.exe");
        driver = new ChromeDriver();
        driver.manage().window().maximize();
        seleniumActions = new SeleniumActions(driver);
        loginPage = new LoginPage(seleniumActions);
    }

    @Test
    public void testLogin() throws IOException {
        String url = ConfigReader.getProperty("app.url");
        String username = ConfigReader.getProperty("app.username");
        String password = ConfigReader.getProperty("app.password");
        driver.get(url);
        loginPage.login(username, password);
    }

    @AfterMethod
    public void tearDown() {
        if (driver != null) {
            driver.quit();
        }
    }
}