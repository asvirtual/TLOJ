package com.tloj.game.utilities;

import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;

import java.util.Iterator;
import java.util.List;

import com.google.auth.oauth2.GoogleCredentials;
import com.google.cloud.storage.Blob;
import com.google.cloud.storage.BlobId;
import com.google.cloud.storage.BlobInfo;
import com.google.cloud.storage.Bucket;
import com.google.cloud.storage.Bucket.BlobTargetOption;
import com.google.firebase.FirebaseApp;
import com.google.firebase.FirebaseOptions;
import com.google.firebase.cloud.StorageClient;

import com.google.api.gax.paging.Page;


public class FirebaseHandler {
    private static FirebaseHandler instance;
    private FirebaseApp app;

    private FirebaseHandler() {
        try {
            FileInputStream serviceAccount = new FileInputStream(Constants.FIREBASE_SERVICE_ACCOUNT_FILE);

            FirebaseOptions options = FirebaseOptions.builder()
                    .setCredentials(GoogleCredentials.fromStream(serviceAccount))
                    .setStorageBucket("the-legend-of-jordan.appspot.com")
                    .build();
    
            FirebaseApp.initializeApp(options);          
            this.app = FirebaseApp.getInstance();
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
    public void saveToCloud(String filename) {
        Bucket bucket = StorageClient.getInstance().bucket();

        try {
            File file = new File(Constants.BASE_SAVES_DIRECTORY + filename);
            FileInputStream fis = new FileInputStream(file);

            byte[] data = new byte[(int) file.length()];

            fis.read(data);
            fis.close();

            bucket.create(filename.replace(Constants.BASE_SAVES_DIRECTORY, ""), data, "application/json");
        } catch (IOException e) {
            System.out.println("Error opening file " + filename + " for reading");
            e.printStackTrace();
        }
    }

    public void loadAllCloud() {
        Bucket bucket = StorageClient.getInstance().bucket();
        Page<Blob> blobs = bucket.list();
        
        blobs.iterateAll().forEach(blob -> {
            byte[] data = blob.getContent(Blob.BlobSourceOption.generationMatch());
            File downloadFile = new File(Constants.BASE_SAVES_DIRECTORY + blob.getName());

            // TODO: check if files locally are up to date --> if not, download them

            // Write data to file
            try (FileOutputStream outputStream = new FileOutputStream(downloadFile)) {
                outputStream.write(data);
            } catch (IOException e) {
                System.out.println("Error writing file: " + e.getMessage());
            }
        });
    }

    public void deleteFromCloud(String filename) {
        Bucket bucket = StorageClient.getInstance().bucket();
        Blob blob = bucket.get(filename.replace(Constants.BASE_SAVES_DIRECTORY, ""));
        blob.delete();
    }
}


