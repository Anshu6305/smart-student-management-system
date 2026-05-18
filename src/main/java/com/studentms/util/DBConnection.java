package com.studentms.util;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;

/**
 * DBConnection.java — Database Connection Utility
 * 
 * Provides a static method to get a MySQL database connection.
 * All database credentials are configured here in one place.
 * 
 * HOW TO USE:
 *   Connection conn = DBConnection.getConnection();
 * 
 * SETUP:
 *   1. Make sure MySQL is running on your machine
 *   2. Update the URL, USER, and PASSWORD below if needed
 *   3. Make sure the mysql-connector-j JAR is in your classpath
 */
public class DBConnection {

    // ---- Database Configuration ----
    // Change these values to match your MySQL setup
    private static final String URL = "jdbc:mysql://localhost:3306/student_management_system";
    private static final String USER = "root";
    private static final String PASSWORD = "root";  // Change this to your MySQL password

    // ---- JDBC Driver ----
    private static final String DRIVER = "com.mysql.cj.jdbc.Driver";

    /**
     * Returns a new database connection.
     * 
     * @return Connection object connected to the MySQL database
     * @throws SQLException if connection fails
     */
    public static Connection getConnection() throws SQLException {
        try {
            // Step 1: Load the MySQL JDBC driver
            Class.forName(DRIVER);
        } catch (ClassNotFoundException e) {
            System.err.println("ERROR: MySQL JDBC Driver not found!");
            System.err.println("Make sure mysql-connector-j JAR is in your classpath.");
            throw new SQLException("JDBC Driver not found", e);
        }

        // Step 2: Create and return the connection
        return DriverManager.getConnection(URL, USER, PASSWORD);
    }
}
