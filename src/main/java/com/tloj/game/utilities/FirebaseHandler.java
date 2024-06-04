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

    private FirebaseHandler() { 
        try {
            FileInputStream serviceAccount = new FileInputStream(Constants.FIREBASE_SERVICE_ACCOUNT_FILE);

            FirebaseOptions options = FirebaseOptions.builder()
                    .setCredentials(GoogleCredentials.fromStream(serviceAccount))
                    .setStorageBucket("the-legend-of-jordan.appspot.com")
                    .build();
    
            FirebaseApp.initializeApp(options);
        } catch (IOException e) {
            System.out.println("Error opening Firebase service account file");
            e.printStackTrace();
        }
    }

    public static FirebaseHandler getInstance() {
        if (instance == null) instance = new FirebaseHandler();
        return instance;
    }

    /**
     * Uploads a JSON file with the specified filename to Firebase Storage.
     */
    public boolean saveToCloud(String filename) {
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

            if (downloadFile.exists()) {
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


