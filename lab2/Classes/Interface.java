import java.util.Scanner;

public class Interface {

    private final String folderPath;

    public Interface(String folderPath) {
        this.folderPath = folderPath;
    }

    public void run() {
        FileDetector detector = new FileDetector(folderPath); // Use explicit data type

        Scanner scanner = new Scanner(System.in);
        while (true) {
            System.out.println("Enter command (commit, info, status, exit):");
            String command = scanner.nextLine();
            switch (command) {
                case "commit":
                    detector.commit();
                    break;
                case "info":
                    System.out.println("Enter file name:");
                    String fileName = scanner.nextLine();
                    detector.info(fileName);
                    break;
                case "status":
                    detector.status();
                    break;
                case "exit":
                    scanner.close();
                    System.exit(0);
                    break;
                default:
                    System.out.println("Invalid command.");
                    break;
            }
        }
    }
}
