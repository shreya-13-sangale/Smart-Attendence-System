package service;

import dao.AttendanceDAO;
import dao.StudentDAO;
import model.AttendanceRecord;
import model.Student;
import util.SortingUtil;

import java.time.LocalDate;
import java.util.List;
import java.util.Map;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.TimeUnit;

public class AttendanceService {

    private static final Object lock = new Object();
    private AttendanceDAO attendanceDAO = new AttendanceDAO();
    private StudentDAO studentDAO = new StudentDAO();
    private FileService fileService = new FileService();

    // SYNCHRONIZATION — safe single attendance mark
    public boolean markAttendanceSafe(int studentId, String status, String date, String markedBy) {
        synchronized (lock) {
            return attendanceDAO.markAttendance(studentId, status, date, markedBy);
        }
    }

    // MULTITHREADING — mark all students at once using thread pool
    public void markAllAttendance(Map<Integer, String> studentStatusMap, String date, String markedBy) {
        ExecutorService pool = Executors.newFixedThreadPool(5);
        for (Map.Entry<Integer, String> entry : studentStatusMap.entrySet()) {
            pool.submit(new AttendanceThread(entry.getKey(), entry.getValue(), date, markedBy));
        }
        pool.shutdown();
        try {
            pool.awaitTermination(30, TimeUnit.SECONDS);
            System.out.println("All attendance threads completed.");
        } catch (InterruptedException e) {
            System.out.println("Thread interrupted: " + e.getMessage());
        }
    }

    // Get today's attendance sorted by name
    public List<AttendanceRecord> getTodayAttendanceSortedByName() {
        String today = LocalDate.now().toString();
        List<AttendanceRecord> records = attendanceDAO.getAttendanceByDate(today);
        SortingUtil.sortByName(records);
        return records;
    }

    // Get all attendance sorted by date
    public List<AttendanceRecord> getAllAttendanceSortedByDate() {
        List<AttendanceRecord> records = attendanceDAO.getAllAttendance();
        SortingUtil.sortByDate(records);
        return records;
    }

    // Get all students sorted by roll number
    public List<Student> getAllStudentsSortedByRoll() {
        List<Student> students = studentDAO.getAllStudents();
        SortingUtil.sortByRollNo(students);
        return students;
    }

    // Export + save binary backup
    public void exportAndBackup(String date) {
        List<AttendanceRecord> records = attendanceDAO.getAttendanceByDate(date);
        fileService.exportAttendanceToCSV(records, "data/attendance_" + date + ".csv");
        fileService.saveAttendanceBinary(records, "data/attendance_cache.dat");
        List<Student> students = studentDAO.getAllStudents();
        fileService.saveStudentsBinary(students, "data/students_backup.ser");
        System.out.println("Export and backup completed!");
    }

    public boolean validateLogin(String username, String password) {
        return studentDAO.validateAdmin(username, password);
    }

    public List<Student> getAllStudents() {
        return studentDAO.getAllStudents();
    }

    public List<AttendanceRecord> getAttendanceByDate(String date) {
        return attendanceDAO.getAttendanceByDate(date);
    }

    public int getTotalPresent(String date) {
        return attendanceDAO.getTotalPresent(date);
    }
}