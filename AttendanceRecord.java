package model;

import java.io.Serializable;

public class AttendanceRecord implements Serializable {
    private static final long serialVersionUID = 2L;

    private int id;
    private int studentId;
    private String studentName;
    private String rollNo;
    private String date;
    private String status;
    private String markedBy;

    public AttendanceRecord() {}

    public AttendanceRecord(int id, int studentId, String studentName,
                             String rollNo, String date, String status, String markedBy) {
        this.id = id;
        this.studentId = studentId;
        this.studentName = studentName;
        this.rollNo = rollNo;
        this.date = date;
        this.status = status;
        this.markedBy = markedBy;
    }

    public int getId() { return id; }
    public void setId(int id) { this.id = id; }

    public int getStudentId() { return studentId; }
    public void setStudentId(int studentId) { this.studentId = studentId; }

    public String getStudentName() { return studentName; }
    public void setStudentName(String studentName) { this.studentName = studentName; }

    public String getRollNo() { return rollNo; }
    public void setRollNo(String rollNo) { this.rollNo = rollNo; }

    public String getDate() { return date; }
    public void setDate(String date) { this.date = date; }

    public String getStatus() { return status; }
    public void setStatus(String status) { this.status = status; }

    public String getMarkedBy() { return markedBy; }
    public void setMarkedBy(String markedBy) { this.markedBy = markedBy; }

    @Override
    public String toString() {
        return rollNo + " | " + studentName + " | " + date + " | " + status;
    }
}