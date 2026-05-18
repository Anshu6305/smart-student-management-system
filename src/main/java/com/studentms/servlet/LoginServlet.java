package com.studentms.servlet;

import com.studentms.util.DBConnection;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import java.io.IOException;
import java.io.PrintWriter;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;

/**
 * LoginServlet.java — Handles user authentication
 * 
 * URL Pattern: /api/login
 * Method: POST
 * 
 * Accepts username and password, validates against the database,
 * creates a session on success, and returns a JSON response.
 */
public class LoginServlet extends HttpServlet {

    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {

        // Set response type to JSON
        response.setContentType("application/json");
        response.setCharacterEncoding("UTF-8");
        PrintWriter out = response.getWriter();

        // Read the username and password from request parameters
        String username = request.getParameter("username");
        String password = request.getParameter("password");

        // Validate that both fields are provided
        if (username == null || username.trim().isEmpty() ||
            password == null || password.trim().isEmpty()) {
            response.setStatus(HttpServletResponse.SC_BAD_REQUEST);
            out.print("{\"success\": false, \"message\": \"Username and password are required\"}");
            return;
        }

        // Query the database to check credentials
        String sql = "SELECT * FROM users WHERE username = ? AND password = ?";

        try (Connection conn = DBConnection.getConnection();
             PreparedStatement ps = conn.prepareStatement(sql)) {

            ps.setString(1, username.trim());
            ps.setString(2, password.trim());
            ResultSet rs = ps.executeQuery();

            if (rs.next()) {
                // Login successful — create a session
                HttpSession session = request.getSession();
                session.setAttribute("userId", rs.getInt("id"));
                session.setAttribute("username", rs.getString("username"));
                session.setAttribute("fullName", rs.getString("full_name"));
                session.setMaxInactiveInterval(30 * 60); // 30 minutes

                String fullName = rs.getString("full_name");
                out.print("{\"success\": true, \"message\": \"Login successful\", \"fullName\": \"" + fullName + "\"}");
            } else {
                // Invalid credentials
                response.setStatus(HttpServletResponse.SC_UNAUTHORIZED);
                out.print("{\"success\": false, \"message\": \"Invalid username or password\"}");
            }

        } catch (Exception e) {
            // Database error
            response.setStatus(HttpServletResponse.SC_INTERNAL_SERVER_ERROR);
            out.print("{\"success\": false, \"message\": \"Server error: " + e.getMessage() + "\"}");
            e.printStackTrace();
        }
    }
}
