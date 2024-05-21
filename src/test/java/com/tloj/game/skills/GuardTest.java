package com.tloj.game.skills;

import static org.junit.jupiter.api.Assertions.*;

import org.junit.jupiter.api.Test;

import com.tloj.game.entities.characters.MechaKnight;
import com.tloj.game.entities.Character;
import com.tloj.game.entities.mobs.CyberGoblin;
import com.tloj.game.game.Controller;
import com.tloj.game.game.MobAttack;
import com.tloj.game.utilities.Coordinates;
import com.tloj.game.utilities.Dice;
import com.tloj.game.collectables.weapons.PlasmaGreatsword;
import com.tloj.game.rooms.*;


public class GuardTest {
    @Test
    public void guardTest(){
        Dice.setSeed(1);
        Controller.getInstance();
        CyberGoblin mockCyberGoblin = new CyberGoblin(new Coordinates(0, 0), 1);
        Room mockRoom = new HostileRoom(new Coordinates(0, 0), mockCyberGoblin);
        Character mockCharacter = new MechaKnight(20, 4, 0, 5, 0, 1, 5, 10, null, mockRoom, new PlasmaGreatsword(), null, null);

        MobAttack mockMobAttack = new MobAttack(mockCyberGoblin, mockCharacter);

        mockMobAttack.setDiceRoll(2);
        int initalDamage = mockMobAttack.getTotalAttack();

        mockCharacter.getSkill().activate();
        mockCharacter.getSkill().execute(mockMobAttack);

        int finalDamage = mockMobAttack.getTotalAttack();

        assertEquals((initalDamage / 2), finalDamage);
        assertEquals(mockCharacter.getMaxMana() - 5, mockCharacter.getMana());
    }

    @Test
    public void noManaGuardTest() {
        Dice.setSeed(1);
        Controller.getInstance();
        CyberGoblin mockCyberGoblin = new CyberGoblin(new Coordinates(0, 0), 1);
        Room mockRoom = new HostileRoom(new Coordinates(0, 0), mockCyberGoblin);
        Character mockCharacter = new MechaKnight(20, 4, 0, 4, 0, 1, 5, 10, null, mockRoom, new PlasmaGreatsword(), null, null);

        MobAttack mockMobAttack = new MobAttack(mockCyberGoblin, mockCharacter);

        mockMobAttack.setDiceRoll(2);
        int initalDamage = mockMobAttack.getTotalAttack();

        mockCharacter.getSkill().activate();
        mockCharacter.getSkill().execute(mockMobAttack);

        int finalDamage = mockMobAttack.getTotalAttack();

        assertEquals(initalDamage, finalDamage);
        assertEquals(mockCharacter.getMaxMana(), mockCharacter.getMana());
    }
}
