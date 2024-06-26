package com.tloj.game.entities.bosses;

import static org.junit.jupiter.api.Assertions.*;

import java.io.InputStream;

import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import com.tloj.game.entities.characters.BasePlayer;
import com.tloj.game.entities.Character;
import com.tloj.game.game.Controller;
import com.tloj.game.game.Coordinates;
import com.tloj.game.game.Dice;
import com.tloj.game.game.ControllerHandler;
import com.tloj.game.game.PlayerAttack;
import com.tloj.game.collectables.weapons.LaserBlade;


/**
 * {@code EvenBossTest} is a test class for the {@link EvenBoss} entity.<br>
 * It tests the ability of the boss to only take damage from even-numbered rolls.<br>
 * It also tests wether the ability triggers or not.<br>
 */

public class EvenBossTest {
    private final InputStream originalSystemIn = System.in;
   
    @BeforeEach
    public void setUpInput() {
        Dice.setSeed(1);
        ControllerHandler.deleteController();
        Controller.getInstance();           
    }


    @AfterEach
    public void restoreSystemIn() {
        ControllerHandler.resetInput(originalSystemIn);
    }

    @Test
    void testAbilityUsed() {
        EvenBoss evenBoss = new EvenBoss(new Coordinates(0, 0));
        Character mockCharacter = new BasePlayer(20, 4, 4, 10, 0, 1, 5, 10, null, null, null, null, null);

        PlayerAttack mockPlayerAttack = new PlayerAttack(mockCharacter, evenBoss);
        
        while (!evenBoss.getAbility().wasUsed()) {
            ControllerHandler.deleteController();
            ControllerHandler.setInput("\n");
            Controller.getInstance();
            evenBoss.defend(mockPlayerAttack);

            if (evenBoss.getAbility().wasUsed()) 
                assertTrue((mockPlayerAttack.getWeaponRoll()) % 2 == 0);
        }
    }
    
    @Test
    void testAbilityNotUsed() {
        EvenBoss evenBoss = new EvenBoss(new Coordinates(0, 0));
        Character mockCharacter = new BasePlayer(20, 4, 4, 10, 0, 1, 5, 10, null, null, new LaserBlade(), null, null);
        PlayerAttack mockPlayerAttack = new PlayerAttack(mockCharacter, evenBoss);
    
        while (true) {
            ControllerHandler.deleteController();
            ControllerHandler.setInput("\n");
            Controller.getInstance();

            mockCharacter.getWeapon().modifyAttack(mockPlayerAttack);
            evenBoss.defend(mockPlayerAttack);
            int totalDamage = mockPlayerAttack.getTotalDamage();
            mockPlayerAttack.perform();

            if (!evenBoss.getAbility().wasUsed()) {
                assertEquals(evenBoss.getMaxHp() - evenBoss.getHp(), totalDamage);
                return;
            } else {
                evenBoss = new EvenBoss(new Coordinates(0, 0));
                mockPlayerAttack.setTarget(evenBoss);
            }
        }
    }
}