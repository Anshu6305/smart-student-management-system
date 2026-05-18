package com.studentms.servlet;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import java.io.IOException;
import java.io.PrintWriter;

/**
 * LogoutServlet.java — Handles user logout
 * 
 * URL Pattern: /api/logout
 * Method: GET
 * 
 * Invalidates the current session and returns a success response.
 */
public class LogoutServlet extends HttpServlet {

    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {

        response.setContentType("application/json");
        response.setCharacterEncoding("UTF-8");
        PrintWriter out = response.getWriter();

        // Invalidate the existing session if one exists
        HttpSession session = request.getSession(false);
        if (session != null) {
            session.invalidate();
        }

        out.print("{\"success\": true, \"message\": \"Logged out successfully\"}");
    }
}
