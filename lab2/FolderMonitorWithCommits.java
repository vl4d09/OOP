import java.io.*;
import java.nio.file.*;
import java.util.*;
import java.util.concurrent.TimeUnit;

public class FolderMonitorWithCommits {
    
    private static Map<WatchKey, Path> keyMap = new HashMap<>();
    private static Map<String, Map<String, Long>> commitStates = new HashMap<>();
    private static Map<WatchEvent<?>, Long> eventTimeMap = new HashMap<>();
    private static long debounceDelayMillis = 1000;
    private static String commitFileName = "commit_info.txt"; // Name of the text file

    public static void main(String[] args) {
        // Define the folder you want to monitor
        String folderPath = "C:\\Users\\VLAD'S LAPTOP\\Desktop\\OOP\\lab2";

        loadLastCommitInfo();

        try {
            WatchService watchService = FileSystems.getDefault().newWatchService();
            Path path = Paths.get(folderPath);
            WatchKey key = path.register(watchService, StandardWatchEventKinds.ENTRY_CREATE, StandardWatchEventKinds.ENTRY_MODIFY, StandardWatchEventKinds.ENTRY_DELETE);
            keyMap.put(key, path);

            System.out.println("Monitoring changes in " + folderPath);

            while (true) {
                WatchKey watchKey;
                try {
                    watchKey = watchService.poll(5, TimeUnit.SECONDS);
                } catch (InterruptedException e) {
                    System.out.println("Monitoring interrupted.");
                    saveCommitInfo(); // Save commit information when the program is stopped
                    return;
                }

                if (watchKey != null) {
                    Path dir = keyMap.get(watchKey);
                    if (dir == null) {
                        System.err.println("WatchKey not recognized!");
                        continue;
                    }

                    for (WatchEvent<?> event : watchKey.pollEvents()) {
                        long currentTime = System.currentTimeMillis();
                        if (isNotDuplicate(event, currentTime)) {
                            WatchEvent.Kind<?> kind = event.kind();
                            Path modifiedFile = (Path) event.context();
                            String filePath = dir.resolve(modifiedFile).toString();

                            System.out.println("File " + modifiedFile + " has been " + kind.name().toLowerCase() + "d.");

                            if (kind == StandardWatchEventKinds.ENTRY_MODIFY || kind == StandardWatchEventKinds.ENTRY_CREATE) {
                                updateCommitState(filePath, currentTime);
                            }

                            eventTimeMap.put(event, currentTime);
                        }
                    }

                    boolean valid = watchKey.reset();
                    if (!valid) {
                        keyMap.remove(watchKey);
                    }
                }
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    private static boolean isNotDuplicate(WatchEvent<?> event, long currentTime) {
        if (eventTimeMap.containsKey(event)) {
            long previousTime = eventTimeMap.get(event);
            return (currentTime - previousTime) > debounceDelayMillis;
        }
        return true;
    }

    private static void updateCommitState(String filePath, long commitTime) {
        commitStates.put(filePath, new HashMap<>());
        commitStates.get(filePath).put("commitTime", commitTime);
    }

    private static void saveCommitInfo() {
        try (BufferedWriter writer = new BufferedWriter(new FileWriter(commitFileName))) {
            for (Map.Entry<String, Map<String, Long>> entry : commitStates.entrySet()) {
                String filePath = entry.getKey();
                long commitTime = entry.getValue().get("commitTime");
                writer.write(filePath + ":" + commitTime);
                writer.newLine();
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    private static void loadLastCommitInfo() {
        try (BufferedReader reader = new BufferedReader(new FileReader(commitFileName))) {
            String line;
            while ((line = reader.readLine()) != null) {
                String[] parts = line.split(":");
                if (parts.length == 2) {
                    String filePath = parts[0];
                    long commitTime = Long.parseLong(parts[1]);
                    updateCommitState(filePath, commitTime);
                }
            }
        } catch (IOException e) {
            // Ignore if the file does not exist or cannot be read
        }
    }
}
