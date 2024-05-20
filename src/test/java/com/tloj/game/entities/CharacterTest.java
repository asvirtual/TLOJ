package com.tloj.game.entities;

import static org.junit.jupiter.api.Assertions.*;

import org.junit.jupiter.api.Test;

import com.tloj.game.entities.characters.BasePlayer;
import com.tloj.game.utilities.Dice;

public class CharacterTest {

    @Test
    public void levelUpTest(){
        
        Dice.setSeed(1);
        Character mockCharacter = new BasePlayer(null);
      
        for(int i = 0; i < 10; i++){
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
    public void restoreOnLevelUpTest(){

        Dice.setSeed(1);
        Character mockCharacter = new BasePlayer(null);

        mockCharacter.setHp(1);
        mockCharacter.setMana(1);
      
        int startHp = mockCharacter.getHp();
        int startMana = mockCharacter.getMana();
        
        mockCharacter.addXp(Character.REQ_XP_BASE * mockCharacter.getLvl());

        int endHp = mockCharacter.getHp();
        int endMana = mockCharacter.getMana();

        assertTrue(startHp != endHp && endHp == mockCharacter.getMaxHp());
        assertTrue(startMana != endMana && endMana == mockCharacter.getMaxMana());
    }
}
