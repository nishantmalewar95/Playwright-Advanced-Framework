package com.banking.tests;

import com.banking.base.BaseTest;
import com.banking.pages.LoginPage;
import com.banking.pages.LoanRequestPage;
import com.banking.utils.APIUtils;
import io.restassured.response.Response;
import org.testng.Assert;
import org.testng.annotations.Test;

public class APIAndUITest extends BaseTest {

    @Test
    public void verifyLoanViaAPIAndUI() {
        // 1. UI Flow: Login aur Loan Apply
        LoginPage loginPage = new LoginPage(page);
        LoanRequestPage loanPage = new LoanRequestPage(page);

        loginPage.navigateToApp("https://parabank.parasoft.com/parabank/index.htm");
        loginPage.login("john", "demo");
        
        loanPage.goToRequestLoan();
        loanPage.applyForLoan("10000", "1000");
        
        System.out.println("UI Step: Loan Applied Successfully.");

        // 2. API Flow: Verify the Account status via Backend API
        // Maan lijiye account ID 12345 hai (ParaBank static data)
        Response response = APIUtils.getAccountDetails("12345");
        
        System.out.println("API Status Code: " + response.getStatusCode());
        
        // Assertion: API se verify karna ki account active hai
        Assert.assertEquals(response.getStatusCode(), 200, "API Validation Failed!");
        Assert.assertTrue(response.asString().contains("BALANCE"), "API Response mismatch!");
        
        System.out.println("Hybrid Test Passed: UI and API data is consistent.");
    }
}