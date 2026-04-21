
import java.io.Serializable;

public class Student implements Serializable {
    private static final long serialVersionUID = 1L;

    private int id;
    private String rollNo;
    private String name;
    private String email;
    private String className;

    public Student() {}

    public Student(int id, String rollNo, String name, String email, String className) {
        this.id = id;
        this.rollNo = rollNo;
        this.name = name;
        this.email = email;
        this.className = className;
    }

    public int getId() { return id; }
    public void setId(int id) { this.id = id; }

    public String getRollNo() { return rollNo; }
    public void setRollNo(String rollNo) { this.rollNo = rollNo; }

    public String getName() { return name; }
    public void setName(String name) { this.name = name; }

    public String getEmail() { return email; }
    public void setEmail(String email) { this.email = email; }

    public String getClassName() { return className; }
    public void setClassName(String className) { this.className = className; }

    @Override
    public String toString() {
        return "Student{id=" + id + ", rollNo=" + rollNo + ", name=" + name + "}";
    }
}