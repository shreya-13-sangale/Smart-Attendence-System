package service;

import dao.AttendanceDAO;

public class AttendanceThread implements Runnable {
    private int studentId;
    private String status;
    private String date;
    private String markedBy;

    public AttendanceThread(int studentId, String status, String date, String markedBy) {
        this.studentId = studentId;
        this.status = status;
        this.date = date;
        this.markedBy = markedBy;
    }

    @Override
    public void run() {
        AttendanceDAO dao = new AttendanceDAO();
        boolean success = dao.markAttendance(studentId, status, date, markedBy);
        System.out.println("Thread " + Thread.currentThread().getName() +
                           " marked student " + studentId +
                           " as " + status + " → " + (success ? "SUCCESS" : "FAILED"));
    }
}