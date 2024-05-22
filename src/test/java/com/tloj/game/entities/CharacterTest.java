package com.tloj.game.entities;

import static org.junit.jupiter.api.Assertions.*;

import java.io.ByteArrayInputStream;
import java.io.InputStream;

import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import com.tloj.game.entities.characters.BasePlayer;
import com.tloj.game.game.Controller;
import com.tloj.game.utilities.Dice;


/**
 * {@code CharacterTest} is a test class for the generic {@link Character} entity.<br>
 * It tests the level up mechanism and the restoration of health and mana on level up.<br>
 */

public class CharacterTest {
    private final InputStream originalSystemIn = System.in;
   
    @BeforeEach
    public void setUpInput() {
        try {
            Thread.sleep(100); 
            
            String input = "";
            for (int i = 0; i < 10000; i++) {
                input += "\n";
            }

            System.setIn(new ByteArrayInputStream(input.getBytes()));

            Dice.setSeed(1);
            Controller.getInstance();
        }
        catch(InterruptedException e){
            e.printStackTrace();
        }
    }


    @AfterEach
    public void restoreSystemIn() {
        System.setIn(originalSystemIn);
    }

    @Test
    public void levelUpTest() {
        Character mockCharacter = new BasePlayer(null);
      
        for (int i = 0; i < 10; i++) {
            int startLvl = mockCharacter.getLvl();
            int startHp = mockCharacter.getHp();
            int startMana = mockCharacter.getMana();
            int startAtk = mockCharacter.getAtk();
            int startDef = mockCharacter.getDef();
            int startXp = Character.REQ_XP_BASE;
            
            mockCharacter.addXp(Character.REQ_XP_BASE * mockCharacter.getLvl());

            int endLvl = mockCharacter.getLvl();
            int endHp = mockCharacter.getHp();
            int endMana = mockCharacter.getMana();
            int endAtk = mockCharacter.getAtk();
            int endDef = mockCharacter.getDef();
            int endXp = mockCharacter.requiredXp;

            assertEquals(startLvl + 1, endLvl);
            assertTrue(startHp < endHp && endHp <= startHp + 5);
            assertTrue(startMana < endMana && endMana <= startMana + 5);
            assertTrue(startAtk < endAtk && endAtk <= startAtk + 3);
            assertTrue(startDef < endDef && endDef <= startDef + 3);
            assertEquals(startXp * mockCharacter.getLvl(), endXp);
        }
    }

    @Test
    public void restoreOnLevelUpTest() {
        Character mockCharacter = new BasePlayer(null);
      
        int startHp = 1;
        int startMana = 1;

        mockCharacter.setHp(startHp);
        mockCharacter.setMana(startMana);
        
        mockCharacter.addXp(Character.REQ_XP_BASE * mockCharacter.getLvl());

        int endHp = mockCharacter.getHp();
        int endMana = mockCharacter.getMana();

        assertEquals(endHp, mockCharacter.getMaxHp());
        assertEquals(endMana, mockCharacter.getMaxMana());
    }
}
