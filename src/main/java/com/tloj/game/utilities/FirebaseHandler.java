package com.tloj.game.utilities;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;

import com.google.auth.oauth2.GoogleCredentials;
import com.google.cloud.storage.Blob;
import com.google.cloud.storage.Bucket;
import com.google.firebase.FirebaseApp;
import com.google.firebase.FirebaseOptions;
import com.google.firebase.cloud.StorageClient;
import com.tloj.game.game.Controller;
import com.tloj.game.game.Game;
import com.google.api.gax.paging.Page;


/**
 * Utility class to handle Firebase operations, such as:<br>
 *  - Save game to cloud<br>
 *  - Load game from cloud<br>
 *  - Delete game from cloud<br>
 */
public class FirebaseHandler {
    private static FirebaseHandler instance;
    private boolean initialized = false;

    private FirebaseHandler() { 
        try {
            FileInputStream serviceAccount = new FileInputStream(Constants.FIREBASE_SERVICE_ACCOUNT_FILE);

            FirebaseOptions options = FirebaseOptions.builder()
                    .setCredentials(GoogleCredentials.fromStream(serviceAccount))
                    .setStorageBucket("the-legend-of-jordan.appspot.com")
                    .build();
    
            FirebaseApp.initializeApp(options);
            this.initialized = true;
        } catch (IOException e) {
            System.out.println(ConsoleHandler.RED + "Couldn't find a Firebase service account file, cloud functionalities are disabled" + ConsoleHandler.RESET);
            Controller.awaitEnter();
        }
    }

    public static FirebaseHandler getInstance() {
        if (instance == null) instance = new FirebaseHandler();
        return instance;
    }

    public boolean isInitialized() {
        return this.initialized;
    }

    /**
     * Uploads a JSON file with the specified filename to Firebase Storage.
     */
    public boolean saveToCloud(String filename) {
        if (!this.initialized) return false;

        if (!NetworkUtils.isInternetAvailable()) {
            System.out.println(ConsoleHandler.RED + "No internet connection. Save to cloud failed." + ConsoleHandler.RESET);
            return false;
        }

        Bucket bucket = StorageClient.getInstance().bucket();

        try {
            File file = new File(Constants.BASE_SAVES_DIRECTORY + filename);
            FileInputStream fis = new FileInputStream(file);

            byte[] data = new byte[(int) file.length()];

            fis.read(data);
            fis.close();

            bucket.create(filename.replace(Constants.BASE_SAVES_DIRECTORY, ""), data, "application/json");
            return true;
        } catch (IOException e) {
            System.out.println("Error opening file " + filename + " for reading");
            e.printStackTrace();
            return false;
        }

    }

    public boolean loadAllCloud() {
        if (!this.initialized) return false;
        
        if (!NetworkUtils.isInternetAvailable()) {
            System.out.println(ConsoleHandler.RED + "No internet connection. Couldn't load games from cloud." + ConsoleHandler.RESET);
            return false;
        }

        System.out.println(ConsoleHandler.YELLOW + "Loading games from cloud..." + ConsoleHandler.RESET);

        if (!new File(Constants.BASE_SAVES_DIRECTORY).exists()) new File(Constants.BASE_SAVES_DIRECTORY).mkdir();
        Bucket bucket = StorageClient.getInstance().bucket();
        Page<Blob> blobs = bucket.list();
        
        blobs.iterateAll().forEach(blob -> {
            byte[] data = blob.getContent(Blob.BlobSourceOption.generationMatch());
            File downloadFile = new File(Constants.BASE_SAVES_DIRECTORY + blob.getName());

            if (downloadFile.exists() && !downloadFile.getName().contains(Constants.GAMES_INDEX_FILE_PATH)) {
                try {
                    Game game = JsonParser.loadFromFile(Constants.BASE_SAVES_DIRECTORY + blob.getName());
                    if (!game.isBackedUp()) return;
                } catch (IOException e) {
                    e.printStackTrace();    
                }
            }

            // Write data to file
            try (FileOutputStream outputStream = new FileOutputStream(downloadFile)) {
                outputStream.write(data);
            } catch (IOException e) {
                System.out.println("Error writing file: " + e.getMessage());
            }
        });

        return true;
    }

    public boolean deleteFromCloud(String filename) {
        if (!this.initialized) return false;
        
        if (!NetworkUtils.isInternetAvailable()) {
            System.out.println(ConsoleHandler.RED + "No internet connection. Delete from cloud failed." + ConsoleHandler.RESET);
            return false;
        }
        
        if (filename == null) return false;
        Bucket bucket = StorageClient.getInstance().bucket();
        Blob blob = bucket.get(filename.replace(Constants.BASE_SAVES_DIRECTORY, ""));
        if (blob != null) blob.delete();

        return true;
    }
}


