import java.io.File;

abstract public class Detector {

    protected final String folderPath;

    public Detector(String folderPath) {
        this.folderPath = folderPath;
    }

    protected File[] getListOfFiles() {
        File folder = new File(folderPath); // Use explicit data type
        if (!folder.exists() || !folder.isDirectory()) {
            System.out.println("It's not a valid directory: " + folderPath);
            return null;
        }

        File[] listOfFiles = folder.listFiles(); // Use explicit data type

        if (listOfFiles == null) {
            System.out.println("Unable to list files from the directory: " + folderPath);
            return null;
        }

        return listOfFiles;
    }

    public abstract void scanDirectory();
}
