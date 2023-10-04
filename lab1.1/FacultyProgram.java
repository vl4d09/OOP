import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Scanner;

public class FacultyProgram {
    public static void main(String[] args) {
        Scanner scanner = new Scanner(System.in);
        List<Faculty> faculties = new ArrayList<>();

        while (true) {
            System.out.println("Choose an option:");
            System.out.println("1. Faculty Operations");
            System.out.println("2. General Operations");
            System.out.println("Enter 'q' to quit.");

            String choice = scanner.nextLine();

            if (choice.equals("1")) {
                // Faculty Operations
                facultyOperations(faculties, scanner);
            } else if (choice.equals("2")) {
                // General Operations
                generalOperations(faculties, scanner);
            } else if (choice.equals("q")) {
                break;
            } else {
                System.out.println("Invalid choice. Please try again.");
            }
        }
    }

    private static void facultyOperations(List<Faculty> faculties, Scanner scanner) {
        while (true) {
            System.out.println("Faculty Operations:");
            System.out.println("1. Create and assign a student to a faculty.");
            System.out.println("2. Graduate a student from a faculty.");
            System.out.println("3. Display current enrolled students.");
            System.out.println("4. Display graduates.");
            System.out.println("5. Check if a student belongs to this faculty.");
            System.out.println("Enter 'b' to go back to the main menu.");

            String choice = scanner.nextLine();

            if (choice.equals("1")) {
                // Create and assign a student to a faculty
                System.out.println("Enter faculty name:");
                String facultyName = scanner.nextLine();
                Faculty faculty = findFacultyByName(faculties, facultyName);

                if (faculty != null) {
                    // Create a student
                    System.out.println("Enter student first name:");
                    String studentFirstName = scanner.nextLine();
                    System.out.println("Enter student last name:");
                    String studentLastName = scanner.nextLine();
                    System.out.println("Enter student email:");
                    String studentEmail = scanner.nextLine();
                    System.out.println("Enter student enrollment date (yyyy-MM-dd):");
                    String enrollmentDateString = scanner.nextLine();

                    // Parse the enrollment date into a Date object
                    Date enrollmentDate = parseDate(enrollmentDateString);

                    if (enrollmentDate != null) {
                        // Create the student
                        Student student = new Student(studentFirstName, studentLastName, studentEmail, enrollmentDate);

                        // Add the student to the faculty
                        faculty.addStudent(student);

                        System.out.println("Student added to the faculty successfully.");
                    } else {
                        System.out.println("Invalid date format. Please enter the date in yyyy-MM-dd format.");
                    }

                } else {
                    System.out.println("Faculty not found.");
                }
            } else if (choice.equals("2")) {
                System.out.println("Enter faculty name:");
                String facultyName = scanner.nextLine();
                Faculty faculty = findFacultyByName(faculties, facultyName);

                if (faculty != null) {
                    System.out.println("Enter student email:");
                    String studentEmail = scanner.nextLine();
                    Student student = findStudentByEmail(faculty, studentEmail);

                    if (student != null) {
                        // Mark the student as graduated
                        student.setGraduated(true);
                        System.out.println("Student graduated successfully.");
                    } else {
                        System.out.println("Student not found in this faculty.");
                    }

                } else {
                    System.out.println("Faculty not found.");
                }
            } else if (choice.equals("3")) {
                // Display current enrolled students
                System.out.println("Enter faculty name:");
                String facultyName = scanner.nextLine();
                Faculty faculty = findFacultyByName(faculties, facultyName);

                if (faculty != null) {
                    List<Student> enrolledStudents = getEnrolledStudents(faculty);

                    if (enrolledStudents.isEmpty()) {
                        System.out.println("No currently enrolled students in this faculty.");
                    } else {
                        System.out.println("Currently enrolled students in " + faculty.getName() + ":");
                        for (Student student : enrolledStudents) {
                            if (!student.isGraduated()) {
                                System.out.println(student.getFirstName() + " " + student.getLastName());
                            }
                        }
                    }
                } else {
                    System.out.println("Faculty not found.");
                }
            } else if (choice.equals("4")) {
                // Display graduates
                System.out.println("Enter faculty name:");
                String facultyName = scanner.nextLine();
                Faculty faculty = findFacultyByName(faculties, facultyName);

                if (faculty != null) {
                    List<Student> graduatedStudents = getGraduatedStudents(faculty);

                    if (graduatedStudents.isEmpty()) {
                        System.out.println("No graduates in " + faculty.getName() + ".");
                    } else {
                        System.out.println("Graduates in " + faculty.getName() + ":");
                        for (Student student : graduatedStudents) {
                            if (student.isGraduated()) {
                                System.out.println(student.getFirstName() + " " + student.getLastName() + " ("
                                        + student.getEmail() + ")");
                            }
                        }
                    }
                } else {
                    System.out.println("Faculty not found.");
                }
            } else if (choice.equals("5")) {
                // Check if a student belongs to this faculty
                System.out.println("Enter faculty name:");
                String facultyName = scanner.nextLine();
                Faculty faculty = findFacultyByName(faculties, facultyName);

                if (faculty != null) {
                    System.out.println("Enter student email:");
                    String studentEmail = scanner.nextLine();
                    Student student = findStudentByEmail(faculty, studentEmail);

                    if (student != null) {
                        System.out.println(student.getFirstName() + " " + student.getLastName() + " belongs to "
                                + faculty.getName() + " faculty.");
                    } else {
                        System.out.println("Student not found in this faculty.");
                    }
                } else {
                    System.out.println("Faculty not found.");
                }
            } else if (choice.equals("b")) {
                break;
            } else {
                System.out.println("Invalid choice. Please try again.");
            }
        }
    }

    private static Student findStudentByEmail(Faculty faculty, String studentEmail) {
        List<Student> students = faculty.getStudents();
        for (Student student : students) {
            if (student.getEmail().equals(studentEmail)) {
                return student;
            }
        }
        return null;
    }

    private static List<Student> getEnrolledStudents(Faculty faculty) {
        List<Student> enrolledStudents = new ArrayList<>();

        for (Student student : faculty.getStudents()) {
            if (!student.isGraduated()) {
                enrolledStudents.add(student);
            }
        }

        return enrolledStudents;
    }

    private static Faculty findFacultyByName(List<Faculty> faculties, String facultyName) {
        for (Faculty faculty : faculties) {
            if (faculty.getName().equalsIgnoreCase(facultyName)) {
                return faculty;
            }
        }
        return null;
    }

    private static Date parseDate(String dateString) {
        SimpleDateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd");
        try {
            return dateFormat.parse(dateString);
        } catch (ParseException e) {
            e.printStackTrace();
            return null;
        }
    }

    private static List<Student> getGraduatedStudents(Faculty faculty) {
        List<Student> graduatedStudents = new ArrayList<>();

        for (Student student : faculty.getStudents()) {
            if (student.isGraduated()) {
                graduatedStudents.add(student);
            }
        }

        return graduatedStudents;
    }

    private static void generalOperations(List<Faculty> faculties, Scanner scanner) {
        while (true) {
            System.out.println("General Operations:");
            System.out.println("1. Create a new faculty.");
            System.out.println("2. Search what faculty a student belongs to by a unique identifier.");
            System.out.println("3. Display University faculties.");
            System.out.println("4. Display all faculties belonging to a field.");
            System.out.println("Enter 'b' to go back to the main menu.");

            String choice = scanner.nextLine();

            if (choice.equals("1")) {
                // Create a new faculty
                System.out.println("Enter faculty name:");
                String facultyName = scanner.nextLine();
                System.out.println("Enter faculty abbreviation:");
                String facultyAbbreviation = scanner.nextLine();
                System.out.println("Enter faculty study field (Choose one from the list):");
                for (StudyField field : StudyField.values()) {
                    System.out.println(field.name());
                }
                String fieldInput = scanner.nextLine();
                StudyField field = StudyField.valueOf(fieldInput);

                Faculty faculty = new Faculty(facultyName, facultyAbbreviation, field);
                faculties.add(faculty);
                System.out.println("Faculty created successfully.");
            } else if (choice.equals("2")) {
                // Search what faculty a student belongs to by a unique identifier
                System.out.println("Enter student's unique identifier (e.g., email or ID):");
                String identifier = scanner.nextLine();
                Faculty faculty = findFacultyByStudentIdentifier(faculties, identifier);
                if (faculty != null) {
                    System.out.println("The student belongs to the " + faculty.getName() + " faculty.");
                } else {
                    System.out.println("Student not found in any faculty.");
                }
            } else if (choice.equals("3")) {
                // Display University faculties
                for (Faculty faculty : faculties) {
                    System.out.println(faculty.getName() + " (" + faculty.getAbbreviation() + ")");
                }
            } else if (choice.equals("4")) {
                // Display all faculties belonging to a field
                System.out.println("Enter the name of the field to search for:");
                String fieldToSearch = scanner.nextLine();
                displayFacultiesByField(faculties, fieldToSearch);
            } else if (choice.equals("b")) {
                break;
            } else {
                System.out.println("Invalid choice. Please try again.");
            }
        }
    }

    private static Faculty findFacultyByStudentIdentifier(List<Faculty> faculties, String identifier) {
        for (Faculty faculty : faculties) {
            for (Student student : faculty.getStudents()) {
                if (student.getEmail().equals(identifier)) {
                    return faculty;
                }
            }
        }
        return null;
    }

    private static void displayFacultiesByField(List<Faculty> faculties, String fieldToSearch) {
        for (Faculty faculty : faculties) {
            if (faculty.getStudyField().name().equals(fieldToSearch)) {
                System.out.println(faculty.getName() + " (" + faculty.getAbbreviation() + ")");
            }
        }
    }
}
