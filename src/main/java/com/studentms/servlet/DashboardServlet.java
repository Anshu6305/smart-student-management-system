package com.studentms.servlet;

import com.studentms.dao.StudentDAO;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.io.PrintWriter;
import java.util.Map;

/**
 * DashboardServlet.java — Returns dashboard statistics
 * 
 * URL Pattern: /api/dashboard
 * Method: GET
 * 
 * Returns JSON with:
 *   - totalStudents (count)
 *   - averageCGPA
 *   - averageAttendance
 *   - totalBranches (count)
 *   - branchDistribution (branch → count mapping)
 */
public class DashboardServlet extends HttpServlet {

    private StudentDAO studentDAO = new StudentDAO();

    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {

        // Set response type to JSON
        response.setContentType("application/json");
        response.setCharacterEncoding("UTF-8");
        PrintWriter out = response.getWriter();

        try {
            // Fetch all dashboard statistics from the DAO
            int totalStudents = studentDAO.getStudentCount();
            double averageCGPA = studentDAO.getAverageCGPA();
            double averageAttendance = studentDAO.getAverageAttendance();
            int totalBranches = studentDAO.getBranchCount();
            Map<String, Integer> branchDistribution = studentDAO.getBranchDistribution();

            // Build JSON response manually (no external library needed)
            StringBuilder json = new StringBuilder();
            json.append("{");
            json.append("\"totalStudents\": ").append(totalStudents).append(", ");
            json.append("\"averageCGPA\": ").append(averageCGPA).append(", ");
            json.append("\"averageAttendance\": ").append(averageAttendance).append(", ");
            json.append("\"totalBranches\": ").append(totalBranches).append(", ");

            // Build branch distribution as a JSON object
            json.append("\"branchDistribution\": {");
            int i = 0;
            for (Map.Entry<String, Integer> entry : branchDistribution.entrySet()) {
                if (i > 0) json.append(", ");
                json.append("\"").append(entry.getKey()).append("\": ").append(entry.getValue());
                i++;
            }
            json.append("}");
            json.append("}");

            out.print(json.toString());

        } catch (Exception e) {
            response.setStatus(HttpServletResponse.SC_INTERNAL_SERVER_ERROR);
            out.print("{\"error\": \"Failed to load dashboard data: " + e.getMessage() + "\"}");
            e.printStackTrace();
        }
    }
}
