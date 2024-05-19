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
        
        assertEquals(28, jetBat.getHp());
        assertEquals(17, jetBat.getAtk());
        assertEquals(6, jetBat.getDef());
        assertEquals(17, jetBat.getCurrentFightAtk());
        assertEquals(6, jetBat.getCurrentFightDef());
        assertEquals(5, jetBat.getDiceFaces());
        assertEquals(16, jetBat.dropXp());
        assertEquals(2, jetBat.getMoneyDrop());
    }    

    @Test
    void testSkillUsed() {
        JetBat jetBat = new JetBat(new Coordinates(0, 0), 1);
        Character mockCharacter = new BasePlayer(20, 4, 4, 10, 0, 1, 5, 10, null, null, new LaserBlade(), null, null);
        PlayerAttack mockPlayerAttack = new PlayerAttack(mockCharacter, jetBat);
        jetBat.defend(mockPlayerAttack);

        if (jetBat.getAbility().wasUsed()) assertTrue(mockPlayerAttack.getTotalAttack() == 0 );
    }

    @Test
    void testSkillNotUsed() {
        JetBat jetBat = new JetBat(new Coordinates(0, 0), 1);
        Character mockCharacter = new BasePlayer(20, 4, 4, 10, 0, 1, 5, 10, null, null, new LaserBlade(), null, null);
        PlayerAttack mockPlayerAttack = new PlayerAttack(mockCharacter, jetBat);
        jetBat.defend(mockPlayerAttack);

        if (!jetBat.getAbility().wasUsed()) assertTrue(mockPlayerAttack.getTotalAttack() != 0);
    }
}
