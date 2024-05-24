package com.tloj.game.collectables.weapons;

import org.junit.jupiter.api.Test;
import static org.junit.jupiter.api.Assertions.*;

import java.io.InputStream;

import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;

import com.tloj.game.entities.characters.BasePlayer;
import com.tloj.game.entities.mobs.CyberGoblin;
import com.tloj.game.game.Controller;
import com.tloj.game.game.Coordinates;
import com.tloj.game.game.Dice;
import com.tloj.game.game.ControllerHandler;
import com.tloj.game.game.PlayerAttack;
import com.tloj.game.entities.Character;


public class NaniteLeechBladeTest {
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
    public void usedEffectTest() {    

        NaniteLeechBlade naniteLeechBlade = new NaniteLeechBlade();
        Character mockCharacter = new BasePlayer(20, 4, 4, 10, 0, 1, 5, 10, null, null, naniteLeechBlade, null, null);
        CyberGoblin mockCyberGoblin = new CyberGoblin(new Coordinates(0, 0), 1);
        PlayerAttack mockPlayerAttack = new PlayerAttack(mockCharacter, mockCyberGoblin);

        mockCharacter.setHp(10);
        int initialHp = mockCharacter.getHp();

        while (!naniteLeechBlade.getEffect().wasUsed()) {
            ControllerHandler.deleteController();
            ControllerHandler.setInput("\n");
            Controller.getInstance();
            naniteLeechBlade.modifyAttack(mockPlayerAttack);
            int totalDamage = mockPlayerAttack.getTotalDamage();
            mockPlayerAttack.perform();

            if (naniteLeechBlade.getEffect().wasUsed()) 
                assertEquals(mockCharacter.getHp(), initialHp + (totalDamage / 2));
        }
    }

    @Test
    public void notUsedEffectTest() {

        NaniteLeechBlade naniteLeechBlade = new NaniteLeechBlade();
        Character mockCharacter = new BasePlayer(20, 4, 4, 10, 0, 1, 5, 10, null, null, naniteLeechBlade, null, null);
        CyberGoblin mockCyberGoblin = new CyberGoblin(new Coordinates(0, 0), 1);
        PlayerAttack mockPlayerAttack = new PlayerAttack(mockCharacter, mockCyberGoblin);

        mockCharacter.setHp(10);
        int initialHp = mockCharacter.getHp();
    
        do {
            ControllerHandler.deleteController();
            ControllerHandler.setInput("\n");
            Controller.getInstance();
            naniteLeechBlade.modifyAttack(mockPlayerAttack);
            int weaponRoll = mockPlayerAttack.getWeaponRoll();
            mockPlayerAttack.perform();
            if (!naniteLeechBlade.getEffect().wasUsed()) {
                assertTrue(weaponRoll >= 1 && weaponRoll <= 12);
                assertEquals(initialHp, mockCharacter.getHp());
            } else {
                mockCharacter.setHp(initialHp);
                mockCyberGoblin.setHp(mockCyberGoblin.getMaxHp());
            }
        } while(naniteLeechBlade.getEffect().wasUsed());
    }
}



