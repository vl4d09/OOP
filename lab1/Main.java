
import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;

public class Main {
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
                FacultyOperations.performFacultyOperations(faculties, scanner);
            } else if (choice.equals("2")) {
                GeneralOperations.performGeneralOperations(faculties, scanner);
            } else if (choice.equals("q")) {
                break;
            } else {
                System.out.println("Invalid choice. Please try again.");
            }
        }
    }
}
