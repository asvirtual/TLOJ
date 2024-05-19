package com.tloj.game.entities.bosses;

import static org.junit.jupiter.api.Assertions.*;

import java.io.ByteArrayInputStream;

import org.junit.jupiter.api.Test;

import com.tloj.game.entities.characters.BasePlayer;
import com.tloj.game.entities.Character;
import com.tloj.game.game.Controller;
import com.tloj.game.game.PlayerAttack;
import com.tloj.game.utilities.Coordinates;
import com.tloj.game.collectables.weapons.LaserBlade;
import com.tloj.game.utilities.Dice;




public class FlyingBossTest {

    @Test
    void testAbilityUsed() {
        Dice.setSeed(1);
        
        FlyingBoss flyingBoss = new FlyingBoss(new Coordinates(0, 0));
        Character mockCharacter = new BasePlayer(20, 4, 4, 10, 0, 1, 5, 10, null, null, null, null, null);

        PlayerAttack mockPlayerAttack = new PlayerAttack(mockCharacter, flyingBoss);
        
        while (!flyingBoss.getAbility().wasUsed()) {
            flyingBoss.setHp(flyingBoss.getMaxHp() / 2);
            int startHp = flyingBoss.getHp();
            flyingBoss.defend(mockPlayerAttack);

            if (flyingBoss.getAbility().wasUsed()) {
                assertEquals(startHp + mockPlayerAttack.getWeaponRoll(), flyingBoss.getHp());
                assertTrue(mockPlayerAttack.getTotalDamage() == 0);
            }
            else {
                flyingBoss = new FlyingBoss(new Coordinates(0, 0));
                mockCharacter = new BasePlayer(20, 4, 4, 10, 0, 1, 5, 10, null, null, null, null, null);
                mockPlayerAttack = new PlayerAttack(mockCharacter, flyingBoss);
            }
        }
    }
    
    @Test
    void testAbilityNotUsed() {  
        String input = "\n\n";
        ByteArrayInputStream testIn = new ByteArrayInputStream(input.getBytes());
        System.setIn(testIn);
        Controller.getInstance();      
        Dice.setSeed(1);

        FlyingBoss flyingBoss = new FlyingBoss(new Coordinates(0, 0));
        Character mockCharacter = new BasePlayer(20, 4, 4, 10, 0, 1, 5, 10, null, null, new LaserBlade(), null, null);
        PlayerAttack mockPlayerAttack = new PlayerAttack(mockCharacter, flyingBoss);

        do {
            mockCharacter.getWeapon().modifyAttack(mockPlayerAttack);
            flyingBoss.defend(mockPlayerAttack);
            int totalDamage = mockPlayerAttack.getTotalDamage();
            mockPlayerAttack.perform();

            if (!flyingBoss.getAbility().wasUsed()) {
                assertEquals(flyingBoss.getMaxHp() - flyingBoss.getHp(), totalDamage);
                return;
            } else {
                flyingBoss = new FlyingBoss(new Coordinates(0, 0));
                mockPlayerAttack = new PlayerAttack(mockCharacter, flyingBoss);
                mockCharacter.setHp(mockCharacter.getMaxHp());
            }
        } while (!flyingBoss.getAbility().wasUsed());
    }
    
}