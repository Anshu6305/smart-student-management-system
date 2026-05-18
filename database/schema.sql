-- ============================================================
-- Smart Student Management System — Database Schema
-- ============================================================
-- Run this file in MySQL to set up the database, tables,
-- and sample data needed for the application.
-- ============================================================

-- 1. Create the database
CREATE DATABASE IF NOT EXISTS student_management_system;
USE student_management_system;

-- ============================================================
-- 2. Users table (for login authentication)
-- ============================================================
CREATE TABLE IF NOT EXISTS users (
    id INT AUTO_INCREMENT PRIMARY KEY,
    username VARCHAR(50) NOT NULL UNIQUE,
    password VARCHAR(255) NOT NULL,
    full_name VARCHAR(100) NOT NULL,
    created_at TIMESTAMP DEFAULT CURRENT_TIMESTAMP
);

-- ============================================================
-- 3. Students table (main data table)
-- ============================================================
CREATE TABLE IF NOT EXISTS students (
    id INT AUTO_INCREMENT PRIMARY KEY,
    name VARCHAR(100) NOT NULL,
    roll_number VARCHAR(20) NOT NULL UNIQUE,
    branch VARCHAR(50) NOT NULL,
    semester INT NOT NULL CHECK (semester BETWEEN 1 AND 8),
    email VARCHAR(100) NOT NULL,
    phone VARCHAR(15) NOT NULL,
    attendance DECIMAL(5,2) DEFAULT 0.00,
    cgpa DECIMAL(3,2) DEFAULT 0.00,
    created_at TIMESTAMP DEFAULT CURRENT_TIMESTAMP,
    updated_at TIMESTAMP DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP
);

-- ============================================================
-- 4. Insert default admin user
--    Username: admin | Password: admin123
-- ============================================================
INSERT INTO users (username, password, full_name) VALUES
('admin', 'admin123', 'Administrator');

-- ============================================================
-- 5. Insert sample student data (15 students)
-- ============================================================
INSERT INTO students (name, roll_number, branch, semester, email, phone, attendance, cgpa) VALUES
('Aarav Sharma',      'CSE2024001', 'Computer Science',    5, 'aarav.sharma@university.edu',     '9876543210', 92.50, 8.75),
('Priya Patel',       'CSE2024002', 'Computer Science',    5, 'priya.patel@university.edu',      '9876543211', 88.00, 9.10),
('Rohan Gupta',       'ECE2024001', 'Electronics',         3, 'rohan.gupta@university.edu',      '9876543212', 78.50, 7.60),
('Sneha Reddy',       'ME2024001',  'Mechanical',          7, 'sneha.reddy@university.edu',      '9876543213', 95.00, 9.40),
('Arjun Singh',       'CSE2024003', 'Computer Science',    5, 'arjun.singh@university.edu',      '9876543214', 82.00, 8.20),
('Kavya Nair',        'EE2024001',  'Electrical',          3, 'kavya.nair@university.edu',       '9876543215', 91.00, 8.90),
('Vikram Joshi',      'CE2024001',  'Civil',               7, 'vikram.joshi@university.edu',     '9876543216', 74.50, 7.10),
('Ananya Iyer',       'CSE2024004', 'Computer Science',    3, 'ananya.iyer@university.edu',      '9876543217', 96.00, 9.55),
('Rahul Verma',       'ECE2024002', 'Electronics',         5, 'rahul.verma@university.edu',      '9876543218', 85.50, 8.30),
('Deepika Mishra',    'ME2024002',  'Mechanical',          3, 'deepika.mishra@university.edu',   '9876543219', 89.00, 8.60),
('Karan Mehta',       'CSE2024005', 'Computer Science',    7, 'karan.mehta@university.edu',      '9876543220', 70.00, 7.00),
('Ishita Banerjee',   'EE2024002',  'Electrical',          5, 'ishita.banerjee@university.edu',  '9876543221', 93.50, 9.20),
('Aditya Kulkarni',   'CE2024002',  'Civil',               3, 'aditya.kulkarni@university.edu',  '9876543222', 80.00, 7.80),
('Meera Choudhury',   'ECE2024003', 'Electronics',         7, 'meera.choudhury@university.edu',  '9876543223', 87.00, 8.50),
('Siddharth Rao',     'CSE2024006', 'Computer Science',    5, 'siddharth.rao@university.edu',    '9876543224', 90.00, 8.95);
