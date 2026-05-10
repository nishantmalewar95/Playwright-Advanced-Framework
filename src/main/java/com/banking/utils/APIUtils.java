package com.banking.utils;

import io.restassured.RestAssured;
import io.restassured.response.Response;
import io.restassured.specification.RequestSpecification;

public class APIUtils {
    
    private static final String BASE_URL = "https://parabank.parasoft.com/parabank/services/bank";

    public static Response getAccountDetails(String accountId) {
        RequestSpecification request = RestAssured.given();
        // Banking API mein content type set karna zaroori hai
        request.header("Content-Type", "application/json");
        
        Response response = request.get(BASE_URL + "/accounts/" + accountId);
        return response;
    }

    public static Response createLoanAccount(String customerId, String amount) {
        // Yahan hum POST call simulate kar sakte hain
        return RestAssured.given()
                .queryParam("customerId", customerId)
                .queryParam("amount", amount)
                .post(BASE_URL + "/requestLoan");
    }
}
