package com.tloj.game.collectables.weapons;

import org.junit.jupiter.api.Test;
import static org.junit.jupiter.api.Assertions.*;

import com.tloj.game.entities.characters.DataThief;
import com.tloj.game.entities.mobs.CyberGoblin;
import com.tloj.game.game.Coordinates;
import com.tloj.game.game.Dice;
import com.tloj.game.game.PlayerAttack;
import com.tloj.game.entities.Character;


public class NanoDirkTest {
    @Test
    public void usedEffectTest() {    
        Dice.setSeed(1);

        NanoDirk nanoDirk = new NanoDirk();
        Character mockCharacter = new DataThief(20, 4, 4, 10, 0, 1, 5, 10, null, null, nanoDirk, null, null);
        CyberGoblin mockCyberGoblin = new CyberGoblin(new Coordinates(0, 0), 1);
        PlayerAttack mockPlayerAttack = new PlayerAttack(mockCharacter, mockCyberGoblin);

        while (!nanoDirk.getEffect().wasUsed()) {
            nanoDirk.modifyAttack(mockPlayerAttack);
            if (nanoDirk.getEffect().wasUsed()) 
                assertTrue(mockPlayerAttack.getWeaponRoll() > 6);
        }
    }

    @Test
    public void notUsedEffectTest() {
        Dice.setSeed(1);

        NanoDirk nanoDirk = new NanoDirk();
        Character mockCharacter = new DataThief(20, 4, 4, 10, 0, 1, 5, 10, null, null, nanoDirk, null, null);
        CyberGoblin mockCyberGoblin = new CyberGoblin(new Coordinates(0, 0), 1);
        PlayerAttack mockPlayerAttack = new PlayerAttack(mockCharacter, mockCyberGoblin);
    
        do {
            nanoDirk.modifyAttack(mockPlayerAttack);
            if (!nanoDirk.getEffect().wasUsed()) 
                assertTrue(mockPlayerAttack.getWeaponRoll() <= 6);
        } while(nanoDirk.getEffect().wasUsed());
    }
}



