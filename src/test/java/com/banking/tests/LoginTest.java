package com.banking.tests;

import com.banking.base.BaseTest;
import com.banking.pages.LoginPage;
import org.testng.annotations.Test;

/**
 * Sample Login Test Class
 * Extends BaseTest to inherit Playwright setup and teardown
 */
public class LoginTest extends BaseTest {
    
    @Test(description = "Test valid login functionality")
    public void testValidLogin() {
        LoginPage loginPage = new LoginPage(page);
        
        // Navigate to application
        loginPage.navigateToApp("https://parabank.parasoft.com/parabank/index.htm");
        
        // Perform login
        loginPage.login("john", "demo");
        
        // Add your assertions here
        String title = loginPage.getPageTitle();
        System.out.println("Page Title after login: " + title);
    }
    
    @Test(description = "Test invalid login functionality")
    public void testInvalidLogin() {
        LoginPage loginPage = new LoginPage(page);
        
        // Navigate to application
        loginPage.navigateToApp("https://parabank.parasoft.com/parabank/index.htm");
        
        // Perform login with invalid credentials
        loginPage.login("invalid", "invalid");
        
        // Add your assertions here
        String title = loginPage.getPageTitle();
        System.out.println("Page Title after invalid login: " + title);
    }
}
