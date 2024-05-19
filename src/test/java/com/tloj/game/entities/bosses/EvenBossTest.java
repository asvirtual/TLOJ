package com.tloj.game.entities.bosses;

import static org.junit.jupiter.api.Assertions.*;

import org.checkerframework.checker.units.qual.g;
import org.junit.jupiter.api.Test;

import com.tloj.game.entities.characters.BasePlayer;
import com.tloj.game.entities.Character;
import com.tloj.game.game.PlayerAttack;
import com.tloj.game.utilities.Coordinates;




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
        EvenBoss evenBoss = new EvenBoss(new Coordinates(0, 0));
        Character mockCharacter = new BasePlayer(20, 4, 4, 10, 0, 1, 5, 10, null, null, null, null, null);
        PlayerAttack mockPlayerAttack = new PlayerAttack(mockCharacter, evenBoss);

        do {
            evenBoss.defend(mockPlayerAttack);

            if (!evenBoss.getAbility().wasUsed()) assertEquals(evenBoss.getMaxHp() - evenBoss.getHp() , mockPlayerAttack.getTotalDamage() - evenBoss.getCurrentFightDef());
            else {
                evenBoss = new EvenBoss(new Coordinates(0, 0));
                mockPlayerAttack = new PlayerAttack(mockCharacter, evenBoss);
                mockCharacter.setHp(mockCharacter.getMaxHp());
            }
        } while (evenBoss.getAbility().wasUsed());
    }
    
}