package com.tloj.game.entities.mobs;

import org.junit.jupiter.api.Test;
import static org.junit.jupiter.api.Assertions.*;

import com.tloj.game.utilities.Coordinates;


public class CyberGoblinTest {
    @Test
    void testConstructor() {
        CyberGoblin cyberGoblin = new CyberGoblin(new Coordinates(0, 0), 1);
        
        assertEquals(10, cyberGoblin.getHp());
        assertEquals(4, cyberGoblin.getAtk());
        assertEquals(2, cyberGoblin.getDef());
        assertEquals(4, cyberGoblin.getCurrentFightAtk());
        assertEquals(2, cyberGoblin.getCurrentFightDef());
        assertEquals(7, cyberGoblin.getDiceFaces());
        assertEquals(5, cyberGoblin.getXpDrop());
        assertEquals(3, cyberGoblin.getMoneyDrop());
    }
}
