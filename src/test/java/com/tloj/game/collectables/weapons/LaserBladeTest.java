package com.tloj.game.collectables.weapons;

import org.junit.jupiter.api.Test;
import static org.junit.jupiter.api.Assertions.*;

import com.tloj.game.entities.characters.DataThief;
import com.tloj.game.entities.mobs.CyberGoblin;
import com.tloj.game.game.Coordinates;
import com.tloj.game.game.Dice;
import com.tloj.game.game.PlayerAttack;
import com.tloj.game.entities.Character;

public class LaserBladeTest {
    @Test
    public void attackTest() {
        Dice.setSeed(1);

        LaserBlade laserBlade = new LaserBlade();
        
        Character mockCharacter = new DataThief(20, 4, 4, 10, 0, 1, 5, 10, null, null, laserBlade, null, null);
        CyberGoblin mockCyberGoblin = new CyberGoblin(new Coordinates(0, 0), 1);
        PlayerAttack mockPlayerAttack = new PlayerAttack(mockCharacter, mockCyberGoblin);
        //TODO
        int calculatedDamage = 7;
        assertTrue(mockPlayerAttack.getWeaponRoll() == 6);
        assertEquals(mockPlayerAttack.getTotalDamage(), calculatedDamage);

    }

}
