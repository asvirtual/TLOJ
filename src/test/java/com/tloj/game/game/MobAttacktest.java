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


/**
 * {@code MobAttacktest} is a test class for the {@link MobAttack} action.<br>
 * It tests the attack method with its formula, the player's defense and if its possible to kill the player.<br>
 */

public class MobAttackTest {
    private final InputStream originalSystemIn = System.in;
   
    @BeforeEach
    public void setUpInput() {
        try {
            Thread.sleep(100); 
            
            String input = "";
            for (int i = 0; i < 10000; i++) {
                input += "\n";
            }

            System.setIn(new ByteArrayInputStream(input.getBytes()));

            Dice.setSeed(1);
            Controller.getInstance();
        }
        catch(InterruptedException e){
            e.printStackTrace();
        }
    }

    @AfterEach
    public void restoreSystemIn() {
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
