package com.automation.pages;

import org.openqa.selenium.By;

import com.automation.utils.SeleniumActions;

public class LoginPage {
    private SeleniumActions seleniumActions;
    private By usernameField = By.id("username");
    private By passwordField = By.id("password");
    private By loginButton = By.id("loginBtn");

    public LoginPage(SeleniumActions seleniumActions) {
        this.seleniumActions = seleniumActions;
    }

    public void enterUsername(String username) {
        seleniumActions.enterText(usernameField, username);
    }

    public void enterPassword(String password) {
        seleniumActions.enterText(passwordField, password);
    }

    public void clickLogin() {
        seleniumActions.clickElement(loginButton);
    }

    public void login(String username, String password) {
        enterUsername(username);
        enterPassword(password);
        clickLogin();
    }
}