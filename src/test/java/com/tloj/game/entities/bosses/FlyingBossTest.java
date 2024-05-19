package com.tloj.game.entities.bosses;

import static org.junit.jupiter.api.Assertions.*;

import org.checkerframework.checker.units.qual.g;
import org.junit.jupiter.api.Test;

import com.tloj.game.entities.characters.BasePlayer;
import com.tloj.game.entities.Character;
import com.tloj.game.game.PlayerAttack;
import com.tloj.game.utilities.Coordinates;




public class FlyingBossTest {

    @Test
    void testAbilityUsed() {
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
        FlyingBoss flyingBoss = new FlyingBoss(new Coordinates(0, 0));
        Character mockCharacter = new BasePlayer(20, 4, 4, 10, 0, 1, 5, 10, null, null, null, null, null);
        PlayerAttack mockPlayerAttack = new PlayerAttack(mockCharacter, flyingBoss);

        do {
            flyingBoss.defend(mockPlayerAttack);
            mockPlayerAttack.perform();

            if (!flyingBoss.getAbility().wasUsed()) assertEquals(flyingBoss.getMaxHp() - flyingBoss.getHp() , mockPlayerAttack.getTotalDamage() - flyingBoss.getCurrentFightDef());
            else {
                flyingBoss = new FlyingBoss(new Coordinates(0, 0));
                mockPlayerAttack = new PlayerAttack(mockCharacter, flyingBoss);
                mockCharacter.setHp(mockCharacter.getMaxHp());
            }
        } while (flyingBoss.getAbility().wasUsed());
    }
    
}