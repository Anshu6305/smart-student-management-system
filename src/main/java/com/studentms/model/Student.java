package com.studentms.model;

/**
 * Student.java — Model class (POJO)
 * 
 * Represents a student entity with all required fields.
 * This class follows JavaBean conventions with private fields,
 * public getters/setters, and multiple constructors.
 * 
 * Used by: StudentDAO, StudentServlet
 */
public class Student {

    // ---- Fields ----
    private int id;
    private String name;
    private String rollNumber;
    private String branch;
    private int semester;
    private String email;
    private String phone;
    private double attendance;
    private double cgpa;

    // ---- Constructors ----

    /**
     * Default no-arg constructor (needed for JavaBean pattern)
     */
    public Student() {
    }

    /**
     * Parameterized constructor — used when creating a new student (no ID yet)
     */
    public Student(String name, String rollNumber, String branch, int semester,
                   String email, String phone, double attendance, double cgpa) {
        this.name = name;
        this.rollNumber = rollNumber;
        this.branch = branch;
        this.semester = semester;
        this.email = email;
        this.phone = phone;
        this.attendance = attendance;
        this.cgpa = cgpa;
    }

    /**
     * Full constructor — used when reading from database (has ID)
     */
    public Student(int id, String name, String rollNumber, String branch, int semester,
                   String email, String phone, double attendance, double cgpa) {
        this.id = id;
        this.name = name;
        this.rollNumber = rollNumber;
        this.branch = branch;
        this.semester = semester;
        this.email = email;
        this.phone = phone;
        this.attendance = attendance;
        this.cgpa = cgpa;
    }

    // ---- Getters and Setters ----

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getRollNumber() {
        return rollNumber;
    }

    public void setRollNumber(String rollNumber) {
        this.rollNumber = rollNumber;
    }

    public String getBranch() {
        return branch;
    }

    public void setBranch(String branch) {
        this.branch = branch;
    }

    public int getSemester() {
        return semester;
    }

    public void setSemester(int semester) {
        this.semester = semester;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getPhone() {
        return phone;
    }

    public void setPhone(String phone) {
        this.phone = phone;
    }

    public double getAttendance() {
        return attendance;
    }

    public void setAttendance(double attendance) {
        this.attendance = attendance;
    }

    public double getCgpa() {
        return cgpa;
    }

    public void setCgpa(double cgpa) {
        this.cgpa = cgpa;
    }

    // ---- toString (useful for debugging) ----

    @Override
    public String toString() {
        return "Student{" +
                "id=" + id +
                ", name='" + name + '\'' +
                ", rollNumber='" + rollNumber + '\'' +
                ", branch='" + branch + '\'' +
                ", semester=" + semester +
                ", email='" + email + '\'' +
                ", phone='" + phone + '\'' +
                ", attendance=" + attendance +
                ", cgpa=" + cgpa +
                '}';
    }
}
