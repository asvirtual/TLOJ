package com.tloj.game.entities.mobs;

import static org.junit.jupiter.api.Assertions.*;
import org.junit.jupiter.api.Test;

import com.tloj.game.entities.characters.BasePlayer;
import com.tloj.game.entities.Character;
import com.tloj.game.game.PlayerAttack;
import com.tloj.game.utilities.Coordinates;


/**
 * {@code JunkSlimeTest} is a test class for the {@link JunkSlime} entity.<br>
 * It tests the constructors for the level up mechanic.<br>
 * It tests the ability of the JunkSlime to poison the attacker.<br>
 * It also tests wether the ability triggers or not.<br>
 */

public class JunkSlimeTest {
    private static final int MOCK_CHARACTER_MAX_HP = 20;
    
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
        
        assertEquals(16 * junkSlime.getLvl(), junkSlime.getHp());
        assertEquals(9 + 2  * (junkSlime.getLvl() - 3), junkSlime.getAtk());
        assertEquals(9 + 4 * (junkSlime.getLvl() - 3), junkSlime.getDef());
        assertEquals(9 + 2 * (junkSlime.getLvl() - 3), junkSlime.getCurrentFightAtk());
        assertEquals(9 + 4 * (junkSlime.getLvl() - 3), junkSlime.getCurrentFightDef());
        assertEquals(4, junkSlime.getDiceFaces());
        assertEquals(6 * junkSlime.getLvl(), junkSlime.dropXp());
        assertEquals(2, junkSlime.getMoneyDrop());
    }    

    @Test
    void testSkillUsed() {
        JunkSlime junkSlime = new JunkSlime(new Coordinates(0, 0), 1);
        Character mockCharacter = new BasePlayer(MOCK_CHARACTER_MAX_HP, 4, 4, 10, 0, 1, 5, 10, null, null, null, null, null);
        PlayerAttack mockPlayerAttack = new PlayerAttack(mockCharacter, junkSlime);
        
        while (!junkSlime.getAbility().wasUsed()) {
            junkSlime.defend(mockPlayerAttack);
            if (junkSlime.getAbility().wasUsed()){
                assertTrue(mockCharacter.getHp() < MOCK_CHARACTER_MAX_HP);
                return;
            }
            else {
                junkSlime = new JunkSlime(new Coordinates(0, 0), 1);
                mockCharacter.setHp(MOCK_CHARACTER_MAX_HP);
                mockPlayerAttack.setTarget(junkSlime);
            }
        }
    }

    @Test
    void testSkillNotUsed() {
        JunkSlime junkSlime = new JunkSlime(new Coordinates(0, 0), 1);
        Character mockCharacter = new BasePlayer(MOCK_CHARACTER_MAX_HP, 4, 4, 10, 0, 1, 5, 10, null, null, null, null, null);
        PlayerAttack mockPlayerAttack = new PlayerAttack(mockCharacter, junkSlime);

        do {
            junkSlime.defend(mockPlayerAttack);
            if (!junkSlime.getAbility().wasUsed()) {
                assertTrue(mockCharacter.getHp() == MOCK_CHARACTER_MAX_HP);
                return;
            }
            else {
                junkSlime = new JunkSlime(new Coordinates(0, 0), 1);
                mockCharacter.setHp(MOCK_CHARACTER_MAX_HP);
                mockPlayerAttack.setTarget(junkSlime);
            }
        } while (!junkSlime.getAbility().wasUsed());
    }
}