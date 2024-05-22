package com.tloj.game.game;

import static org.junit.jupiter.api.Assertions.*;

import java.io.ByteArrayInputStream;
import java.io.InputStream;

import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import com.tloj.game.collectables.weapons.LaserBlade;
import com.tloj.game.entities.characters.BasePlayer;
import com.tloj.game.entities.mobs.CyberGoblin;
import com.tloj.game.utilities.Coordinates;
import com.tloj.game.utilities.Dice;


/**
 * {@code MobAttacktest} is a test class for the {@link MobAttack} action.<br>
 * It tests the attack method with its formula, the player's defense and if its possible to kill the player.<br>
 */

public class MobAttacktest {
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
        BasePlayer target = new BasePlayer(20, 4, 1, 10, 0, 1, 5, 10, null, null, new LaserBlade(), null, null);
        CyberGoblin attacker = new CyberGoblin(new Coordinates(0,0), 1);
        MobAttack mobAttack = new MobAttack(attacker, target);

        int initialTargetHp = target.getHp();
        int mobRoll = attacker.rollDice();
        mobAttack.setDiceRoll(mobRoll);
        mobAttack.perform();

        assertNotEquals(initialTargetHp, target.getHp());
        assertEquals(4, mobAttack.getBaseAttack());
        assertTrue(mobRoll >= 1 && mobRoll <= 7);
        assertEquals((4 + mobRoll) - target.getCurrentFightDef(), initialTargetHp - target.getHp());
    }

    @Test
    void tooMuchDefenseTest() {
        BasePlayer target = new BasePlayer(20, 4, Integer.MAX_VALUE, 10, 0, 1, 5, 10, null, null, new LaserBlade(), null, null);
        CyberGoblin attacker = new CyberGoblin(new Coordinates(0,0), 1);
        MobAttack mobAttack = new MobAttack(attacker, target);

        int initialTargetHp = target.getHp();
        int mobRoll = attacker.rollDice();
        mobAttack.setDiceRoll(mobRoll);
        mobAttack.perform();

        assertEquals(initialTargetHp, target.getHp());
    }

    @Test
    void canKillTest() {
        BasePlayer target = new BasePlayer(20, 4, 4, 10, 0, 1, 5, 10, null, null, new LaserBlade(), null, null);
        CyberGoblin attacker = new CyberGoblin(new Coordinates(0,0), 99);
        MobAttack mobAttack = new MobAttack(attacker, target);

        int mobRoll = attacker.rollDice();
        mobAttack.setDiceRoll(mobRoll);
        mobAttack.perform();

        assertEquals(0, target.getHp());
    }
}
