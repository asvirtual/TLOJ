package com.tloj.game.collectables.weapons;

import org.junit.jupiter.api.Test;
import static org.junit.jupiter.api.Assertions.*;

import org.fusesource.jansi.internal.Kernel32.INPUT_RECORD;

import com.tloj.game.entities.characters.BasePlayer;
import com.tloj.game.entities.mobs.CyberGoblin;
import com.tloj.game.game.Coordinates;
import com.tloj.game.game.Dice;
import com.tloj.game.game.PlayerAttack;
import com.tloj.game.entities.Character;

public class UpgradedWeaponTest {
    @Test
    void upgradedWeaponTest() {

        Dice.setSeed(1);

        LaserBlade upgradedWeapon = new LaserBlade();

        Character mockCharacter = new BasePlayer(20, 4, 4, 10, 0, 1, 5, 10, null, null, upgradedWeapon, null, null);
        CyberGoblin mockCyberGoblin = new CyberGoblin(new Coordinates(0, 0), 1);
        PlayerAttack mockPlayerAttack = new PlayerAttack(mockCharacter, mockCyberGoblin);

        upgradedWeapon.upgrade(5);
        mockCharacter.getWeapon().modifyAttack(mockPlayerAttack);
        int weaponRoll = mockPlayerAttack.getWeaponRoll();

        assertTrue(weaponRoll >= 6);
    }
}
