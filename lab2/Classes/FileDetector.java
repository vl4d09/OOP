import java.io.*;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class FileDetector extends Detector {

    private List<String> changeMessages = new ArrayList<>();
    private long snapshotTime = System.currentTimeMillis();
    private final Map<String, Long> fileLastModifiedMap = new HashMap<>();
    private static final String COMMIT_FILE = "commit_info.txt";

    public FileDetector(String folderPath) {
        super(folderPath);
        loadCommitInfo();
    }

    @Override
    public void scanDirectory() {
        File[] listOfFiles = getListOfFiles();
        if (listOfFiles == null)
            return;

        changeMessages = new ArrayList<>();

        for (File file : listOfFiles) {
            if (file.isFile()) {
                long lastModified = file.lastModified();
                if (fileLastModifiedMap.containsKey(file.getName())) {
                    if (fileLastModifiedMap.get(file.getName()) != lastModified) {
                        changeMessages.add(file.getName() + " - Changed");
                    } else {
                        changeMessages.add(file.getName() + " - No Change");
                    }
                } else {
                    changeMessages.add(file.getName() + " - Added");
                }
            }
        }

        for (String fileName : new HashMap<>(fileLastModifiedMap).keySet()) {
            File file = new File(folderPath + File.separator + fileName);
            if (!file.exists()) {
                changeMessages.add(fileName + " - Deleted");
            }
        }
    }

    public void commit() {
        File[] listOfFiles = getListOfFiles();
        if (listOfFiles == null)
            return;
    
        List<String> filesToRemove = new ArrayList<>(fileLastModifiedMap.keySet());
    
        for (File file : listOfFiles) {
            if (file.isFile()) {
                long lastModified = file.lastModified();
                fileLastModifiedMap.put(file.getName(), lastModified);
    
                // Remove file
                filesToRemove.remove(file.getName());
            }
    
            snapshotTime = System.currentTimeMillis();
        }
    
        // Remove eleted files
        for (String fileName : filesToRemove) {
            fileLastModifiedMap.remove(fileName);
        }
    
        System.out.println("Created Snapshot at: " + DateHelp.formatDate(snapshotTime));
    
        // Write commit information to a file
        try (BufferedWriter writer = new BufferedWriter(new FileWriter(COMMIT_FILE))) {
            writer.write(Long.toString(snapshotTime));
    
            for (Map.Entry<String, Long> entry : fileLastModifiedMap.entrySet()) {
                writer.newLine();
                writer.write(entry.getKey() + " " + entry.getValue());
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
    
    public void info(String fileName) {
        Service fileService = new Service(folderPath);
        fileService.info(fileName);
    }

 public void status() {
        if (fileLastModifiedMap.isEmpty()) {
            System.out.println("There're no any snapshots. You have to execute command commit.");
            return;
        }

        scanDirectory();

        System.out.println("Created Snapshot at: " + DateHelp.formatDate(snapshotTime));
        for (String message : changeMessages) {
            System.out.println(message);
        }
    }

    private void loadCommitInfo() {
        // Read commit 
        try (BufferedReader reader = new BufferedReader(new FileReader(COMMIT_FILE))) {
            snapshotTime = Long.parseLong(reader.readLine());
            fileLastModifiedMap.clear();

            String line;
            while ((line = reader.readLine()) != null) {
                String[] parts = line.split(" ");
                if (parts.length == 2) {
                    fileLastModifiedMap.put(parts[0], Long.parseLong(parts[1]));
                }
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}
 
