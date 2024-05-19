package com.tloj.game.entities.mobs;

import static org.junit.jupiter.api.Assertions.*;

import java.util.Random;

import org.junit.jupiter.api.Test;

import com.tloj.game.entities.characters.BasePlayer;
import com.tloj.game.collectables.weapons.LaserBlade;
import com.tloj.game.entities.Character;
import com.tloj.game.utilities.Coordinates;
import com.tloj.game.entities.mobs.Glitched;
import com.tloj.game.game.PlayerAttack;


public class GlitchedTest {
    @Test
    void testConstructor() {
        Glitched glitched = new Glitched(new Coordinates(0, 0), 1);
        
        assertEquals(70, glitched.getHp());
        assertEquals(10, glitched.getAtk());
        assertEquals(5, glitched.getDef());
        assertEquals(10, glitched.getCurrentFightAtk());
        assertEquals(5, glitched.getCurrentFightDef());
        assertEquals(6, glitched.getDiceFaces());
        assertEquals(6, glitched.dropXp());
        assertEquals(6, glitched.getMoneyDrop());
    }

    @Test
    void testTeleport(){
        Glitched glitched = new Glitched(new Coordinates(0, 0), 1);
        Character mockCharacter = new BasePlayer(20, 4, 4, 10, 0, 1, 5, 10, null, null, new LaserBlade(), null, null);
        Coordinates startCoordinates = glitched.getCoordinates();
        glitched.attack(mockCharacter);
        Coordinates endCoordinates = glitched.getCoordinates();

        assertTrue(() -> startCoordinates.equals(endCoordinates) == false);
    }
    
}
