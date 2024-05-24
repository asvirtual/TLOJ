package com.tloj.game.entities.bosses;

import static org.junit.jupiter.api.Assertions.*;

import java.io.InputStream;

import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import com.tloj.game.entities.characters.BasePlayer;
import com.tloj.game.entities.Character;
import com.tloj.game.entities.Inventory;
import com.tloj.game.game.Controller;
import com.tloj.game.game.Coordinates;
import com.tloj.game.game.Dice;
import com.tloj.game.game.MockController;
import com.tloj.game.game.PlayerAttack;
import com.tloj.game.collectables.weapons.LaserBlade;


/**
 * {@code FlyingBossTest} is a test class for the {@link FlyingBoss} entity.<br>
 * It tests the ability of the boss to dodge attacks and heal for the attacker weapon roll.<br>
 * It also tests wether the ability triggers or not.<br>
 */

public class FlyingBossTest {
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
    void testAbilityUsed() {        
        FlyingBoss flyingBoss = new FlyingBoss(new Coordinates(0, 0));
        int startHp = flyingBoss.getMaxHp() / 2;
        flyingBoss.setHp(startHp);
        Character mockCharacter = new BasePlayer(20, 4, 4, 10, 0, 1, 5, 10, null, null, null, new Inventory(), null);
        PlayerAttack mockPlayerAttack = new PlayerAttack(mockCharacter, flyingBoss);
        
        while (!flyingBoss.getAbility().wasUsed()) {
            MockController.deleteController();
            MockController.setInput("\n");
            Controller.getInstance();

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
        Character mockCharacter = new BasePlayer(20, 4, 4, 10, 0, 1, 5, 10, null, null, new LaserBlade(), new Inventory(), null);
        PlayerAttack mockPlayerAttack = new PlayerAttack(mockCharacter, flyingBoss);

        while (true) {

            MockController.deleteController();
            MockController.setInput("\n");
            Controller.getInstance();

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