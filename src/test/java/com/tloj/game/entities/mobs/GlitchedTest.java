package com.tloj.game.entities.mobs;

import static org.junit.jupiter.api.Assertions.*;
import org.junit.jupiter.api.Test;

import com.tloj.game.utilities.Coordinates;

public class GlitchedTest {
    @Test
    void testConstructor() {
        Glitched glitched = new Glitched(new Coordinates(0, 0), 1);
        
        assertEquals(70, glitched.getHp());
        assertEquals(10, glitched.getAtk());
        assertEquals(6, glitched.getDef());
        assertEquals(10, glitched.getCurrentFightAtk());
        assertEquals(6, glitched.getCurrentFightDef());
        assertEquals(6, glitched.getDiceFaces());
        assertEquals(16, glitched.dropXp());
        assertEquals(6, glitched.getMoneyDrop());
    }

    @Test
    void testTeleport() {
    // assertTrue(false);
        // Glitched glitched = new Glitched(new Coordinates(0, 0), 1);
        // Character mockCharacter = new BasePlayer(20, 4, 4, 10, 0, 1, 5, 10, null, null, new LaserBlade(), null, null);
        // Coordinates startCoordinates = glitched.getPosition();
        // glitched.attack(mockCharacter);
        // Coordinates endCoordinates = glitched.getPosition();

        // assertTrue(() -> startCoordinates.equals(endCoordinates) == false);
    }
    
}
