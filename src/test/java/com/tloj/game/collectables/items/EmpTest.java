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
import com.tloj.game.game.ControllerHandler;
import com.tloj.game.game.PlayerRoomVisitor;
import com.tloj.game.rooms.TrapRoom;
import java.io.InputStream;

import org.junit.jupiter.api.Test;
import static org.junit.jupiter.api.Assertions.*;

import java.util.ArrayList;

public class EmpTest {
    private final InputStream originalSystemIn = System.in;
   
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
        
        ControllerHandler.resetInput(originalSystemIn);
    }
}
