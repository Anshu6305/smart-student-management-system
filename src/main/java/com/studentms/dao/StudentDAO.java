package com.studentms.dao;

import com.studentms.model.Student;
import com.studentms.util.DBConnection;

import java.sql.*;
import java.util.ArrayList;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;

/**
 * StudentDAO.java — Data Access Object
 * 
 * This class handles ALL database operations for the Student entity.
 * It uses JDBC PreparedStatements to prevent SQL injection.
 * 
 * Methods:
 *   - addStudent()          → INSERT a new student
 *   - getStudentById()      → SELECT one student by ID
 *   - getAllStudents()       → SELECT all students
 *   - updateStudent()       → UPDATE an existing student
 *   - deleteStudent()       → DELETE a student by ID
 *   - searchStudents()      → SEARCH by name, roll number, or branch
 *   - getStudentCount()     → COUNT total students (for dashboard)
 *   - getAverageCGPA()      → AVG CGPA (for dashboard)
 *   - getAverageAttendance()→ AVG Attendance (for dashboard)
 *   - getBranchCount()      → COUNT distinct branches (for dashboard)
 */
public class StudentDAO {

    // ============================================================
    // CREATE — Add a new student
    // ============================================================
    public boolean addStudent(Student student) {
        String sql = "INSERT INTO students (name, roll_number, branch, semester, email, phone, attendance, cgpa) "
                   + "VALUES (?, ?, ?, ?, ?, ?, ?, ?)";

        try (Connection conn = DBConnection.getConnection();
             PreparedStatement ps = conn.prepareStatement(sql)) {

            // Set all parameters using the Student object
            ps.setString(1, student.getName());
            ps.setString(2, student.getRollNumber());
            ps.setString(3, student.getBranch());
            ps.setInt(4, student.getSemester());
            ps.setString(5, student.getEmail());
            ps.setString(6, student.getPhone());
            ps.setDouble(7, student.getAttendance());
            ps.setDouble(8, student.getCgpa());

            // executeUpdate() returns the number of rows affected
            int rowsAffected = ps.executeUpdate();
            return rowsAffected > 0;

        } catch (SQLException e) {
            System.err.println("Error adding student: " + e.getMessage());
            return false;
        }
    }

    // ============================================================
    // READ — Get a single student by ID
    // ============================================================
    public Student getStudentById(int id) {
        String sql = "SELECT * FROM students WHERE id = ?";

        try (Connection conn = DBConnection.getConnection();
             PreparedStatement ps = conn.prepareStatement(sql)) {

            ps.setInt(1, id);
            ResultSet rs = ps.executeQuery();

            // If a row is found, convert it to a Student object
            if (rs.next()) {
                return extractStudentFromResultSet(rs);
            }

        } catch (SQLException e) {
            System.err.println("Error fetching student by ID: " + e.getMessage());
        }
        return null;
    }

    // ============================================================
    // READ — Get all students
    // ============================================================
    public List<Student> getAllStudents() {
        List<Student> students = new ArrayList<>();
        String sql = "SELECT * FROM students ORDER BY id DESC";

        try (Connection conn = DBConnection.getConnection();
             Statement stmt = conn.createStatement();
             ResultSet rs = stmt.executeQuery(sql)) {

            // Loop through every row and convert to Student object
            while (rs.next()) {
                students.add(extractStudentFromResultSet(rs));
            }

        } catch (SQLException e) {
            System.err.println("Error fetching all students: " + e.getMessage());
        }
        return students;
    }

    // ============================================================
    // UPDATE — Update an existing student
    // ============================================================
    public boolean updateStudent(Student student) {
        String sql = "UPDATE students SET name=?, roll_number=?, branch=?, semester=?, "
                   + "email=?, phone=?, attendance=?, cgpa=? WHERE id=?";

        try (Connection conn = DBConnection.getConnection();
             PreparedStatement ps = conn.prepareStatement(sql)) {

            ps.setString(1, student.getName());
            ps.setString(2, student.getRollNumber());
            ps.setString(3, student.getBranch());
            ps.setInt(4, student.getSemester());
            ps.setString(5, student.getEmail());
            ps.setString(6, student.getPhone());
            ps.setDouble(7, student.getAttendance());
            ps.setDouble(8, student.getCgpa());
            ps.setInt(9, student.getId());  // WHERE clause

            int rowsAffected = ps.executeUpdate();
            return rowsAffected > 0;

        } catch (SQLException e) {
            System.err.println("Error updating student: " + e.getMessage());
            return false;
        }
    }

    // ============================================================
    // DELETE — Delete a student by ID
    // ============================================================
    public boolean deleteStudent(int id) {
        String sql = "DELETE FROM students WHERE id = ?";

        try (Connection conn = DBConnection.getConnection();
             PreparedStatement ps = conn.prepareStatement(sql)) {

            ps.setInt(1, id);
            int rowsAffected = ps.executeUpdate();
            return rowsAffected > 0;

        } catch (SQLException e) {
            System.err.println("Error deleting student: " + e.getMessage());
            return false;
        }
    }

    // ============================================================
    // SEARCH — Find students by name, roll number, or branch
    // ============================================================
    public List<Student> searchStudents(String keyword) {
        List<Student> students = new ArrayList<>();
        String sql = "SELECT * FROM students WHERE "
                   + "name LIKE ? OR roll_number LIKE ? OR branch LIKE ? "
                   + "ORDER BY id DESC";

        try (Connection conn = DBConnection.getConnection();
             PreparedStatement ps = conn.prepareStatement(sql)) {

            // Use % wildcards for partial matching
            String searchPattern = "%" + keyword + "%";
            ps.setString(1, searchPattern);
            ps.setString(2, searchPattern);
            ps.setString(3, searchPattern);

            ResultSet rs = ps.executeQuery();
            while (rs.next()) {
                students.add(extractStudentFromResultSet(rs));
            }

        } catch (SQLException e) {
            System.err.println("Error searching students: " + e.getMessage());
        }
        return students;
    }

    // ============================================================
    // DASHBOARD — Get total number of students
    // ============================================================
    public int getStudentCount() {
        String sql = "SELECT COUNT(*) AS total FROM students";

        try (Connection conn = DBConnection.getConnection();
             Statement stmt = conn.createStatement();
             ResultSet rs = stmt.executeQuery(sql)) {

            if (rs.next()) {
                return rs.getInt("total");
            }

        } catch (SQLException e) {
            System.err.println("Error getting student count: " + e.getMessage());
        }
        return 0;
    }

    // ============================================================
    // DASHBOARD — Get average CGPA
    // ============================================================
    public double getAverageCGPA() {
        String sql = "SELECT ROUND(AVG(cgpa), 2) AS avg_cgpa FROM students";

        try (Connection conn = DBConnection.getConnection();
             Statement stmt = conn.createStatement();
             ResultSet rs = stmt.executeQuery(sql)) {

            if (rs.next()) {
                return rs.getDouble("avg_cgpa");
            }

        } catch (SQLException e) {
            System.err.println("Error getting average CGPA: " + e.getMessage());
        }
        return 0.0;
    }

    // ============================================================
    // DASHBOARD — Get average attendance
    // ============================================================
    public double getAverageAttendance() {
        String sql = "SELECT ROUND(AVG(attendance), 2) AS avg_attendance FROM students";

        try (Connection conn = DBConnection.getConnection();
             Statement stmt = conn.createStatement();
             ResultSet rs = stmt.executeQuery(sql)) {

            if (rs.next()) {
                return rs.getDouble("avg_attendance");
            }

        } catch (SQLException e) {
            System.err.println("Error getting average attendance: " + e.getMessage());
        }
        return 0.0;
    }

    // ============================================================
    // DASHBOARD — Get number of distinct branches
    // ============================================================
    public int getBranchCount() {
        String sql = "SELECT COUNT(DISTINCT branch) AS branch_count FROM students";

        try (Connection conn = DBConnection.getConnection();
             Statement stmt = conn.createStatement();
             ResultSet rs = stmt.executeQuery(sql)) {

            if (rs.next()) {
                return rs.getInt("branch_count");
            }

        } catch (SQLException e) {
            System.err.println("Error getting branch count: " + e.getMessage());
        }
        return 0;
    }

    // ============================================================
    // DASHBOARD — Get student count per branch
    // ============================================================
    public Map<String, Integer> getBranchDistribution() {
        Map<String, Integer> distribution = new LinkedHashMap<>();
        String sql = "SELECT branch, COUNT(*) AS count FROM students GROUP BY branch ORDER BY count DESC";

        try (Connection conn = DBConnection.getConnection();
             Statement stmt = conn.createStatement();
             ResultSet rs = stmt.executeQuery(sql)) {

            while (rs.next()) {
                distribution.put(rs.getString("branch"), rs.getInt("count"));
            }

        } catch (SQLException e) {
            System.err.println("Error getting branch distribution: " + e.getMessage());
        }
        return distribution;
    }

    // ============================================================
    // HELPER — Extract a Student object from a ResultSet row
    // ============================================================
    /**
     * Converts one row of a ResultSet into a Student object.
     * This avoids duplicating the same mapping code in every method.
     */
    private Student extractStudentFromResultSet(ResultSet rs) throws SQLException {
        return new Student(
            rs.getInt("id"),
            rs.getString("name"),
            rs.getString("roll_number"),
            rs.getString("branch"),
            rs.getInt("semester"),
            rs.getString("email"),
            rs.getString("phone"),
            rs.getDouble("attendance"),
            rs.getDouble("cgpa")
        );
    }
}
