package com.tloj.game.entities.mobs;

import static org.junit.jupiter.api.Assertions.*;

import java.util.ArrayList;

import org.junit.jupiter.api.Test;

import com.tloj.game.collectables.Item;
import com.tloj.game.collectables.items.HealthPotion;
import com.tloj.game.collectables.weapons.LaserBlade;
import com.tloj.game.entities.characters.BasePlayer;
import com.tloj.game.game.PlayerAttack;
import com.tloj.game.entities.Character;
import com.tloj.game.utilities.Coordinates;

public class JetBatTest {

    @Test
     void testConstructorLevelTwo() {
        JetBat jetBat = new JetBat(new Coordinates(0, 0), 2);
        
        assertEquals(14, jetBat.getHp());
        assertEquals(6, jetBat.getAtk());
        assertEquals(2, jetBat.getDef());
        assertEquals(6, jetBat.getCurrentFightAtk());
        assertEquals(2, jetBat.getCurrentFightDef());
        assertEquals(5, jetBat.getDiceFaces());
        assertEquals(8, jetBat.dropXp());
        assertEquals(2, jetBat.getMoneyDrop());
    }

    @Test
    void testConstructorLevelThree() {
        JetBat jetBat = new JetBat(new Coordinates(0, 0), 3);
        
        assertEquals(21, jetBat.getHp());
        assertEquals(14, jetBat.getAtk());
        assertEquals(5, jetBat.getDef());
        assertEquals(14, jetBat.getCurrentFightAtk());
        assertEquals(5, jetBat.getCurrentFightDef());
        assertEquals(5, jetBat.getDiceFaces());
        assertEquals(12, jetBat.dropXp());
        assertEquals(2, jetBat.getMoneyDrop());
    }

    @Test
    void testConstructorLevelGreaterThanThree() {
        JetBat jetBat = new JetBat(new Coordinates(0, 0), 4);
        
        assertEquals(7 * jetBat.getLvl(), jetBat.getHp());
        assertEquals(14 + 3 * (jetBat.getLvl() - 3), jetBat.getAtk());
        assertEquals(5 + jetBat.getLvl() - 3, jetBat.getDef());
        assertEquals(14 + 3 * (jetBat.getLvl() - 3), jetBat.getCurrentFightAtk());
        assertEquals(5 + jetBat.getLvl() - 3, jetBat.getCurrentFightDef());
        assertEquals(5, jetBat.getDiceFaces());
        assertEquals(4 * jetBat.getLvl(), jetBat.dropXp());
        assertEquals(2, jetBat.getMoneyDrop());
    }    

    @Test
    void testSkillUsed() {
        JetBat jetBat = new JetBat(new Coordinates(0, 0), 1);
        Character mockCharacter = new BasePlayer(20, 4, 4, 10, 0, 1, 5, 10, null, null, new LaserBlade(), null, null);
        PlayerAttack mockPlayerAttack = new PlayerAttack(mockCharacter, jetBat);
        jetBat.defend(mockPlayerAttack);

        while (!jetBat.getAbility().wasUsed()) {
            jetBat.defend(mockPlayerAttack);
            if (jetBat.getAbility().wasUsed()) {
                assertTrue(mockPlayerAttack.getTotalAttack() == 0);
                assertEquals(7, jetBat.getHp());
            } else {
                jetBat = new JetBat(new Coordinates(0, 0), 1);
                mockCharacter = new BasePlayer(20, 4, 4, 10, 0, 1, 5, 10, null, null, null, null, null);
                mockPlayerAttack = new PlayerAttack(mockCharacter, jetBat);
            }            
        }
    }

    @Test
    void testSkillNotUsed() {
        JetBat jetBat = new JetBat(new Coordinates(0, 0), 1);
        Character mockCharacter = new BasePlayer(20, 4, 4, 10, 0, 1, 5, 10, null, null, new LaserBlade(), null, null);
        PlayerAttack mockPlayerAttack = new PlayerAttack(mockCharacter, jetBat);

        do {
            jetBat.defend(mockPlayerAttack);
            if (!jetBat.getAbility().wasUsed()){
                assertTrue(mockPlayerAttack.getTotalAttack() != 0);
                assertTrue(jetBat.getHp() < 7);
            } else {
                jetBat = new JetBat(new Coordinates(0, 0), 1);
                mockPlayerAttack = new PlayerAttack(mockCharacter, jetBat);
            }
        } while (jetBat.getAbility().wasUsed());
    }
}
