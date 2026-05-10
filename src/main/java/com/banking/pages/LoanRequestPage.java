package com.banking.pages;

import com.microsoft.playwright.Page;
import com.microsoft.playwright.options.LoadState;
import io.qameta.allure.Step;

public class LoanRequestPage {
    private final Page page;

    // Locators
    private final String loanAmountInput = "#amount";
    private final String downPaymentInput = "#downPayment";
    private final String fromAccountIdSelect = "#fromAccountId";
    private final String applyNowButton = "input[value='Apply Now']";
    private final String loanStatusMessage = "#loanStatus";

    public LoanRequestPage(Page page) {
        this.page = page;
    }

    public Page getPage() {
        return page;
    }

    @Step("Navigate to loan request page")
    public void goToRequestLoan() {
        page.navigate("https://parabank.parasoft.com/parabank/requestloan.htm");
        page.waitForLoadState(LoadState.DOMCONTENTLOADED);
        page.waitForSelector(loanAmountInput);
        // Add a small delay to ensure the form is fully rendered
        page.waitForTimeout(1000);
    }

    @Step("Apply for loan with amount: {amount} and down payment: {downPayment}")
    public void applyForLoan(String amount, String downPayment) {
        String firstAccountId = page.locator(fromAccountIdSelect + " option").first().getAttribute("value");
        applyForLoan(amount, downPayment, firstAccountId);
    }

    @Step("Apply for loan with amount: {amount}, down payment: {downPayment}, and account: {accountId}")
    public void applyForLoan(String amount, String downPayment, String accountId) {
        page.waitForSelector(loanAmountInput);
        page.waitForSelector(downPaymentInput);
        page.waitForSelector(fromAccountIdSelect);

        page.fill(loanAmountInput, amount);
        page.fill(downPaymentInput, downPayment);
        if (accountId == null || accountId.isEmpty()) {
            accountId = page.locator(fromAccountIdSelect + " option").first().getAttribute("value");
        }
        page.selectOption(fromAccountIdSelect, accountId);
        page.waitForSelector(applyNowButton);
        page.click(applyNowButton);
        page.waitForLoadState(LoadState.NETWORKIDLE);
        page.waitForTimeout(1000);
    }

    @Step("Get loan application result")
    public String getLoanResult() {
        page.waitForTimeout(2000);

        // Try multiple selectors for loan status
        String[] selectors = {loanStatusMessage, "#loan-status", ".title", "#rightPanel .message", "#rightPanel h1", "#rightPanel h2", "#rightPanel p", "p"};

        for (String selector : selectors) {
            try {
                String text = page.textContent(selector);
                if (text != null && !text.trim().isEmpty()) {
                    return text.trim();
                }
            } catch (Exception e) {
                // Continue to next selector
            }
        }

        // If no specific selector works, get all body text safely
        String bodyText = page.textContent("body");
        return bodyText != null ? bodyText.trim() : "";
    }
}