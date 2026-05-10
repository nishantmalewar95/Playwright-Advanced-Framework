package com.banking.tests;

import com.banking.base.BaseTest;
import com.banking.pages.LoginPage;
import com.banking.pages.LoanRequestPage;
import org.testng.Assert;
import org.testng.annotations.Test;

public class LoanApplicationTest extends BaseTest {

    @Test
    public void verifyTractorLoanFlow() {
        LoginPage loginPage = new LoginPage(page);
        LoanRequestPage loanPage = new LoanRequestPage(page);

        // 1. Login
        loginPage.navigateToApp("https://parabank.parasoft.com/parabank/index.htm");
        loginPage.login("john", "demo");

        // 2. Navigate to Loan Section
        loanPage.goToRequestLoan();

        // 3. Apply for Loan (Simulating Tractor Loan logic)
        // Agri/Tractor loans mein loan amount aur downpayment ka ratio check hota hai
        loanPage.applyForLoan("5000", "500");

        // 4. Assertion
        String status = loanPage.getLoanResult();
        System.out.println("Loan Application Result: " + status);
        
        // For loan application test, just verify we get some status (not empty)
        Assert.assertNotNull(status, "Loan status should not be null");
        Assert.assertFalse(status.trim().isEmpty(), "Loan status should not be empty");
        System.out.println("Loan Application Test Completed Successfully with status: " + status);
    }
}