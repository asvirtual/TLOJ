package com.tloj.game.entities.bosses;

import static org.junit.jupiter.api.Assertions.*;

import java.io.ByteArrayInputStream;

import org.checkerframework.checker.units.qual.g;
import org.junit.jupiter.api.Test;

import com.tloj.game.entities.characters.BasePlayer;
import com.tloj.game.entities.Character;
import com.tloj.game.game.Controller;
import com.tloj.game.game.PlayerAttack;
import com.tloj.game.utilities.Coordinates;
import com.tloj.game.utilities.Dice;
import com.tloj.game.collectables.weapons.LaserBlade;




public class EvenBossTest {

    @Test
    void testAbilityUsed() {
        EvenBoss evenBoss = new EvenBoss(new Coordinates(0, 0));
        Character mockCharacter = new BasePlayer(20, 4, 4, 10, 0, 1, 5, 10, null, null, null, null, null);

        PlayerAttack mockPlayerAttack = new PlayerAttack(mockCharacter, evenBoss);
        
        while (!evenBoss.getAbility().wasUsed()) {
            evenBoss.defend(mockPlayerAttack);

            if (evenBoss.getAbility().wasUsed()) 
                assertTrue((mockPlayerAttack.getWeaponRoll()) % 2 == 0);
            else {
                evenBoss = new EvenBoss(new Coordinates(0, 0));
                mockCharacter = new BasePlayer(20, 4, 4, 10, 0, 1, 5, 10, null, null, null, null, null);
                mockPlayerAttack = new PlayerAttack(mockCharacter, evenBoss);
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
        
        EvenBoss evenBoss = new EvenBoss(new Coordinates(0, 0));
        Character mockCharacter = new BasePlayer(20, 4, 4, 10, 0, 1, 5, 10, null, null, new LaserBlade(), null, null);
        PlayerAttack mockPlayerAttack = new PlayerAttack(mockCharacter, evenBoss);

        do {
            mockCharacter.getWeapon().modifyAttack(mockPlayerAttack);
            evenBoss.defend(mockPlayerAttack);
            int totalDamage = mockPlayerAttack.getTotalDamage();
            mockPlayerAttack.perform();

            if (!evenBoss.getAbility().wasUsed()) {
                assertEquals(evenBoss.getMaxHp() - evenBoss.getHp(), totalDamage);
                return;
            }
            else {
                evenBoss = new EvenBoss(new Coordinates(0, 0));
                mockPlayerAttack = new PlayerAttack(mockCharacter, evenBoss);
                mockCharacter.setHp(mockCharacter.getMaxHp());
            }
        } while (!evenBoss.getAbility().wasUsed());
    }
}