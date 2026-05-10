package com.banking.tests;

import com.banking.utils.DBUtils;
import org.testng.Assert;
import org.testng.annotations.Test;
import java.util.List;
import java.util.Map;

public class LoanVerificationTest {

    @Test
    public void verifyLoanDisbursementInDB() {
        // Step 1: Query taiyar karein (Parabank schema ke according)
        String customerId = "12345";
        String query = "SELECT * FROM loans WHERE customer_id = '" + customerId + "' AND status = 'Approved'";

        // Step 2: DBUtils use karke data fetch karein
        List<Map<String, Object>> result = DBUtils.executeSelectQuery(query);

        // Step 3: TestNG Assertion
        // Check karein ki list khali toh nahi hai (Record exists)
        Assert.assertFalse(result.isEmpty(), "Loan record not found in database for Customer: " + customerId);

        // Specific data validate karna (Column name 'amount' check karein)
        Object loanAmount = result.get(0).get("amount");
        System.out.println("Loan Amount from DB: " + loanAmount);
        Assert.assertNotNull(loanAmount, "Loan amount should not be null");
    }
}