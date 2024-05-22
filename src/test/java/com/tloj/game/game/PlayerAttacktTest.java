package com.tloj.game.game;

import static org.junit.jupiter.api.Assertions.*;

import java.io.ByteArrayInputStream;
import java.io.InputStream;

import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import com.tloj.game.collectables.weapons.LaserBlade;
import com.tloj.game.entities.Inventory;
import com.tloj.game.entities.characters.BasePlayer;
import com.tloj.game.entities.mobs.CyberGoblin;


/**
 * {@code PlayerAttacktTest} is a test class for the {@link PlayerAttack} action.<br>
 * It tests the attack method with its formula, the mob's defense, the lootMob method and if its possible to kill the mob.<br>
 */

public class PlayerAttacktTest {
    private final InputStream originalSystemIn = System.in;
    Thread inputThread;
    
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
    void testAttack() {
        BasePlayer player = new BasePlayer(20, 4, 4, 10, 0, 1, 5, 10, null, null, new LaserBlade(), null, null);
        CyberGoblin enemy = new CyberGoblin(new Coordinates(0,0), 1);
        PlayerAttack playerAttack = new PlayerAttack(player, enemy);
        
        int initialEnemyHp = enemy.getHp();
        player.getWeapon().modifyAttack(playerAttack);
        int weaponRoll = playerAttack.getWeaponRoll();
        playerAttack.perform();

        assertNotEquals(initialEnemyHp, enemy.getHp());
        assertEquals(4, playerAttack.getBaseAttack());
        assertEquals((4 + weaponRoll) - enemy.getCurrentFightDef(),initialEnemyHp - enemy.getHp());
    }

    @Test
    void tooMuchDefenseTest() {
        BasePlayer player = new BasePlayer(20, 4, 4, 10, 0, 1, 5, 10, null, null, new LaserBlade(), null, null);
        CyberGoblin enemy = new CyberGoblin(new Coordinates(0,0), 5);
        PlayerAttack playerAttack = new PlayerAttack(player, enemy);
        
        int initialEnemyHp = enemy.getHp();
        playerAttack.setWeaponRoll(1);
        playerAttack.perform();
        assertEquals(initialEnemyHp, enemy.getHp());
    }

    @Test
    void canKillTest() {
        BasePlayer player = new BasePlayer(20, Integer.MAX_VALUE, 4, 10, 0, 1, 5, 10, null, null, new LaserBlade(), null, null);
        CyberGoblin enemy = new CyberGoblin(new Coordinates(0,0), 1);
        PlayerAttack playerAttack = new PlayerAttack(player, enemy);
        
        playerAttack.perform();
        assertEquals(0, enemy.getHp());
    }

    @Test
    void lootMobTest() {
        Inventory inventory = new Inventory();
        BasePlayer player = new BasePlayer(20, Integer.MAX_VALUE, 4, 10, 0, 1, 5, 10, null, null, new LaserBlade(), inventory, null);
        inventory.setHolder(player);
        CyberGoblin enemy = new CyberGoblin(new Coordinates(0,0), 1);
        PlayerAttack playerAttack = new PlayerAttack(player, enemy);
        
        while (inventory.getSize() == 0) {
            playerAttack.perform();
            player.lootMob(enemy);
            enemy = new CyberGoblin(new Coordinates(0,0), 1);
            playerAttack.setTarget(enemy);
        }

        assertTrue(inventory.getSize() > 0);
        assertTrue(player.getMoney() > 0);
        assertTrue(player.getXp() > 0);
        
    }
}
