package com.tloj.game.collectables.weapons;

import org.junit.jupiter.api.Test;
import static org.junit.jupiter.api.Assertions.*;

import com.tloj.game.entities.characters.DataThief;
import com.tloj.game.entities.mobs.CyberGoblin;
import com.tloj.game.game.Coordinates;
import com.tloj.game.game.Dice;
import com.tloj.game.game.PlayerAttack;
import com.tloj.game.entities.Character;

public class PulseStaffTest {

    @Test
    void effectUsedTest(){
        Dice.setSeed(1);

        PulseStaff pulseStaff = new PulseStaff();
        Character mockCharacter = new DataThief(20, 4, 4, 10, 0, 1, 5, 10, null, null, pulseStaff, null, null);
        CyberGoblin mockCyberGoblin = new CyberGoblin(new Coordinates(0, 0), 1);
        PlayerAttack mockPlayerAttack = new PlayerAttack(mockCharacter, mockCyberGoblin);

        int initialMana = mockCharacter.getMana();
        pulseStaff.modifyAttack(mockPlayerAttack);
        assertTrue(mockPlayerAttack.getWeaponRoll() >= 5);
        //the ability costs 3 mana to use
        assertEquals(initialMana - 3, mockCharacter.getMana());
        }

    @Test
    void noManaTest() {
        Dice.setSeed(1);

        PulseStaff pulseStaff = new PulseStaff();
        Character mockCharacter = new DataThief(20, 4, 4, 0, 0, 1, 5, 10, null, null, pulseStaff, null, null);
        CyberGoblin mockCyberGoblin = new CyberGoblin(new Coordinates(0, 0), 1);
        PlayerAttack mockPlayerAttack = new PlayerAttack(mockCharacter, mockCyberGoblin);

        pulseStaff.modifyAttack(mockPlayerAttack);
        assertTrue(mockPlayerAttack.getWeaponRoll() == 0);
    }
}
