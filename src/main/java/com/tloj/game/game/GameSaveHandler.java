package com.tloj.game.game;

import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.util.ArrayList;

import com.fasterxml.jackson.core.JsonGenerationException;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.JsonMappingException;
import com.fasterxml.jackson.databind.ObjectMapper;

import com.google.auth.oauth2.GoogleCredentials;
import com.google.cloud.storage.Bucket;
import com.google.firebase.FirebaseApp;
import com.google.firebase.FirebaseOptions;
import com.google.firebase.cloud.StorageClient;


public class GameSaveHandler {
    private static GameSaveHandler instance;
    private FirebaseApp app;

    private GameSaveHandler() {
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

    public static GameSaveHandler getInstance() {
        if (instance == null) instance = new GameSaveHandler();
        return instance;
    }
    
    /**
     * Saves the given Game object to a JSON file with the specified filename.
     *
     * @param game The Game object to be saved.
     * @param filename The filename for the JSON file.
     */
    public void saveToFile(Game game, String filename) {
        ObjectMapper mapper = new ObjectMapper();

        try {
            mapper.writeValue(new File(filename), game);
        } catch (JsonGenerationException e) {
            System.out.println("Error generating JSON from GameData");
            e.printStackTrace();
        } catch (JsonMappingException e) {
            System.out.println("Error mapping JSON from GameData");
            e.printStackTrace();
        } catch (IOException e) {
            System.out.println("Error opening file " + filename + " for writing");
            e.printStackTrace();
        } 
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
     * Loads Game from a JSON file with the specified filename.
     *
     * @param filename The filename of the JSON file.
     * @return The loaded Game object.
     */
    public Game loadFromFile(String filename) {
        ObjectMapper mapper = new ObjectMapper();

        try {
            return mapper.readValue(new File(filename), Game.class);
        } catch (JsonGenerationException e) {
            System.out.println("Error generating JSON from GameData");
            e.printStackTrace();
        } catch (JsonMappingException e) {
            System.out.println("Error mapping JSON from GameData");
            e.printStackTrace();
        } catch (IOException e) {
            System.out.println("Error opening file " + filename + " for reading");
            e.printStackTrace();
        }

        return null;
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

    /**
     * Serializes this GameData object to a JSON string.
     *
     * @return The JSON string representing this GameData.
     */
    public String serializeJSON(Game game) {
        ObjectMapper mapper = new ObjectMapper();

        try {
            return mapper.writeValueAsString(game);
        } catch (JsonGenerationException e) {
            System.out.println("Error generating JSON from GameData");
            e.printStackTrace();
        } catch (JsonMappingException e) {
            System.out.println("Error mapping JSON from GameData");
            e.printStackTrace();
        } catch (JsonProcessingException e) {
            System.out.println("Error processing JSON from GameData");
            e.printStackTrace();
        }

        return null;
    }

    /**
     * Deserializes a GameData object from a JSON string.
     *
     * @param json The JSON string to deserialize.
     * @return The deserialized GameData object.
     */
    public Game deserializeJSON(String json) {
        ObjectMapper mapper = new ObjectMapper();

        try {
            return mapper.readValue(json, Game.class);
        } catch (JsonGenerationException e) {
            System.out.println("Error generating JSON from GameData");
            e.printStackTrace();
        } catch (JsonMappingException e) {
            System.out.println("Error mapping JSON from GameData");
            e.printStackTrace();
        } catch (JsonProcessingException e) {
            System.out.println("Error processing JSON from GameData");
            e.printStackTrace();
        }

        return null;
    }

    /**
     * Deserializes a list of levels from a JSON string.
     * TODO: This method will be deleted once we only get the maps from a file.
     *
     * @param json The JSON string representing the levels.
     * @return The deserialized list of levels.
     */
    public ArrayList<Level> deserializeMap(String json) {
        ObjectMapper mapper = new ObjectMapper();

        try {
            return mapper.readValue(json, new TypeReference<ArrayList<Level>>(){});
        } catch (JsonGenerationException e) {
            System.out.println("Error generating JSON from GameData");
            e.printStackTrace();
        } catch (JsonMappingException e) {
            System.out.println("Error mapping JSON from GameData");
            e.printStackTrace();
        } catch (JsonProcessingException e) {
            System.out.println("Error processing JSON from GameData");
            e.printStackTrace();
        }

        return null;
    }

    /**
     * Deserializes a list of levels from a JSON file with the specified filename.
     *
     * @param filename The filename of the JSON file.
     * @return The deserialized list of levels.
     */
    public ArrayList<Level> deserializeMapFromFile(String filename) {
        ObjectMapper mapper = new ObjectMapper();

        try {
            return mapper.readValue(new File(filename), new TypeReference<ArrayList<Level>>(){});
        } catch (JsonGenerationException e) {
            System.out.println("Error generating JSON from GameData");
            e.printStackTrace();
        } catch (JsonMappingException e) {
            System.out.println("Error mapping JSON from GameData");
            e.printStackTrace();
        } catch (IOException e) {
            System.out.println("Error opening file " + filename + " for reading");
            e.printStackTrace();
        }

        return null;
    }
}
