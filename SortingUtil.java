package util;

import model.AttendanceRecord;
import model.Student;

import java.util.Collections;
import java.util.Comparator;
import java.util.List;

public class SortingUtil {

    // Sort students by name A-Z
    public static void sortByName(List<AttendanceRecord> records) {
        Collections.sort(records, Comparator.comparing(AttendanceRecord::getStudentName));
    }

    // Sort attendance by date latest first
    public static void sortByDate(List<AttendanceRecord> records) {
        records.sort((a, b) -> b.getDate().compareTo(a.getDate()));
    }

    // Sort students by roll number
    public static void sortByRollNo(List<Student> students) {
        Collections.sort(students, Comparator.comparing(Student::getRollNo));
    }

    // Bubble sort students by name (manual sorting algorithm)
    public static void bubbleSortByName(List<Student> list) {
        int n = list.size();
        for (int i = 0; i < n - 1; i++) {
            for (int j = 0; j < n - i - 1; j++) {
                if (list.get(j).getName().compareTo(list.get(j + 1).getName()) > 0) {
                    Student temp = list.get(j);
                    list.set(j, list.get(j + 1));
                    list.set(j + 1, temp);
                }
            }
        }
        System.out.println("Bubble sort by name completed.");
    }

    // Sort by status (Present first, then Late, then Absent)
    public static void sortByStatus(List<AttendanceRecord> records) {
        records.sort((a, b) -> {
            int rankA = getStatusRank(a.getStatus());
            int rankB = getStatusRank(b.getStatus());
            return Integer.compare(rankA, rankB);
        });
    }

    private static int getStatusRank(String status) {
        switch (status) {
            case "Present": return 1;
            case "Late":    return 2;
            case "Absent":  return 3;
            default:        return 4;
        }
    }
}