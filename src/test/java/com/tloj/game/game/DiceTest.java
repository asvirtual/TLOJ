package com.tloj.game.game;

import static org.junit.jupiter.api.Assertions.*;

import org.junit.jupiter.api.Test;

public class DiceTest {
    
    static void setSeed(long seed) {
        Dice.setSeed(seed);
    }

    @Test
    void diceRollTest(){
        Dice dice = new Dice(6);
        
        int maxRoll = dice.getMax();
        int minRoll = dice.getMin();
        setSeed(1);
        int roll = dice.roll();
        
        assertEquals(6, maxRoll);
        assertEquals(1, minRoll);
        assertTrue(1 <= roll && roll <= 6);
    } 
    
}
