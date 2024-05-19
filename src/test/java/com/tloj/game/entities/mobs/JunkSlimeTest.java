package com.tloj.game.entities.mobs;

import static org.junit.jupiter.api.Assertions.*;
import org.junit.jupiter.api.Test;

import com.tloj.game.entities.characters.BasePlayer;
import com.tloj.game.entities.Character;
import com.tloj.game.game.PlayerAttack;
import com.tloj.game.utilities.Coordinates;

public class JunkSlimeTest {
    
    @Test
    void testConstructorLevelTwo() {
        JunkSlime junkSlime = new JunkSlime(new Coordinates(0, 0), 2);
        
        assertEquals(32, junkSlime.getHp());
        assertEquals(6, junkSlime.getAtk());
        assertEquals(6, junkSlime.getDef());
        assertEquals(6, junkSlime.getCurrentFightAtk());
        assertEquals(6, junkSlime.getCurrentFightDef());
        assertEquals(4, junkSlime.getDiceFaces());
        assertEquals(12, junkSlime.dropXp());
        assertEquals(2, junkSlime.getMoneyDrop());
    }

    @Test
    void testConstructorLevelThree() {
        JunkSlime junkSlime = new JunkSlime(new Coordinates(0, 0), 3);
        
        assertEquals(48, junkSlime.getHp());
        assertEquals(9, junkSlime.getAtk());
        assertEquals(9, junkSlime.getDef());
        assertEquals(9, junkSlime.getCurrentFightAtk());
        assertEquals(9, junkSlime.getCurrentFightDef());
        assertEquals(4, junkSlime.getDiceFaces());
        assertEquals(18, junkSlime.dropXp());
        assertEquals(2, junkSlime.getMoneyDrop());
    }

    @Test
    void testConstructorLevelGreaterThanThree() {
        JunkSlime junkSlime = new JunkSlime(new Coordinates(0, 0), 4);
        
        assertEquals(64, junkSlime.getHp());
        assertEquals(11, junkSlime.getAtk());
        assertEquals(13, junkSlime.getDef());
        assertEquals(11, junkSlime.getCurrentFightAtk());
        assertEquals(13, junkSlime.getCurrentFightDef());
        assertEquals(4, junkSlime.getDiceFaces());
        assertEquals(24, junkSlime.dropXp());
        assertEquals(2, junkSlime.getMoneyDrop());
    }    

    @Test
    void testSkillUsed() {
        JunkSlime junkSlime = new JunkSlime(new Coordinates(0, 0), 1);
        Character mockCharacter = new BasePlayer(20, 4, 4, 10, 0, 1, 5, 10, null, null, null, null, null);
        PlayerAttack mockPlayerAttack = new PlayerAttack(mockCharacter, junkSlime);
        junkSlime.defend(mockPlayerAttack);

        if (junkSlime.getAbility().wasUsed()) assertTrue(mockCharacter.getHp() < 20);
    }

    @Test
    void testSkillNotUsed() {
        JunkSlime junkSlime = new JunkSlime(new Coordinates(0, 0), 1);
        Character mockCharacter = new BasePlayer(20, 4, 4, 10, 0, 1, 5, 10, null, null, null, null, null);
        PlayerAttack mockPlayerAttack = new PlayerAttack(mockCharacter, junkSlime);
        junkSlime.defend(mockPlayerAttack);

        if (!junkSlime.getAbility().wasUsed()) assertTrue(mockCharacter.getHp() == 20);
    }

}
