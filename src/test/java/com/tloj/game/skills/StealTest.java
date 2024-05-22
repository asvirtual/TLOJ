package com.tloj.game.skills;

import static org.junit.jupiter.api.Assertions.*;

import org.junit.jupiter.api.Test;

import com.tloj.game.entities.characters.DataThief;
import com.tloj.game.entities.Character;
import com.tloj.game.rooms.HostileRoom;
import com.tloj.game.entities.Inventory;
import com.tloj.game.entities.mobs.CyberGoblin;
import com.tloj.game.game.Controller;
import com.tloj.game.game.Coordinates;
import com.tloj.game.game.Dice;
import com.tloj.game.game.PlayerAttack;
import com.tloj.game.collectables.weapons.NanoDirk;

import com.tloj.game.rooms.*;


public class StealTest {
    @Test
    void stealSuccessTest() {
        Dice.setSeed(1);
        Controller.getInstance();
        CyberGoblin mockCyberGoblin = new CyberGoblin(new Coordinates(0, 0), 1);
        Room mockRoom = new HostileRoom(new Coordinates(0, 0), mockCyberGoblin);
        Inventory mockInventory = new Inventory();
        Character mockCharacter = new DataThief(20, 4, 2, 10, 0, 1, 5, 0, null, mockRoom, new NanoDirk(), mockInventory, null);
        mockInventory.setHolder(mockCharacter);

        PlayerAttack mockPlayerAttack = new PlayerAttack(mockCharacter, mockCyberGoblin);

        boolean used = false;
        do {
            mockCharacter.getSkill().activate();
            mockCharacter.getSkill().execute(mockPlayerAttack);
            
            if (mockCharacter.getInventorySize() == 0){
                mockCharacter.setMana(10);
            }
            else{
                used = true;
                assertEquals(1, mockCharacter.getInventorySize());
                assertEquals(mockCharacter.getMaxMana() - 10, mockCharacter.getMana());
            }
        } while (!used);    
    }
    
    @Test
    void stealFailedTest() {
        Dice.setSeed(1);
        Controller.getInstance();
        CyberGoblin mockCyberGoblin = new CyberGoblin(new Coordinates(0, 0), 1);
        Room mockRoom = new HostileRoom(new Coordinates(0, 0), mockCyberGoblin);
        Inventory mockInventory = new Inventory(null);
        Character mockCharacter = new DataThief(20, 4, 2, 10, 0, 1, 5, 0, null, mockRoom, new NanoDirk(), mockInventory, null);
        mockInventory.setHolder(mockCharacter);

        PlayerAttack mockPlayerAttack = new PlayerAttack(mockCharacter, mockCyberGoblin);

        boolean used = false;
        do {
            mockCharacter.getSkill().activate();
            mockCharacter.getSkill().execute(mockPlayerAttack);
            
            if (mockCharacter.getInventorySize() != 0) {
                mockCharacter.setMana(10);
                mockCharacter.removeInventoryItem(0);
            } else{
                used = true;
                assertEquals(0, mockCharacter.getInventorySize());
                assertEquals(mockCharacter.getMaxMana() - 10, mockCharacter.getMana());
            }
        } while (!used);    
    }

    @Test
    public void noManaStealTest() {
        Dice.setSeed(1);
        Controller.getInstance();
        CyberGoblin mockCyberGoblin = new CyberGoblin(new Coordinates(0, 0), 1);
        Room mockRoom = new HostileRoom(new Coordinates(0, 0), mockCyberGoblin);
        Inventory mockInventory = new Inventory(null);
        Character mockCharacter = new DataThief(20, 4, 2, 4, 0, 1, 5, 0, null, mockRoom, new NanoDirk(), mockInventory, null);
        mockInventory.setHolder(mockCharacter);

        PlayerAttack mockPlayerAttack = new PlayerAttack(mockCharacter, mockCyberGoblin);

        mockCharacter.getSkill().activate();
        mockCharacter.getSkill().execute(mockPlayerAttack);

        assertEquals(0, mockCharacter.getInventorySize());
        assertEquals(mockCharacter.getMaxMana(), mockCharacter.getMana());
    }
}
