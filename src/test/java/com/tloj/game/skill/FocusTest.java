package com.tloj.game.skill;

import static org.junit.jupiter.api.Assertions.*;

import org.junit.jupiter.api.Test;

import com.tloj.game.entities.characters.BasePlayer;
import com.tloj.game.entities.Character;
import com.tloj.game.entities.mobs.CyberGoblin;
import com.tloj.game.game.Controller;
import com.tloj.game.game.PlayerAttack;
import com.tloj.game.rooms.HostileRoom;
import com.tloj.game.utilities.Coordinates;
import com.tloj.game.utilities.Dice;
import com.tloj.game.collectables.weapons.LaserBlade;
import com.tloj.game.rooms.*;


public class FocusTest {
    
    @Test
    void focusTest() {
        
        Dice.setSeed(1);
        Controller.getInstance();
        CyberGoblin mockCyberGoblin = new CyberGoblin(new Coordinates(0, 0), 1);
        Room mockRoom = new HostileRoom(new Coordinates(0, 0), mockCyberGoblin);
        Character mockCharacter = new BasePlayer(20, 4, 4, 5, 0, 1, 5, 10, null, mockRoom, new LaserBlade(), null, null);
   
        PlayerAttack mockPlayerAttack = new PlayerAttack(mockCharacter, mockCyberGoblin);
        
        mockPlayerAttack.setWeaponRoll(5);
        int initialAttack = mockPlayerAttack.getTotalDamage();

        
        mockCharacter.getSkill().activate();
        mockCharacter.getSkill().execute(mockPlayerAttack);

        int finalAttack = mockPlayerAttack.getTotalDamage();
    
        assertEquals(initialAttack + 3, finalAttack);
        assertEquals(mockCharacter.getMaxMana() - 5, mockCharacter.getMana());
    }

    @Test
    void focusNoManaTest(){

        Dice.setSeed(1);
        Controller.getInstance();
        CyberGoblin mockCyberGoblin = new CyberGoblin(new Coordinates(0, 0), 1);
        Room mockRoom = new HostileRoom(new Coordinates(0, 0), mockCyberGoblin);
        Character mockCharacter = new BasePlayer(20, 4, 4, 4, 0, 1, 5, 10, null, mockRoom, new LaserBlade(), null, null);

        PlayerAttack mockPlayerAttack = new PlayerAttack(mockCharacter, mockCyberGoblin);
        
        mockPlayerAttack.setWeaponRoll(5);
        int initialAttack = mockPlayerAttack.getTotalDamage();

        
        mockCharacter.getSkill().activate();
        mockCharacter.getSkill().execute(mockPlayerAttack);

        int finalAttack = mockPlayerAttack.getTotalDamage();
    
        assertEquals(initialAttack, finalAttack);
        assertEquals(mockCharacter.getMaxMana(), mockCharacter.getMana());
    }
}
