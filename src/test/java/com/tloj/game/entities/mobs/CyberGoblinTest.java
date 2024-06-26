package com.tloj.game.entities.mobs;

import org.junit.jupiter.api.Test;
import static org.junit.jupiter.api.Assertions.*;

import java.io.InputStream;

import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;

import com.tloj.game.entities.characters.BasePlayer;
import com.tloj.game.game.Coordinates;
import com.tloj.game.game.Dice;
import com.tloj.game.game.ControllerHandler;
import com.tloj.game.game.Controller;
import com.tloj.game.game.PlayerAttack;
import com.tloj.game.entities.Character;


/**
 * {@code CyberGoblinTest} is a test class for the {@link CyberGoblin} entity.<br>
 * It tests the constructors for the level up mechanic.<br>
 * It tests the ability of the CyberGoblin to steal money from the player.<br>
 * It also tests wether the ability triggers or not.<br>
 */

public class CyberGoblinTest {
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
        CyberGoblin cyberGoblin = new CyberGoblin(new Coordinates(0, 0), 2);
        
        assertEquals(20, cyberGoblin.getHp());
        assertEquals(8, cyberGoblin.getAtk());
        assertEquals(4, cyberGoblin.getDef());
        assertEquals(8, cyberGoblin.getCurrentFightAtk());
        assertEquals(4, cyberGoblin.getCurrentFightDef());
        assertEquals(7, cyberGoblin.getDiceFaces());
        assertEquals(10, cyberGoblin.dropXp());
        assertEquals(3, cyberGoblin.getMoneyDrop());
    }

    @Test
    void testCyberGoblinLevelThree() {
        CyberGoblin cyberGoblin = new CyberGoblin(new Coordinates(0, 0), 3);
        
        assertEquals(30, cyberGoblin.getHp());
        assertEquals(14, cyberGoblin.getAtk());
        assertEquals(7, cyberGoblin.getDef());
        assertEquals(14, cyberGoblin.getCurrentFightAtk());
        assertEquals(7, cyberGoblin.getCurrentFightDef());
        assertEquals(7, cyberGoblin.getDiceFaces());
        assertEquals(15, cyberGoblin.dropXp());
        assertEquals(3, cyberGoblin.getMoneyDrop());
    }

    @Test
    void testConstructorLevelGreaterThanThree() {
        CyberGoblin cyberGoblin = new CyberGoblin(new Coordinates(0, 0), 4);
        
        assertEquals(10 * cyberGoblin.getLvl(), cyberGoblin.getHp());
        assertEquals(14 + 4 * (cyberGoblin.getLvl() - 3), cyberGoblin.getAtk());
        assertEquals(7 + 2 * (cyberGoblin.getLvl() - 3), cyberGoblin.getDef());
        assertEquals(14 + 4 * (cyberGoblin.getLvl() - 3), cyberGoblin.getCurrentFightAtk());
        assertEquals(7 + 2 * (cyberGoblin.getLvl() - 3), cyberGoblin.getCurrentFightDef());
        assertEquals(7, cyberGoblin.getDiceFaces());
        assertEquals(5 * cyberGoblin.getLvl(), cyberGoblin.dropXp());
        assertEquals(3, cyberGoblin.getMoneyDrop());
    }

    @Test
    void testSkillUsed() {
        CyberGoblin cyberGoblin = new CyberGoblin(new Coordinates(0, 0), 1);
        Character mockCharacter = new BasePlayer(20, 4, 4, 10, 0, 1, 5, 10, null, null, null, null, null);

        PlayerAttack mockPlayerAttack = new PlayerAttack(mockCharacter, cyberGoblin);

        while (!cyberGoblin.getAbility().wasUsed()) {

            ControllerHandler.deleteController();
            ControllerHandler.setInput("\n");
            Controller.getInstance();

            cyberGoblin.defend(mockPlayerAttack);

            if (cyberGoblin.getAbility().wasUsed()) 
                assertTrue(mockCharacter.getMoney() >= 4 && mockCharacter.getMoney() <= 7);
            else {
                cyberGoblin = new CyberGoblin(new Coordinates(0, 0), 1);
                mockCharacter = new BasePlayer(20, 4, 4, 10, 0, 1, 5, 10, null, null, null, null, null);
                mockPlayerAttack = new PlayerAttack(mockCharacter, cyberGoblin);
            }
        }
    }

    @Test
    void testSkillNotUsed() {
        CyberGoblin cyberGoblin = new CyberGoblin(new Coordinates(0, 0), 1);
        Character mockCharacter = new BasePlayer(20, 4, 4, 10, 0, 1, 5, 10, null, null, null, null, null);
        PlayerAttack mockPlayerAttack = new PlayerAttack(mockCharacter, cyberGoblin);

        do {

            ControllerHandler.deleteController();
            ControllerHandler.setInput("\n");
            Controller.getInstance();

            cyberGoblin.defend(mockPlayerAttack);

            if (!cyberGoblin.getAbility().wasUsed()) {
                assertEquals(10, mockCharacter.getMoney());
                return;
            }
            else {
                cyberGoblin = new CyberGoblin(new Coordinates(0, 0), 1);
                mockPlayerAttack = new PlayerAttack(mockCharacter, cyberGoblin);
                mockCharacter.setMoney(10);
            }
        } while (!cyberGoblin.getAbility().wasUsed());
    }
}