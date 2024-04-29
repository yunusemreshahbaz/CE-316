import java.io.Serializable;

public class Student implements Serializable{

    // Attributes 
    private String studentID;
    private String studentName;
    private float studentGrade;

    // Set-get methods
    public String getStudentID() {
        return studentID;
    }
    public void setStudentID(String studentID) {
        this.studentID = studentID;
    }

    public String getStudentName() {
        return studentName;
    }
    public void setStudentName(String studentName) {
        this.studentName = studentName;
    }

    public float getStudentGrade() {
        return studentGrade;
    }
    public void setStudentGrade(float studentGrade) {
        this.studentGrade = studentGrade;
    }

}