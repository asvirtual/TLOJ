package com.tloj.game.collectables.weapon;

import org.junit.jupiter.api.Test;

import com.tloj.game.collectables.weapons.NanoDirk;
import com.tloj.game.entities.characters.DataThief;
import com.tloj.game.entities.mobs.CyberGoblin;
import com.tloj.game.game.Controller;
import com.tloj.game.game.PlayerAttack;
import com.tloj.game.utilities.Coordinates;
import com.tloj.game.entities.Character;
import com.tloj.game.utilities.Dice;
import java.io.ByteArrayInputStream;


import static org.junit.jupiter.api.Assertions.*;



public class NanoDirkTest {

    @Test
    public void usedEffectTest() {

        String input = "\n\n\n\n\n\n\n\n\n\n\n\n";
        ByteArrayInputStream testIn = new ByteArrayInputStream(input.getBytes());
        System.setIn(testIn);
        Controller mockController= Controller.getInstance();
     
    
        Dice.setSeed(1);
        NanoDirk nanoDirk = new NanoDirk();
        Character mockCharacter = new DataThief(20, 4, 4, 10, 0, 1, 5, 10, null, null, nanoDirk, null, null);
        CyberGoblin mockCyberGoblin = new CyberGoblin(new Coordinates(0, 0), 1);
        PlayerAttack mockPlayerAttack = new PlayerAttack(mockCharacter, mockCyberGoblin);

        while(!nanoDirk.getEffect().wasUsed()){
            mockCharacter.attack(mockCyberGoblin);
            if(nanoDirk.getEffect().wasUsed()) assertTrue(mockPlayerAttack.getWeaponRoll() > 6);
            else {
                mockCyberGoblin = new CyberGoblin(new Coordinates(0, 0), 1);
                mockPlayerAttack = new PlayerAttack(mockCharacter, mockCyberGoblin);
            }
        }
    }

    @Test
    public void notUsedEffectTest() {
        NanoDirk nanoDirk = new NanoDirk();
        Character mockCharacter = new DataThief(20, 4, 4, 10, 0, 1, 5, 10, null, null, nanoDirk, null, null);
        CyberGoblin mockCyberGoblin = new CyberGoblin(new Coordinates(0, 0), 1);
        PlayerAttack mockPlayerAttack = new PlayerAttack(mockCharacter, mockCyberGoblin);
    
        do{
            mockCharacter.attack(mockCyberGoblin);
            if(!nanoDirk.getEffect().wasUsed()) assertTrue(mockPlayerAttack.getWeaponRoll() <= 6);
            else mockCyberGoblin = new CyberGoblin(new Coordinates(0, 0), 1);
        
        }while(nanoDirk.getEffect().wasUsed());
    }
}



