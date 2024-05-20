package com.tloj.game.collectables.items;


import com.tloj.game.collectables.Item;
import com.tloj.game.collectables.weapons.LaserBlade;
import com.tloj.game.entities.Character;
import com.tloj.game.rooms.roomeffects.InflictDamage;
import com.tloj.game.rooms.roomeffects.StealMoney;
import com.tloj.game.rooms.roomeffects.TpEffect;
import com.tloj.game.utilities.Constants;
import com.tloj.game.utilities.Dice;
import com.tloj.game.utilities.JsonParser;
import com.tloj.game.utilities.MusicPlayer;
import com.tloj.game.entities.characters.BasePlayer;
import com.tloj.game.game.Controller;
import com.tloj.game.game.Game;
import com.tloj.game.game.Level;
import com.tloj.game.game.PlayerRoomVisitor;
import com.tloj.game.rooms.TrapRoom;
import java.io.ByteArrayInputStream;


import org.junit.jupiter.api.Test;
import static org.junit.jupiter.api.Assertions.*;

import java.io.ByteArrayInputStream;
import java.util.ArrayList;


public class EmpTest {
    @Test
    void testConsume() {
        String input = "\n\n\n\n\n\n\n\n\n\n\n\n";
        ByteArrayInputStream testIn = new ByteArrayInputStream(input.getBytes());
        System.setIn(testIn);
        Controller mockController= Controller.getInstance();
        Dice.setSeed(1);
        long s =1;

        ArrayList<Level> mockLevels = JsonParser.deserializeMapFromFile(Constants.MAP_FILE_PATH);
        Game mockGame = new Game(mockLevels, s);

        mockController.setGame(mockGame);

        Character mockCharacter = new BasePlayer(
            20, 4, 4, 10, 0, 1, 5, 10, null, null, new LaserBlade(), new ArrayList<Item>(), null
        );

        mockGame.setPlayer(mockCharacter);
        MusicPlayer mockMusicPlayer = new MusicPlayer("src/main/resources/sounds/MainMenu.wav");


        TrapRoom mockDamageTrapRoom = new TrapRoom(null, new InflictDamage());
        TrapRoom mockStealTrapRoom = new TrapRoom(null, new StealMoney());
        TrapRoom mockTP_TrapRoom = new TrapRoom(null, new TpEffect());

        PlayerRoomVisitor mockPlayerRoomVisitor = new PlayerRoomVisitor(mockCharacter);
        boolean flag = false;
        while(!flag)
        {
            //mockPlayerRoomVisitor.visit(mockDamageTrapRoom);
            if(mockDamageTrapRoom.triggerTrap(mockCharacter))
            {
                assertEquals(10, mockCharacter.getHp());
                flag=true;
                return ;
            }
            mockCharacter.setHp(20);
            
     
        }
        mockPlayerRoomVisitor.visit(mockDamageTrapRoom);
        assertEquals(10, mockCharacter.getHp());

        mockCharacter.addInventoryItem(new Emp());
        mockCharacter.addInventoryItem(new Emp());
        mockCharacter.addInventoryItem(new Emp());

        Emp item = (Emp) mockCharacter.getInventoryItem(0);
        item.consume(mockCharacter);
        assertEquals(25, mockCharacter.getHp());
        assertEquals("", mockCharacter.getInventory());
    }
}
