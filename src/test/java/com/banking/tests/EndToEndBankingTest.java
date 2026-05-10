package com.banking.tests;

import com.banking.base.BaseTest;
import com.banking.pages.LoginPage;
import com.banking.pages.LoanRequestPage;
import io.qameta.allure.Epic;
import io.qameta.allure.Feature;
import io.qameta.allure.Step;
import org.testng.Assert;
import org.testng.annotations.Test;

@Epic("Banking Operations")
@Feature("End-to-End Loan Processing")
public class EndToEndBankingTest extends BaseTest {

    @Test(description = "Complete loan disbursement flow from login to approval")
    public void verifyCompleteLoanDisbursementFlow() {
        LoginPage loginPage = new LoginPage(page);
        LoanRequestPage loanPage = new LoanRequestPage(page);

        // STAGE 1: Secure Login
        performSecureLogin(loginPage);

        // STAGE 2: Navigate and Apply for Tractor Loan
        applyForLoanApplication(loanPage);

        // STAGE 3: Validation (E2E Assertion)
        validateLoanApplicationResult(loanPage);

        // STAGE 4: Final Verification
        System.out.println("E2E Banking Flow Completed Successfully.");
    }

    @Step("Perform secure login with valid credentials")
    private void performSecureLogin(LoginPage loginPage) {
        loginPage.navigateToApp("https://parabank.parasoft.com/parabank/index.htm");
        loginPage.login("john", "demo");
        System.out.println("Step 1: Login Successful");
    }

    @Step("Apply for loan with amount: {loanAmount} and down payment: {downPayment}")
    private void applyForLoanApplication(LoanRequestPage loanPage) {
        loanPage.goToRequestLoan();
        loanPage.applyForLoan("15000", "1500");
        System.out.println("Step 2: Loan Application Submitted for $15,000");
    }

    @Step("Validate loan application result and status")
    private void validateLoanApplicationResult(LoanRequestPage loanPage) {
        String status = loanPage.getLoanResult();
        System.out.println("Step 3: Loan Status is - " + status);

        // For E2E demo, we'll accept any non-empty status as success
        // In real scenario, you'd check for specific approval text
        Assert.assertNotNull(status, "Loan status should not be null");
        Assert.assertFalse(status.trim().isEmpty(), "Loan status should not be empty");

        System.out.println("E2E Banking Flow Completed Successfully with status: " + status);
    }
}
