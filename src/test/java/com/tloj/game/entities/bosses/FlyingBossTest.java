package com.tloj.game.entities.bosses;

import static org.junit.jupiter.api.Assertions.*;

import java.io.ByteArrayInputStream;
import java.io.InputStream;

import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import com.tloj.game.entities.characters.BasePlayer;
import com.tloj.game.entities.Character;
import com.tloj.game.game.Controller;
import com.tloj.game.game.PlayerAttack;
import com.tloj.game.utilities.Coordinates;
import com.tloj.game.collectables.weapons.LaserBlade;
import com.tloj.game.utilities.Dice;


/**
 * {@code FlyingBossTest} is a test class for the {@link FlyingBoss} entity.<br>
 * It tests the ability of the boss to dodge attacks and heal for the attacker weapon roll.<br>
 * It also tests wether the ability triggers or not.<br>
 */


public class FlyingBossTest {
    private final InputStream originalSystemIn = System.in;
    private ByteArrayInputStream testIn = new ByteArrayInputStream("\n\n".getBytes());
    
    @BeforeEach
    public void setUpInput() {
        Dice.setSeed(1);
        System.setIn(testIn);
        Controller.getInstance();
    }

    @AfterEach
    public void restoreSystemIn() {
        System.setIn(originalSystemIn);
    }

    @Test
    void testAbilityUsed() {        
        FlyingBoss flyingBoss = new FlyingBoss(new Coordinates(0, 0));
        int startHp = flyingBoss.getMaxHp() / 2;
        flyingBoss.setHp(startHp);
        Character mockCharacter = new BasePlayer(20, 4, 4, 10, 0, 1, 5, 10, null, null, null, null, null);
        PlayerAttack mockPlayerAttack = new PlayerAttack(mockCharacter, flyingBoss);
        
        while (!flyingBoss.getAbility().wasUsed()) {
            flyingBoss.defend(mockPlayerAttack);

            if (flyingBoss.getAbility().wasUsed()) {
                assertEquals(startHp + mockPlayerAttack.getWeaponRoll(), flyingBoss.getHp());
                assertTrue(mockPlayerAttack.getTotalDamage() == 0);
            }
        }
    }
    
    @Test
    void testAbilityNotUsed() {  
        FlyingBoss flyingBoss = new FlyingBoss(new Coordinates(0, 0));
        Character mockCharacter = new BasePlayer(20, 4, 4, 10, 0, 1, 5, 10, null, null, new LaserBlade(), null, null);
        PlayerAttack mockPlayerAttack = new PlayerAttack(mockCharacter, flyingBoss);

        while (true) {
            mockCharacter.getWeapon().modifyAttack(mockPlayerAttack);
            flyingBoss.defend(mockPlayerAttack);
            int totalDamage = mockPlayerAttack.getTotalDamage();
            mockPlayerAttack.perform();

            if (!flyingBoss.getAbility().wasUsed()) {
                assertEquals(flyingBoss.getMaxHp() - flyingBoss.getHp(), totalDamage);
                return;
            } else {
                flyingBoss = new FlyingBoss(new Coordinates(0, 0));
                mockPlayerAttack.setTarget(flyingBoss);
            }
        }
    }
    
}