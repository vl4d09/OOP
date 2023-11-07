import javax.imageio.ImageIO;
import java.io.File;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.attribute.BasicFileAttributes;
import java.util.List;
import java.util.regex.Pattern;

public class Service {

    private final String folderPath;

    public Service(String folderPath) {
        this.folderPath = folderPath;
    }

    public void info(String fileName) {
        File file = new File(folderPath + File.separator + fileName);
        if (file.exists()) {
            System.out.println("File Name: " + file.getName());
            try {
                BasicFileAttributes attrs = Files.readAttributes(file.toPath(), BasicFileAttributes.class);
                System.out.print("Created Date: " + DateHelp.formatDate(attrs.creationTime().toMillis()));
            } catch (IOException e) {
                e.printStackTrace();
                System.out.println("Error fetching created date.");
            }

            System.out.println("Updated Date: " + DateHelp.formatDate(file.lastModified()));

            String extension = getFileExtension(file);
            System.out.println("File Extension: " + extension);

            switch (extension) {
                case "png":
                case "jpg":
                    String dimensions = getImageDimensions(file);
                    System.out.println("Image Size: " + dimensions);
                    break;
                case "txt":
                    try {
                        List<String> lines = Files.readAllLines(file.toPath());
                        System.out.println("Line Count: " + lines.size());
                        System.out.println(
                                "Word Count: " + lines.stream().mapToInt(line -> line.split("\\s+").length).sum());
                        System.out.println("Character Count: " + lines.stream().mapToInt(String::length).sum());
                    } catch (IOException e) {
                        e.printStackTrace();
                    }
                    break;
                case "py":
                case "java":
                    System.out.println("Line Count: " + getLineCount(file));
                    System.out.println("Class Count: " + getClassCount(file));
                    System.out.println("Method Count: " + getMethodCount(file));
                    break;
                default:
                    // Handle the default case
                    break;
            }

        } else {
            System.out.println(fileName + " does not exist.");
        }
    }

    private String getFileExtension(File file) {
        String fileName = file.getName();
        if (fileName.lastIndexOf(".") != -1 && fileName.lastIndexOf(".") != 0) {
            return fileName.substring(fileName.lastIndexOf(".") + 1);
        } else {
            return "";
        }
    }

    private String getImageDimensions(File file) {
        try {
            java.awt.image.BufferedImage image = ImageIO.read(file);
            return image.getWidth() + "x" + image.getHeight();
        } catch (IOException e) {
            e.printStackTrace();
            return "Unknown";
        }
    }

    private int getLineCount(File file) {
        try {
            return (int) Files.lines(file.toPath()).count();
        } catch (IOException e) {
            e.printStackTrace();
            return 0;
        }
    }

    private int getClassCount(File file) {
        try {
            java.util.List<String> lines = Files.readAllLines(file.toPath());
            String content = String.join(" ", lines);

            Pattern classPattern = Pattern.compile("\\bclass\\b");
            java.util.regex.Matcher classMatcher = classPattern.matcher(content);

            int count = 0;
            while (classMatcher.find()) {
                count++;
            }
            return count;

        } catch (IOException e) {
            e.printStackTrace();
            return 0;
        }
    }

    private int getMethodCount(File file) {
        try {
            java.util.List<String> lines = Files.readAllLines(file.toPath());
            String content = String.join(" ", lines);

            Pattern methodPattern = Pattern.compile(
                    "(public|protected|private|static|\\s) +[\\w\\<\\>\\[\\]]+\\s+(\\w+) *\\([^\\)]*\\) *\\{? *\\n?");
            java.util.regex.Matcher methodMatcher = methodPattern.matcher(content);
            int count = 0;
            while (methodMatcher.find()) {
                count++;
            }
            return count;

        } catch (IOException e) {
            e.printStackTrace();
            return 0;
        }
    }
}
