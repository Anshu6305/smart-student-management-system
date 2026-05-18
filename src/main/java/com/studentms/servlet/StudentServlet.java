package com.studentms.servlet;

import com.studentms.dao.StudentDAO;
import com.studentms.model.Student;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.BufferedReader;
import java.io.IOException;
import java.io.PrintWriter;
import java.util.List;

/**
 * StudentServlet.java — Handles all Student CRUD operations
 * 
 * URL Pattern: /api/students
 * 
 * Methods:
 *   GET    → Get all students, or search with ?search=keyword, or get one with ?id=123
 *   POST   → Add a new student (form data in request body)
 *   PUT    → Update an existing student
 *   DELETE → Delete a student by ?id=123
 */
public class StudentServlet extends HttpServlet {

    private StudentDAO studentDAO = new StudentDAO();

    // ================================================================
    // GET — Retrieve students (all, by ID, or by search keyword)
    // ================================================================
    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {

        response.setContentType("application/json");
        response.setCharacterEncoding("UTF-8");
        PrintWriter out = response.getWriter();

        try {
            String idParam = request.getParameter("id");
            String searchParam = request.getParameter("search");

            if (idParam != null && !idParam.isEmpty()) {
                // GET single student by ID
                int id = Integer.parseInt(idParam);
                Student student = studentDAO.getStudentById(id);

                if (student != null) {
                    out.print(studentToJson(student));
                } else {
                    response.setStatus(HttpServletResponse.SC_NOT_FOUND);
                    out.print("{\"error\": \"Student not found\"}");
                }

            } else if (searchParam != null && !searchParam.isEmpty()) {
                // GET students matching search keyword
                List<Student> students = studentDAO.searchStudents(searchParam);
                out.print(studentsToJsonArray(students));

            } else {
                // GET all students
                List<Student> students = studentDAO.getAllStudents();
                out.print(studentsToJsonArray(students));
            }

        } catch (Exception e) {
            response.setStatus(HttpServletResponse.SC_INTERNAL_SERVER_ERROR);
            out.print("{\"error\": \"Failed to fetch students: " + escapeJson(e.getMessage()) + "\"}");
            e.printStackTrace();
        }
    }

    // ================================================================
    // POST — Add a new student
    // ================================================================
    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {

        response.setContentType("application/json");
        response.setCharacterEncoding("UTF-8");
        PrintWriter out = response.getWriter();

        try {
            // Extract student data from request parameters
            Student student = extractStudentFromRequest(request);

            // Validate required fields
            if (student.getName() == null || student.getName().trim().isEmpty()) {
                response.setStatus(HttpServletResponse.SC_BAD_REQUEST);
                out.print("{\"success\": false, \"message\": \"Student name is required\"}");
                return;
            }

            // Add to database
            boolean success = studentDAO.addStudent(student);

            if (success) {
                response.setStatus(HttpServletResponse.SC_CREATED);
                out.print("{\"success\": true, \"message\": \"Student added successfully\"}");
            } else {
                response.setStatus(HttpServletResponse.SC_INTERNAL_SERVER_ERROR);
                out.print("{\"success\": false, \"message\": \"Failed to add student\"}");
            }

        } catch (Exception e) {
            response.setStatus(HttpServletResponse.SC_BAD_REQUEST);
            out.print("{\"success\": false, \"message\": \"Invalid data: " + escapeJson(e.getMessage()) + "\"}");
            e.printStackTrace();
        }
    }

    // ================================================================
    // PUT — Update an existing student
    // ================================================================
    @Override
    protected void doPut(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {

        response.setContentType("application/json");
        response.setCharacterEncoding("UTF-8");
        PrintWriter out = response.getWriter();

        try {
            // Extract student data from request parameters
            Student student = extractStudentFromRequest(request);

            // ID is required for update
            String idParam = request.getParameter("id");
            if (idParam == null || idParam.isEmpty()) {
                response.setStatus(HttpServletResponse.SC_BAD_REQUEST);
                out.print("{\"success\": false, \"message\": \"Student ID is required for update\"}");
                return;
            }
            student.setId(Integer.parseInt(idParam));

            // Update in database
            boolean success = studentDAO.updateStudent(student);

            if (success) {
                out.print("{\"success\": true, \"message\": \"Student updated successfully\"}");
            } else {
                response.setStatus(HttpServletResponse.SC_NOT_FOUND);
                out.print("{\"success\": false, \"message\": \"Student not found or update failed\"}");
            }

        } catch (Exception e) {
            response.setStatus(HttpServletResponse.SC_BAD_REQUEST);
            out.print("{\"success\": false, \"message\": \"Invalid data: " + escapeJson(e.getMessage()) + "\"}");
            e.printStackTrace();
        }
    }

    // ================================================================
    // DELETE — Remove a student by ID
    // ================================================================
    @Override
    protected void doDelete(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {

        response.setContentType("application/json");
        response.setCharacterEncoding("UTF-8");
        PrintWriter out = response.getWriter();

        try {
            String idParam = request.getParameter("id");

            if (idParam == null || idParam.isEmpty()) {
                response.setStatus(HttpServletResponse.SC_BAD_REQUEST);
                out.print("{\"success\": false, \"message\": \"Student ID is required\"}");
                return;
            }

            int id = Integer.parseInt(idParam);
            boolean success = studentDAO.deleteStudent(id);

            if (success) {
                out.print("{\"success\": true, \"message\": \"Student deleted successfully\"}");
            } else {
                response.setStatus(HttpServletResponse.SC_NOT_FOUND);
                out.print("{\"success\": false, \"message\": \"Student not found\"}");
            }

        } catch (Exception e) {
            response.setStatus(HttpServletResponse.SC_INTERNAL_SERVER_ERROR);
            out.print("{\"success\": false, \"message\": \"Failed to delete student: " + escapeJson(e.getMessage()) + "\"}");
            e.printStackTrace();
        }
    }

    // ================================================================
    // HELPER — Extract Student object from request parameters
    // ================================================================
    private Student extractStudentFromRequest(HttpServletRequest request) {
        Student student = new Student();
        student.setName(request.getParameter("name"));
        student.setRollNumber(request.getParameter("rollNumber"));
        student.setBranch(request.getParameter("branch"));
        student.setEmail(request.getParameter("email"));
        student.setPhone(request.getParameter("phone"));

        // Parse numeric fields with defaults
        String semesterStr = request.getParameter("semester");
        student.setSemester(semesterStr != null ? Integer.parseInt(semesterStr) : 1);

        String attendanceStr = request.getParameter("attendance");
        student.setAttendance(attendanceStr != null ? Double.parseDouble(attendanceStr) : 0.0);

        String cgpaStr = request.getParameter("cgpa");
        student.setCgpa(cgpaStr != null ? Double.parseDouble(cgpaStr) : 0.0);

        return student;
    }

    // ================================================================
    // HELPER — Convert a single Student to JSON string
    // ================================================================
    private String studentToJson(Student s) {
        return "{" +
            "\"id\": " + s.getId() + ", " +
            "\"name\": \"" + escapeJson(s.getName()) + "\", " +
            "\"rollNumber\": \"" + escapeJson(s.getRollNumber()) + "\", " +
            "\"branch\": \"" + escapeJson(s.getBranch()) + "\", " +
            "\"semester\": " + s.getSemester() + ", " +
            "\"email\": \"" + escapeJson(s.getEmail()) + "\", " +
            "\"phone\": \"" + escapeJson(s.getPhone()) + "\", " +
            "\"attendance\": " + s.getAttendance() + ", " +
            "\"cgpa\": " + s.getCgpa() +
            "}";
    }

    // ================================================================
    // HELPER — Convert a list of Students to a JSON array string
    // ================================================================
    private String studentsToJsonArray(List<Student> students) {
        StringBuilder json = new StringBuilder("[");
        for (int i = 0; i < students.size(); i++) {
            if (i > 0) json.append(", ");
            json.append(studentToJson(students.get(i)));
        }
        json.append("]");
        return json.toString();
    }

    // ================================================================
    // HELPER — Escape special characters for JSON strings
    // ================================================================
    private String escapeJson(String text) {
        if (text == null) return "";
        return text.replace("\\", "\\\\")
                   .replace("\"", "\\\"")
                   .replace("\n", "\\n")
                   .replace("\r", "\\r")
                   .replace("\t", "\\t");
    }
}
