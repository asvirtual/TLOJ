package com.tloj.game.skills;

import static org.junit.jupiter.api.Assertions.*;
import org.junit.jupiter.api.Test;

import com.tloj.game.entities.characters.Hacker;
import com.tloj.game.entities.Character;
import com.tloj.game.entities.mobs.CyberGoblin;
import com.tloj.game.game.Controller;
import com.tloj.game.game.PlayerAttack;
import com.tloj.game.utilities.Coordinates;
import com.tloj.game.utilities.Dice;
import com.tloj.game.collectables.weapons.PulseStaff;
import com.tloj.game.rooms.*;


/**
 * {@code CheatEngineTest} is a test class for the {@link CheatEngine} skill.<br>
 * It tests its effect, if it consumes mana and that you cant use it with no mana.<br>
 */

public class CheatEngineTest {
    
    @Test
    public void cheatEngineTest() {
        Dice.setSeed(1);
        Controller.getInstance();
        CyberGoblin mockCyberGoblin = new CyberGoblin(new Coordinates(0, 0), 1);
        Room mockRoom = new HostileRoom(new Coordinates(0, 0), mockCyberGoblin);
        Character mockCharacter = new Hacker(20, 4, 4, 10, 0, 1, 5, 10, null, mockRoom, new PulseStaff(), null, null);

        PlayerAttack mockPlayerAttack = new PlayerAttack(mockCharacter, mockCyberGoblin);
        
        mockCharacter.getSkill().activate();
        mockCharacter.getSkill().execute(mockPlayerAttack);
        int finalDamage = mockPlayerAttack.getTotalAttack();
    
        assertEquals(mockCharacter.getWeapon().getDiceMax() + mockCharacter.getAtk(), finalDamage);
        assertEquals(mockCharacter.getMaxMana() - 5, mockCharacter.getMana());
    }

    @Test
    public void noManaCheatEngineTest() {
        Dice.setSeed(1);
        Controller.getInstance();
        CyberGoblin mockCyberGoblin = new CyberGoblin(new Coordinates(0, 0), 1);
        Room mockRoom = new HostileRoom(new Coordinates(0, 0), mockCyberGoblin);
        Character mockCharacter = new Hacker(20, 4, 4, 4, 0, 1, 5, 10, null, mockRoom, new PulseStaff(), null, null);

        PlayerAttack mockPlayerAttack = new PlayerAttack(mockCharacter, mockCyberGoblin);
        
        int initalDamage = mockPlayerAttack.getTotalAttack();
        mockCharacter.getSkill().activate();
        mockCharacter.getSkill().execute(mockPlayerAttack);
        int finalDamage = mockPlayerAttack.getTotalAttack();
    
        assertEquals(initalDamage, finalDamage);
        assertEquals(mockCharacter.getMaxMana(), mockCharacter.getMana());
    }
}