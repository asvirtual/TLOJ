package com.tloj.game.entities.npcs;

import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import static org.junit.jupiter.api.Assertions.*;

import java.io.InputStream;

import com.tloj.game.entities.characters.BasePlayer;
import com.tloj.game.entities.Character;
import com.tloj.game.game.Controller;
import com.tloj.game.game.Coordinates;
import com.tloj.game.game.Dice;
import com.tloj.game.game.MockController;
import com.tloj.game.collectables.Item;
import com.tloj.game.collectables.items.WeaponShard;

public class SmithTest {
    private final InputStream originalSystemIn = System.in;
   
    @BeforeEach
    public void setUpInput() {
        Dice.setSeed(1);
        MockController.deleteController();
        Controller.getInstance();
    
    }

    @AfterEach
    public void restoreSystemIn() {
        MockController.resetInput(originalSystemIn);
    }

    @Test
    void weaponUpgradeTest() {
        Smith mockSmith = new Smith(new Coordinates(0, 0));
        Character mockCharacter = new BasePlayer(null);
        mockCharacter.addInventoryItem(new WeaponShard());
        Item itemToGive = mockCharacter.getInventoryItem(new WeaponShard());

        MockController.deleteController();
        MockController.setInput("\n");
        Controller.getInstance();

        int startWeaponLevel = mockCharacter.getWeapon().getLevel();
        mockSmith.interact(mockCharacter);
        mockSmith.giveItem(itemToGive);
        int endWeaponLevel = mockCharacter.getWeapon().getLevel();

        assertEquals(startWeaponLevel + 1, endWeaponLevel);
        assertEquals(0, mockCharacter.getItemCount(new WeaponShard()));
    }

    @Test
    void noWeaponShardToUpgradeTest() {
        Smith mockSmith = new Smith(new Coordinates(0, 0));
        Character mockCharacter = new BasePlayer(null);
        Item itemToGive = mockCharacter.getInventoryItem(new WeaponShard());

        MockController.deleteController();
        MockController.setInput("\n");
        Controller.getInstance();

        int startWeaponLevel = mockCharacter.getWeapon().getLevel();
        mockSmith.interact(mockCharacter);
        mockSmith.giveItem(itemToGive);
        int endWeaponLevel = mockCharacter.getWeapon().getLevel();

        assertEquals(startWeaponLevel, endWeaponLevel);
    }

    @Test
    void maxLevelWeaponUpgradeTest() {
        Smith mockSmith = new Smith(new Coordinates(0, 0));
        Character mockCharacter = new BasePlayer(null);
        mockCharacter.addInventoryItem(new WeaponShard());
        Item itemToGive = mockCharacter.getInventoryItem(new WeaponShard());
        
        mockCharacter.getWeapon().setLevel(5);
        int startWeaponLevel = mockCharacter.getWeapon().getLevel();
        
        MockController.deleteController();
        MockController.setInput("\n");
        Controller.getInstance();

        mockSmith.interact(mockCharacter);
        mockSmith.giveItem(itemToGive);
        
        int endWeaponLevel = mockCharacter.getWeapon().getLevel();

        assertEquals(startWeaponLevel, endWeaponLevel);
    }
}
