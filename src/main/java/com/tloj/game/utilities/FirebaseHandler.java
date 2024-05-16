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
    public static void saveToFirebaseBucket(String filename) {
        Bucket bucket = StorageClient.getInstance().bucket();

        try {
            File file = new File(filename);
            FileInputStream fis = new FileInputStream(file);

            byte[] data = new byte[(int) file.length()];

            fis.read(data);
            fis.close();

            bucket.create(filename, data);
    
            System.out.println("JSON data saved to Firebase Storage.");
        } catch (IOException e) {
            System.out.println("Error opening file " + filename + " for reading");
            e.printStackTrace();
        }
    }

    /**
     * Loads all saves from Firebase Storage and saves them to a list of json files.
     */

/*     public byte[] loadFromCloudBucket(String filename) {
        StorageClient storageClient = StorageClient.getInstance();
        Bucket bucket = storageClient.bucket();

        listFilesInFirebaseBucket();

        Blob blob = bucket.get(filename);
        if (blob == null) {
            System.out.println("File not found: " + filename);
            return null;
        }
        
        byte[] data = blob.getContent(Blob.BlobSourceOption.generationMatch());
        return data;
    } */

    public static void loadFilesInFirebaseBucket() {
        Bucket bucket = StorageClient.getInstance().bucket();
        System.out.println("Files in Firebase Storage bucket:");
        Page<Blob> blobs = bucket.list();

        for (Blob blob : blobs.iterateAll()) {

            byte[] data = blob.getContent(Blob.BlobSourceOption.generationMatch());
            System.out.println(blob.getName());
            File downloadFile = new File("filePath", blob.getName());

            
            //TODO: check if files locally are up to date --> if not, download them

            // Write data to file
            try (FileOutputStream outputStream = new FileOutputStream(downloadFile)) {
            outputStream.write(data);
            }
            catch (IOException e) {
                System.out.println("Error writing file: " + e.getMessage());
            }
        }
    }

}


