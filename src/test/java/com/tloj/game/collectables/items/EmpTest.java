package com.tloj.game.collectables.items;

import com.tloj.game.entities.Character;
import com.tloj.game.rooms.roomeffects.InflictDamage;
import com.tloj.game.utilities.Constants;
import com.tloj.game.game.Dice;
import com.tloj.game.utilities.JsonParser;
import com.tloj.game.entities.characters.BasePlayer;
import com.tloj.game.game.Controller;
import com.tloj.game.game.Floor;
import com.tloj.game.game.Game;
import com.tloj.game.game.GameIndex;
import com.tloj.game.game.ControllerHandler;
import com.tloj.game.game.PlayerRoomVisitor;
import com.tloj.game.rooms.TrapRoom;

import org.junit.jupiter.api.AfterAll;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;
import static org.junit.jupiter.api.Assertions.*;

import java.io.File;
import java.io.IOException;
import java.io.InputStream;
import java.nio.file.Files;
import java.nio.file.StandardCopyOption;
import java.util.ArrayList;


public class EmpTest {
    private final InputStream originalSystemIn = System.in;  

    @BeforeAll
    static void backupLocalSaves() {
        File backupDir = new File("test-backup");
        backupDir.mkdir();

        File savesDir = new File(Constants.BASE_SAVES_DIRECTORY);
        if (!savesDir.exists()) savesDir.mkdir();
        File file = new File(Constants.BASE_SAVES_DIRECTORY + Constants.GAMES_INDEX_FILE_PATH);
    
        if (!file.exists()) return;
        if (savesDir.length() == 0) return;
        
        File[] files = savesDir.listFiles();
        for (File f : files) {
            File backupFile = new File(backupDir.getPath() + "/" + f.getName());
            try {
                Files.move(f.toPath(), backupFile.toPath(), StandardCopyOption.REPLACE_EXISTING);
            } catch (IOException e) {
                e.printStackTrace();
            }            
        }
    }

    @AfterAll
    static void restoreLocalSaves() {
        File backupDir = new File("test-backup");
        File savesDir = new File(Constants.BASE_SAVES_DIRECTORY);

        File[] testSaveFiles = savesDir.listFiles();
        for (File f : testSaveFiles) f.delete();
        
        File[] backupFiles = backupDir.listFiles();
        for (File f : backupFiles) {
            File originalFile = new File(savesDir.getPath() + "/" + f.getName());
            try {
                Files.move(f.toPath(), originalFile.toPath(), StandardCopyOption.REPLACE_EXISTING);
            } catch (IOException e) {
                e.printStackTrace();
            }            
        }

        backupDir.delete();
    }
   
    @Test
    void testConsume() {
        // Simulates user input to use Emp when prompted
        ControllerHandler.setInput("y\n");
        Dice.setSeed(1);
        ControllerHandler.deleteController();
        Controller.getInstance();

        ArrayList<Floor> mockLevels = JsonParser.deserializeMapFromFile(Constants.MAP_FILE_PATH);
        Game mockGame = new Game(mockLevels, 1);
        Controller.getInstance().setGame(mockGame);

        GameIndex.loadGames();
        
        Character mockCharacter = new BasePlayer(null);
        Emp emp = new Emp();
        mockCharacter.addInventoryItem(emp);
        mockGame.setPlayer(mockCharacter);

        TrapRoom mockDamageTrapRoom = new TrapRoom(null, new InflictDamage());
        PlayerRoomVisitor mockPlayerRoomVisitor = new PlayerRoomVisitor(mockCharacter);

        mockPlayerRoomVisitor.visit(mockDamageTrapRoom);
        assertEquals(mockCharacter.getMaxHp(), mockCharacter.getHp());
        assertNull(mockCharacter.getInventoryItem(emp));
        
        ControllerHandler.resetInput(originalSystemIn);
    }
}
