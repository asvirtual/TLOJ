package com.tloj.game.utilities;

import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;

import com.google.auth.oauth2.GoogleCredentials;
import com.google.cloud.storage.Bucket;
import com.google.firebase.FirebaseApp;
import com.google.firebase.FirebaseOptions;
import com.google.firebase.cloud.StorageClient;


public class FirebaseHandler {
    private static FirebaseHandler instance;
    private FirebaseApp app;

    private FirebaseHandler() {
        try {
            FileInputStream serviceAccount = new FileInputStream("./src/main/resources/tlojFirebaseServiceAccount.json");

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
    public void saveToFirebaseBucket(String filename) {
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
    public void loadFromCloudBucket(String filename) {
        Bucket bucket = StorageClient.getInstance().bucket();

        // BlobId blobId = Blob.of(bucketName, filename);
        // Blob blob = storage.get(blobId);
        // if (blob == null) {
        //     System.out.println("Error: Blob not found in the specified bucket");
        //     return null;
        // }
        // byte[] content = blob.getContent();
        // ObjectMapper mapper = new ObjectMapper();
        // try {
        //     return mapper.readValue(content, GameData.class);
        // } catch (JsonGenerationException e) {
        //     System.out.println("Error generating JSON from GameData");
        //     e.printStackTrace();
        // } catch (JsonMappingException e) {
        //     System.out.println("Error mapping JSON from GameData");
        //     e.printStackTrace();
        // } catch (IOException e) {
        //     System.out.println("Error opening file " + filename + " for reading");
        //     e.printStackTrace();
        // }
        // return null;
    }
}
