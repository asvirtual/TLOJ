package com.tloj.game.entities.npcs;

import org.junit.jupiter.api.Test;
import static org.junit.jupiter.api.Assertions.*;

import java.io.ByteArrayInputStream;

import com.tloj.game.entities.characters.BasePlayer;
import com.tloj.game.entities.Character;
import com.tloj.game.game.Controller;
import com.tloj.game.game.Coordinates;
import com.tloj.game.game.Dice;
import com.tloj.game.collectables.Item;
import com.tloj.game.collectables.items.WeaponShard;

public class SmithTest {
    @Test
    void weaponUpgradeTest() {
       
        String input = "\n";
        ByteArrayInputStream testIn = new ByteArrayInputStream(input.getBytes());
        System.setIn(testIn);
       
        Controller.getInstance();
        Smith mockSmith = new Smith(new Coordinates(0, 0));
        Character mockCharacter = new BasePlayer(null);
        mockCharacter.addInventoryItem(new WeaponShard());
        Item itemToGive = mockCharacter.getInventoryItem(new WeaponShard());


        int startWeaponLevel = mockCharacter.getWeapon().getLevel();
        mockSmith.interact(mockCharacter);
        mockSmith.giveItem(itemToGive);
        int endWeaponLevel = mockCharacter.getWeapon().getLevel();

        assertEquals(startWeaponLevel + 1, endWeaponLevel);
        assertEquals(0, mockCharacter.getItemCount(new WeaponShard()));
    }

    @Test
    void noWeaponShardToUpgradeTest() {
        String input = "\n";
        ByteArrayInputStream testIn = new ByteArrayInputStream(input.getBytes());
        System.setIn(testIn);

        Controller.getInstance();

        Smith mockSmith = new Smith(new Coordinates(0, 0));
        Character mockCharacter = new BasePlayer(null);
        Item itemToGive = mockCharacter.getInventoryItem(new WeaponShard());

        int startWeaponLevel = mockCharacter.getWeapon().getLevel();
        mockSmith.interact(mockCharacter);
        mockSmith.giveItem(itemToGive);
        int endWeaponLevel = mockCharacter.getWeapon().getLevel();

        assertEquals(startWeaponLevel, endWeaponLevel);
    }

    @Test
    void maxLevelWeaponUpgradeTest() {
        Controller.getInstance();
        Smith mockSmith = new Smith(new Coordinates(0, 0));
        Character mockCharacter = new BasePlayer(null);
        mockCharacter.addInventoryItem(new WeaponShard());
        Item itemToGive = mockCharacter.getInventoryItem(new WeaponShard());
        
        mockCharacter.getWeapon().setLevel(5);
        int startWeaponLevel = mockCharacter.getWeapon().getLevel();
        
        mockSmith.interact(mockCharacter);
        mockSmith.giveItem(itemToGive);
        
        int endWeaponLevel = mockCharacter.getWeapon().getLevel();

        assertEquals(startWeaponLevel, endWeaponLevel);
    }
}
