package com.tloj.game.skill;

import static org.junit.jupiter.api.Assertions.*;

import org.junit.jupiter.api.Test;

import com.tloj.game.entities.characters.NeoSamurai;
import com.tloj.game.entities.Character;
import com.tloj.game.entities.mobs.CyberGoblin;
import com.tloj.game.game.Controller;
import com.tloj.game.game.PlayerAttack;
import com.tloj.game.utilities.Coordinates;
import com.tloj.game.utilities.Dice;
import com.tloj.game.collectables.weapons.CyberKatana;
import com.tloj.game.rooms.*;


public class DaburuTest {
    
    @Test
    void daburuTest(){

        Dice.setSeed(1);
        Controller.getInstance();
        CyberGoblin mockCyberGoblin = new CyberGoblin(new Coordinates(0, 0), 1);
        Room mockRoom = new HostileRoom(new Coordinates(0, 0), mockCyberGoblin);
        Character mockCharacter = new NeoSamurai(20, 4, 4, 10, 0, 1, 5, 10, null, mockRoom, new CyberKatana(), null, null);

        PlayerAttack mockPlayerAttack = new PlayerAttack(mockCharacter, mockCyberGoblin);
        
        mockPlayerAttack.setWeaponRoll(5);
        int initalDamage = mockPlayerAttack.getTotalAttack();
        
        mockCharacter.getSkill().activate();
        mockCharacter.getSkill().execute(mockPlayerAttack);

        int finalDamage = mockPlayerAttack.getTotalAttack();
    
        assertEquals((initalDamage * 2), finalDamage );
        assertEquals(mockCharacter.getMaxMana() - 10, mockCharacter.getMana());
    }
    
    @Test
    void daburuNoManaTest(){

        Dice.setSeed(1);
        Controller.getInstance();
        CyberGoblin mockCyberGoblin = new CyberGoblin(new Coordinates(0, 0), 1);
        Room mockRoom = new HostileRoom(new Coordinates(0, 0), mockCyberGoblin);
        Character mockCharacter = new NeoSamurai(20, 4, 4, 4, 0, 1, 5, 10, null, mockRoom, new CyberKatana(), null, null);

        PlayerAttack mockPlayerAttack = new PlayerAttack(mockCharacter, mockCyberGoblin);
        
        mockPlayerAttack.setWeaponRoll(5);
        int initalDamage = mockPlayerAttack.getTotalAttack();

        
        mockCharacter.getSkill().activate();
        mockCharacter.getSkill().execute(mockPlayerAttack);

        int finalDamage = mockPlayerAttack.getTotalAttack();
    
        assertEquals(initalDamage, finalDamage);
        assertEquals(mockCharacter.getMaxMana(), mockCharacter.getMana());
    }
}
