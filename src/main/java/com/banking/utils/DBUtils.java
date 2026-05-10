package com.banking.utils;

import java.sql.*;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class DBUtils {
    
    // Connection ko static rakha hai taaki BaseTest isse close kar sake
    private static Connection connection = null;

    // Database Credentials (Inhe ideally config.properties se aana chahiye)
    private static final String URL = "jdbc:postgresql://localhost:5432/parabank_db";
    private static final String USER = "postgres";
    private static final String PASSWORD = "admin";

    /**
     * Singleton Connection Method:
     * Agar connection pehle se open hai toh wahi return karega, naya nahi banayega.
     */
    public static Connection getConnection() throws SQLException {
        if (connection == null || connection.isClosed()) {
            try {
                Class.forName("org.postgresql.Driver"); // Driver load karna mat bhulna
                connection = DriverManager.getConnection(URL, USER, PASSWORD);
                System.out.println("DB Connection Established.");
            } catch (ClassNotFoundException e) {
                System.err.println("PostgreSQL Driver not found!");
                e.printStackTrace();
            }
        }
        return connection;
    }

    /**
     * Query Execute karne ka logic
     */
    public static List<Map<String, Object>> executeSelectQuery(String query) {
        List<Map<String, Object>> rows = new ArrayList<>();
        // Try-with-resources sirf Statement aur ResultSet ke liye (Connection hum manually close karenge)
        try {
            Connection conn = getConnection();
            try (Statement stmt = conn.createStatement();
                 ResultSet rs = stmt.executeQuery(query)) {

                ResultSetMetaData metaData = rs.getMetaData();
                int columnCount = metaData.getColumnCount();

                while (rs.next()) {
                    Map<String, Object> row = new HashMap<>();
                    for (int i = 1; i <= columnCount; i++) {
                        row.put(metaData.getColumnName(i), rs.getObject(i));
                    }
                    rows.add(row);
                }
            }
        } catch (SQLException e) {
            System.err.println("Error executing query: " + query);
            e.printStackTrace();
        }
        return rows;
    }

    /**
     * Yeh wahi method hai jo BaseTest ke @AfterSuite se call hoga
     */
    public static void closeConnection() {
        try {
            if (connection != null && !connection.isClosed()) {
                connection.close();
                connection = null; // Memory leak se bachne ke liye null set kiya
                System.out.println("Database connection closed successfully.");
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }
}