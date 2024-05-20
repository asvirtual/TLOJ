package com.tloj.game.collectables.items;

import com.tloj.game.entities.Character;
import com.tloj.game.rooms.roomeffects.InflictDamage;
import com.tloj.game.utilities.Constants;
import com.tloj.game.utilities.Dice;
import com.tloj.game.utilities.JsonParser;
import com.tloj.game.entities.characters.BasePlayer;
import com.tloj.game.game.Controller;
import com.tloj.game.game.Game;
import com.tloj.game.game.Level;
import com.tloj.game.game.PlayerRoomVisitor;
import com.tloj.game.rooms.TrapRoom;
import java.io.ByteArrayInputStream;

import org.junit.jupiter.api.Test;
import static org.junit.jupiter.api.Assertions.*;

import java.util.ArrayList;


public class EmpTest {
    @Test
    void testConsume() {
        // Simulates user input to use Emp when prompted
        String input = "y\n";
        ByteArrayInputStream testIn = new ByteArrayInputStream(input.getBytes());
        System.setIn(testIn);
        Controller mockController= Controller.getInstance();
        Dice.setSeed(1);

        ArrayList<Level> mockLevels = JsonParser.deserializeMapFromFile(Constants.MAP_FILE_PATH);
        Game mockGame = new Game(mockLevels);
        mockController.setGame(mockGame);
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
