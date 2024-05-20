package com.tloj.game.skill;

import static org.junit.jupiter.api.Assertions.*;

import java.util.ArrayList;

import org.junit.jupiter.api.Test;

import com.tloj.game.entities.characters.DataThief;
import com.tloj.game.entities.Character;
import com.tloj.game.rooms.HostileRoom;
import com.tloj.game.entities.Inventory;
import com.tloj.game.entities.mobs.CyberGoblin;
import com.tloj.game.game.Controller;
import com.tloj.game.game.Level;
import com.tloj.game.game.PlayerAttack;
import com.tloj.game.utilities.Coordinates;
import com.tloj.game.utilities.Dice;
import com.tloj.game.collectables.weapons.LaserBlade;
import com.tloj.game.collectables.weapons.NanoDirk;
import com.tloj.game.collectables.Item;

import com.tloj.game.rooms.*;


public class StealTest {
    
    @Test
    void stealSuccessTest(){
       
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
            
            if (mockCharacter.getInventorySize() == 0){
                mockCharacter.setMana(10);
                mockCyberGoblin.setHp(15);
            }
            else{
                used = true;
                assertEquals(1, mockCharacter.getInventorySize());
                assertEquals(mockCharacter.getMaxMana() - 10, mockCharacter.getMana());
            }
        } while (!used);    
    }
    
    @Test
    void stealFailedTest(){
        
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
            
            if (mockCharacter.getInventorySize() != 0){
                mockCharacter.setMana(10);
                mockCyberGoblin.setHp(15);
                mockCharacter.removeInventoryItem(0);
            }
            else{
                used = true;
                assertEquals(0, mockCharacter.getInventorySize());
                assertEquals(mockCharacter.getMaxMana() - 10, mockCharacter.getMana());
            }
        } while (!used);    
    }


}
