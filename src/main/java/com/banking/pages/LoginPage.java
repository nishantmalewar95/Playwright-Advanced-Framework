package com.banking.pages;

import com.microsoft.playwright.Page;
import com.microsoft.playwright.options.LoadState;

public class LoginPage {
    private final Page page;

    // Locators using Playwright's best practices
    private final String usernameInput = "input[name='username']";
    private final String passwordInput = "input[name='password']";
    private final String loginButton = "input[type='submit']";

    public LoginPage(Page page) {
        this.page = page;
    }

    public void navigateToApp(String url) {
        page.navigate(url);
        page.waitForLoadState(LoadState.DOMCONTENTLOADED);
        page.waitForSelector(usernameInput);
    }

    public void login(String user, String pass) {
        page.fill(usernameInput, user);
        page.fill(passwordInput, pass);
        page.click(loginButton);
        page.waitForLoadState(LoadState.NETWORKIDLE);
    }
    
    public String getPageTitle() {
        return page.title();
    }
}
