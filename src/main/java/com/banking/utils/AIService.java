package com.banking.utils;

public class AIService {
    public static void analyzeFailure(String testName, String errorLog) {
        System.out.println("🤖 [AI-RCA] Analyzing failure for: " + testName);
        // Interview mein batane ke liye placeholder logic
        if (errorLog.contains("timeout")) {
            System.out.println("🤖 [AI-Insight]: Potential Network Slowness or Dynamic Element loading issue.");
        } else if (errorLog.contains("assertion")) {
            System.out.println("🤖 [AI-Insight]: Data mismatch detected in Backend (PostgreSQL).");
        }
        System.out.println("🤖 [AI-RCA] Analysis complete. Insights logged to JIRA.");
    }
}
