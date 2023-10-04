import java.util.List;
import java.util.Scanner;

public class GeneralOperations {
    public static void performGeneralOperations(List<Faculty> faculties, Scanner scanner) {
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
