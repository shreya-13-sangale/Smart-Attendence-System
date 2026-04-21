CREATE DATABASE IF NOT EXISTS attendance_db;
USE attendance_db;

CREATE TABLE IF NOT EXISTS students (
    id INT PRIMARY KEY AUTO_INCREMENT,
    roll_no VARCHAR(20) UNIQUE NOT NULL,
    name VARCHAR(100) NOT NULL,
    email VARCHAR(100),
    class_name VARCHAR(50),
    created_at TIMESTAMP DEFAULT CURRENT_TIMESTAMP
);

CREATE TABLE IF NOT EXISTS attendance (
    id INT PRIMARY KEY AUTO_INCREMENT,
    student_id INT NOT NULL,
    date DATE NOT NULL,
    status ENUM('Present','Absent','Late') NOT NULL,
    marked_by VARCHAR(50),
    FOREIGN KEY (student_id) REFERENCES students(id),
    INDEX idx_date (date),
    INDEX idx_student (student_id)
);

CREATE TABLE IF NOT EXISTS admin_users (
    id INT PRIMARY KEY AUTO_INCREMENT,
    username VARCHAR(50) UNIQUE NOT NULL,
    password_hash VARCHAR(255) NOT NULL,
    role VARCHAR(20) DEFAULT 'teacher'
);

INSERT INTO admin_users (username, password_hash, role)
VALUES ('admin', 'admin123', 'admin');

INSERT INTO students (roll_no, name, email, class_name) VALUES
('R001', 'Aarav Sharma', 'aarav@email.com', 'CS-A'),
('R002', 'Priya Patel', 'priya@email.com', 'CS-A'),
('R003', 'Rohan Mehta', 'rohan@email.com', 'CS-A'),
('R004', 'Sneha Reddy', 'sneha@email.com', 'CS-A'),
('R005', 'Karan Singh', 'karan@email.com', 'CS-A');