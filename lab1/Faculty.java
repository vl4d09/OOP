import java.util.ArrayList;
import java.util.List;

public class Faculty {
    private String name;
    private String abbreviation;
    private List<Student> students;
    private StudyField studyField;

    public Faculty(String name, String abbreviation, StudyField studyField) {
        this.name = name;
        this.abbreviation = abbreviation;
        this.studyField = studyField;
        this.students = new ArrayList<>();
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getAbbreviation() {
        return abbreviation;
    }

    public void setAbbreviation(String abbreviation) {
        this.abbreviation = abbreviation;
    }

    public List<Student> getStudents() {
        return students;
    }

    public void addStudent(Student student) {
        students.add(student);
    }

    public void graduateStudent(Student student) {
        student.setGraduated(true);
    }

    public void displayEnrolledStudents() {
        for (Student student : students) {
            if (!student.isGraduated()) {
                System.out.println(student.getFirstName() + " " + student.getLastName());
            }
        }
    }

    public void displayGraduates() {
        for (Student student : students) {
            if (student.isGraduated()) {
                System.out.println(student.getFirstName() + " " + student.getLastName());
            }
        }
    }

    public boolean hasStudent(Student student) {
        return students.contains(student);
    }

    public StudyField getStudyField() {
        return studyField;
    }

    public void setStudyField(StudyField studyField) {
        this.studyField = studyField;
    }
}
