package service;

import model.AttendanceRecord;
import model.Student;

import java.io.*;
import java.util.List;

public class FileService {

    // FILE I/O — Export attendance to CSV text file
    public void exportAttendanceToCSV(List<AttendanceRecord> records, String filename) {
        try (BufferedWriter writer = new BufferedWriter(new FileWriter(filename))) {
            writer.write("Roll No,Name,Date,Status,Marked By");
            writer.newLine();
            for (AttendanceRecord r : records) {
                writer.write(r.getRollNo() + "," +
                             r.getStudentName() + "," +
                             r.getDate() + "," +
                             r.getStatus() + "," +
                             r.getMarkedBy());
                writer.newLine();
            }
            System.out.println("Attendance exported to: " + filename);
        } catch (IOException e) {
            System.out.println("Error exporting CSV: " + e.getMessage());
        }
    }

    // FILE I/O — Read a config or log file
    public void readAndPrintFile(String filename) {
        try (BufferedReader reader = new BufferedReader(new FileReader(filename))) {
            String line;
            while ((line = reader.readLine()) != null) {
                System.out.println(line);
            }
        } catch (IOException e) {
            System.out.println("File not found: " + filename);
        }
    }

    // BINARY FILE + SERIALIZATION — Save students to binary file
    public void saveStudentsBinary(List<Student> students, String filename) {
        try (ObjectOutputStream oos = new ObjectOutputStream(
                new FileOutputStream(filename))) {
            oos.writeObject(students);
            System.out.println("Students saved to binary file: " + filename);
        } catch (IOException e) {
            System.out.println("Error saving binary: " + e.getMessage());
        }
    }

    // BINARY FILE + SERIALIZATION — Load students from binary file
    @SuppressWarnings("unchecked")
    public List<Student> loadStudentsBinary(String filename) {
        try (ObjectInputStream ois = new ObjectInputStream(
                new FileInputStream(filename))) {
            List<Student> students = (List<Student>) ois.readObject();
            System.out.println("Students loaded from binary file: " + filename);
            return students;
        } catch (IOException | ClassNotFoundException e) {
            System.out.println("Error loading binary: " + e.getMessage());
            return null;
        }
    }

    // BINARY FILE + SERIALIZATION — Save attendance records to binary file
    public void saveAttendanceBinary(List<AttendanceRecord> records, String filename) {
        try (ObjectOutputStream oos = new ObjectOutputStream(
                new FileOutputStream(filename))) {
            oos.writeObject(records);
            System.out.println("Attendance saved to binary file: " + filename);
        } catch (IOException e) {
            System.out.println("Error saving attendance binary: " + e.getMessage());
        }
    }

    // BINARY FILE + SERIALIZATION — Load attendance from binary file
    @SuppressWarnings("unchecked")
    public List<AttendanceRecord> loadAttendanceBinary(String filename) {
        try (ObjectInputStream ois = new ObjectInputStream(
                new FileInputStream(filename))) {
            return (List<AttendanceRecord>) ois.readObject();
        } catch (IOException | ClassNotFoundException e) {
            System.out.println("Error loading attendance binary: " + e.getMessage());
            return null;
        }
    }
}