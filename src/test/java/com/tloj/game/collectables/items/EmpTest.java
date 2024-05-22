package com.tloj.game.collectables.items;

import com.tloj.game.entities.Character;
import com.tloj.game.rooms.roomeffects.InflictDamage;
import com.tloj.game.utilities.Constants;
import com.tloj.game.utilities.Dice;
import com.tloj.game.utilities.JsonParser;
import com.tloj.game.entities.characters.BasePlayer;
import com.tloj.game.game.Controller;
import com.tloj.game.game.Floor;
import com.tloj.game.game.Game;
import com.tloj.game.game.PlayerRoomVisitor;
import com.tloj.game.rooms.TrapRoom;
import java.io.ByteArrayInputStream;
import java.io.InputStream;

import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import static org.junit.jupiter.api.Assertions.*;

import java.util.ArrayList;


public class EmpTest {
    private final InputStream originalSystemIn = System.in;
    Thread inputThread;
    
    @BeforeEach
    public void setUpInput() {
        this.inputThread =  new Thread(() -> {
            while (true) {
                System.setIn(new ByteArrayInputStream("y\n".getBytes()));
                try {
                    Thread.sleep(100);  // Sleep for a short time to ensure the input is read
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
            }
        });

        inputThread.start();
        Dice.setSeed(1);
        Controller.getInstance();
    }

    @AfterEach
    public void restoreSystemIn() {
        this.inputThread.interrupt();
        System.setIn(originalSystemIn);
    }
    
    @Test
    void testConsume() {
        // Simulates user input to use Emp when prompted
       

        ArrayList<Floor> mockLevels = JsonParser.deserializeMapFromFile(Constants.MAP_FILE_PATH);
        Game mockGame = new Game(mockLevels, 1);
        Controller.getInstance().setGame(mockGame);
        
        Character mockCharacter = new BasePlayer(null);
        Emp emp = new Emp();
        mockCharacter.addInventoryItem(emp);
        mockGame.setPlayer(mockCharacter);

        TrapRoom mockDamageTrapRoom = new TrapRoom(null, new InflictDamage());
        PlayerRoomVisitor mockPlayerRoomVisitor = new PlayerRoomVisitor(mockCharacter);

        mockPlayerRoomVisitor.visit(mockDamageTrapRoom);
        assertEquals(mockCharacter.getMaxHp(), mockCharacter.getHp());
        assertNull(mockCharacter.getInventoryItem(emp));
    }
}
