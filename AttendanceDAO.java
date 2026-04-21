package dao;

import model.AttendanceRecord;
import java.sql.*;
import java.util.ArrayList;
import java.util.List;

public class AttendanceDAO {

    public boolean markAttendance(int studentId, String status, String date, String markedBy) {
        String checkSql = "SELECT id FROM attendance WHERE student_id = ? AND date = ?";
        String insertSql = "INSERT INTO attendance (student_id, date, status, marked_by) VALUES (?, ?, ?, ?)";
        String updateSql = "UPDATE attendance SET status = ? WHERE student_id = ? AND date = ?";

        try (Connection conn = DBConnection.getConnection()) {
            PreparedStatement checkPs = conn.prepareStatement(checkSql);
            checkPs.setInt(1, studentId);
            checkPs.setString(2, date);
            ResultSet rs = checkPs.executeQuery();

            if (rs.next()) {
                PreparedStatement updatePs = conn.prepareStatement(updateSql);
                updatePs.setString(1, status);
                updatePs.setInt(2, studentId);
                updatePs.setString(3, date);
                return updatePs.executeUpdate() > 0;
            } else {
                PreparedStatement insertPs = conn.prepareStatement(insertSql);
                insertPs.setInt(1, studentId);
                insertPs.setString(2, date);
                insertPs.setString(3, status);
                insertPs.setString(4, markedBy);
                return insertPs.executeUpdate() > 0;
            }
        } catch (SQLException e) {
            e.printStackTrace();
            return false;
        }
    }

    public List<AttendanceRecord> getAttendanceByDate(String date) {
        List<AttendanceRecord> records = new ArrayList<>();
        String sql = "SELECT a.id, a.student_id, s.name, s.roll_no, a.date, a.status, a.marked_by " +
                     "FROM attendance a JOIN students s ON a.student_id = s.id " +
                     "WHERE a.date = ? ORDER BY s.roll_no";
        try (Connection conn = DBConnection.getConnection();
             PreparedStatement ps = conn.prepareStatement(sql)) {
            ps.setString(1, date);
            ResultSet rs = ps.executeQuery();
            while (rs.next()) {
                records.add(new AttendanceRecord(
                    rs.getInt("id"),
                    rs.getInt("student_id"),
                    rs.getString("name"),
                    rs.getString("roll_no"),
                    rs.getString("date"),
                    rs.getString("status"),
                    rs.getString("marked_by")
                ));
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return records;
    }

    public List<AttendanceRecord> getAllAttendance() {
        List<AttendanceRecord> records = new ArrayList<>();
        String sql = "SELECT a.id, a.student_id, s.name, s.roll_no, a.date, a.status, a.marked_by " +
                     "FROM attendance a JOIN students s ON a.student_id = s.id " +
                     "ORDER BY a.date DESC";
        try (Connection conn = DBConnection.getConnection();
             Statement stmt = conn.createStatement();
             ResultSet rs = stmt.executeQuery(sql)) {
            while (rs.next()) {
                records.add(new AttendanceRecord(
                    rs.getInt("id"),
                    rs.getInt("student_id"),
                    rs.getString("name"),
                    rs.getString("roll_no"),
                    rs.getString("date"),
                    rs.getString("status"),
                    rs.getString("marked_by")
                ));
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return records;
    }

    public int getTotalPresent(String date) {
        String sql = "SELECT COUNT(*) FROM attendance WHERE date = ? AND status = 'Present'";
        try (Connection conn = DBConnection.getConnection();
             PreparedStatement ps = conn.prepareStatement(sql)) {
            ps.setString(1, date);
            ResultSet rs = ps.executeQuery();
            if (rs.next()) return rs.getInt(1);
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return 0;
    }
}