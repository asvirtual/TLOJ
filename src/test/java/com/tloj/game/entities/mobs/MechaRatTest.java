package com.tloj.game.entities.mobs;

import org.junit.jupiter.api.Test;
import static org.junit.jupiter.api.Assertions.*;

import java.io.InputStream;

import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;

import com.tloj.game.entities.characters.BasePlayer;
import com.tloj.game.game.Controller;
import com.tloj.game.game.PlayerAttack;
import com.tloj.game.collectables.items.HealthPotion;
import com.tloj.game.entities.Character;
import com.tloj.game.game.Coordinates;
import com.tloj.game.game.Dice;
import com.tloj.game.game.ControllerHandler;


/**
 * {@code MechaRatTest} is a test class for the {@link MechaRat} entity.<br>
 * It tests the constructors for the level up mechanic.<br>
 * It tests the ability of the MechaRat to destroy a random item from the player inventory.<br>
 * It also tests wether the ability triggers or not.<br>
 */

public class MechaRatTest {
    private final InputStream originalSystemIn = System.in;
   
    @BeforeEach
    public void setUpInput() {
        
            Dice.setSeed(1);
            ControllerHandler.deleteController();
            Controller.getInstance();
    }


    @AfterEach
    public void restoreSystemIn() {
        ControllerHandler.resetInput(originalSystemIn);
    }
 
    @Test
     void testConstructorLevelTwo() {
        MechaRat mechaRat = new MechaRat(new Coordinates(0, 0), 2);
        
        assertEquals(16, mechaRat.getHp());
        assertEquals(6, mechaRat.getAtk());
        assertEquals(4, mechaRat.getDef());
        assertEquals(6, mechaRat.getCurrentFightAtk());
        assertEquals(4, mechaRat.getCurrentFightDef());
        assertEquals(8, mechaRat.getDiceFaces());
        assertEquals(10, mechaRat.dropXp());
        assertEquals(2, mechaRat.getMoneyDrop());
    }

    @Test
    void testConstructorLevelThree() {
        MechaRat mechaRat = new MechaRat(new Coordinates(0, 0), 3);
        
        assertEquals(24, mechaRat.getHp());
        assertEquals(12, mechaRat.getAtk());
        assertEquals(6, mechaRat.getDef());
        assertEquals(12, mechaRat.getCurrentFightAtk());
        assertEquals(6, mechaRat.getCurrentFightDef());
        assertEquals(8, mechaRat.getDiceFaces());
        assertEquals(15, mechaRat.dropXp());
        assertEquals(2, mechaRat.getMoneyDrop());
    }

    @Test
    void testConstructorLevelGreaterThanThree() {
        MechaRat mechaRat = new MechaRat(new Coordinates(0, 0), 4);
        
        assertEquals(8 * mechaRat.getLvl(), mechaRat.getHp());
        assertEquals(12 + 3 * (mechaRat.getLvl() - 3), mechaRat.getAtk());
        assertEquals(6 + mechaRat.getLvl() - 3, mechaRat.getDef());
        assertEquals(12 + 3 * (mechaRat.getLvl() - 3), mechaRat.getCurrentFightAtk());
        assertEquals(6 + mechaRat.getLvl() - 3, mechaRat.getCurrentFightDef());
        assertEquals(8, mechaRat.getDiceFaces());
        assertEquals(5 * mechaRat.getLvl(), mechaRat.dropXp());
        assertEquals(2, mechaRat.getMoneyDrop());
    }    

    @Test
    void testSkillUsed() {
        MechaRat mechaRat = new MechaRat(new Coordinates(0, 0), 1);
        Character mockCharacter = new BasePlayer(null);
        HealthPotion healthPotion = new HealthPotion();

        PlayerAttack mockPlayerAttack = new PlayerAttack(mockCharacter, mechaRat);
        while (!mechaRat.getAbility().wasUsed()) {
            ControllerHandler.deleteController();
            ControllerHandler.setInput("\n");
            Controller.getInstance();

            mechaRat.defend(mockPlayerAttack);
            if (mechaRat.getAbility().wasUsed()) {
                assertNull(mockCharacter.getInventoryItem(healthPotion));
            } else {
                mechaRat = new MechaRat(new Coordinates(0, 0), 1);
                mockPlayerAttack = new PlayerAttack(mockCharacter, mechaRat);
            }    
        }
    }

    @Test
    void testSkillNotUsed() {
        MechaRat mechaRat = new MechaRat(new Coordinates(0, 0), 1);
        Character mockCharacter = new BasePlayer(null);
        HealthPotion healthPotion = new HealthPotion();
        mockCharacter.addInventoryItem(healthPotion);

        PlayerAttack mockPlayerAttack = new PlayerAttack(mockCharacter, mechaRat);
        mechaRat.defend(mockPlayerAttack);
        do {
            ControllerHandler.deleteController();
            ControllerHandler.setInput("\n");
            Controller.getInstance();

            if (!mechaRat.getAbility().wasUsed()) assertEquals(healthPotion, mockCharacter.getInventoryItem(healthPotion));
            else {
                mechaRat = new MechaRat(new Coordinates(0, 0), 1);
                mockPlayerAttack = new PlayerAttack(mockCharacter, mechaRat);
                mechaRat.defend(mockPlayerAttack);
            }
        } while (mechaRat.getAbility().wasUsed());
    }
}
