package com.tloj.game.entities.npcs;

import org.junit.jupiter.api.Test;
import static org.junit.jupiter.api.Assertions.*;

import java.io.ByteArrayInputStream;
import java.util.ArrayList;

import com.tloj.game.utilities.Coordinates;
import com.tloj.game.entities.characters.BasePlayer;
import com.tloj.game.entities.Character;
import com.tloj.game.game.Controller;
import com.tloj.game.collectables.Item;
import com.tloj.game.collectables.items.WeaponShard;
import com.tloj.game.collectables.weapons.LaserBlade;

public class SmithTest {
    @Test
    void weaponUpgradeTest() {
       
        String input = "\n";
        ByteArrayInputStream testIn = new ByteArrayInputStream(input.getBytes());
        System.setIn(testIn);
       
        Controller.getInstance();
        Smith mockSmith = new Smith(new Coordinates(0, 0));
        ArrayList<Item> inventory = new ArrayList<Item>();
        inventory.add(new WeaponShard());
        Character mockCharacter = new BasePlayer(20, 4, 4, 10, 0, 1, 5, 100, null, null, new LaserBlade(), inventory, null);
        Item itemToGive = mockCharacter.searchInventoryItem(new WeaponShard());


        int startWeaponLevel = mockCharacter.getWeapon().getLevel();
        mockSmith.interact(mockCharacter);
        mockSmith.giveItem(itemToGive);
        int endWeaponLevel = mockCharacter.getWeapon().getLevel();

        assertEquals(startWeaponLevel + 1, endWeaponLevel);
        assertEquals(0, mockCharacter.getItemCount(new WeaponShard()));
    }

    @Test
    void noWeaponShardToUpgradeTest() {
        Controller.getInstance();
        Smith mockSmith = new Smith(new Coordinates(0, 0));
        ArrayList<Item> inventory = new ArrayList<Item>();
        Character mockCharacter = new BasePlayer(20, 4, 4, 10, 0, 1, 5, 100, null, null, new LaserBlade(), inventory, null);
        Item itemToGive = mockCharacter.searchInventoryItem(new WeaponShard());

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
        ArrayList<Item> inventory = new ArrayList<Item>();
        inventory.add(new WeaponShard());
        Character mockCharacter = new BasePlayer(20, 4, 4, 10, 0, 1, 5, 100, null, null, new LaserBlade(), inventory, null);
        Item itemToGive = mockCharacter.searchInventoryItem(new WeaponShard());
        
        mockCharacter.getWeapon().setLevel(5);
        int startWeaponLevel = mockCharacter.getWeapon().getLevel();
        
        mockSmith.interact(mockCharacter);
        mockSmith.giveItem(itemToGive);
        
        int endWeaponLevel = mockCharacter.getWeapon().getLevel();

        assertEquals(startWeaponLevel, endWeaponLevel);
    }
}
