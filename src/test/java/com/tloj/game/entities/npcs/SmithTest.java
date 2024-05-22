package com.tloj.game.entities.npcs;

import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import static org.junit.jupiter.api.Assertions.*;

import java.io.ByteArrayInputStream;
import java.io.InputStream;

import com.tloj.game.utilities.Coordinates;
import com.tloj.game.utilities.Dice;
import com.tloj.game.entities.characters.BasePlayer;
import com.tloj.game.entities.Character;
import com.tloj.game.game.Controller;
import com.tloj.game.collectables.Item;
import com.tloj.game.collectables.items.WeaponShard;

public class SmithTest {
    private final InputStream originalSystemIn = System.in;
    private Thread inputThread;
    
    @BeforeEach
    public void setUpInput() {
        this.inputThread =  new Thread(() -> {
            while (true) {
                System.setIn(new ByteArrayInputStream("\n\n".getBytes()));
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
    void weaponUpgradeTest() {
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
