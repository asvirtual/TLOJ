package com.tloj.game.collectables.weapons;

import org.junit.jupiter.api.Test;
import static org.junit.jupiter.api.Assertions.*;

import java.io.ByteArrayInputStream;
import java.io.InputStream;

import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;

import com.tloj.game.entities.characters.BasePlayer;
import com.tloj.game.entities.mobs.CyberGoblin;
import com.tloj.game.game.Controller;
import com.tloj.game.game.Coordinates;
import com.tloj.game.game.Dice;
import com.tloj.game.game.MockController;
import com.tloj.game.game.PlayerAttack;
import com.tloj.game.entities.Character;


public class NaniteLeechBladeTest {
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
            MockController.deleteController();
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
    public void usedEffectTest() {    

        NaniteLeechBlade naniteLeechBlade = new NaniteLeechBlade();
        Character mockCharacter = new BasePlayer(20, 4, 4, 10, 0, 1, 5, 10, null, null, naniteLeechBlade, null, null);
        CyberGoblin mockCyberGoblin = new CyberGoblin(new Coordinates(0, 0), 1);
        PlayerAttack mockPlayerAttack = new PlayerAttack(mockCharacter, mockCyberGoblin);

        mockCharacter.setHp(10);
        int initialHp = mockCharacter.getHp();

        while (!naniteLeechBlade.getEffect().wasUsed()) {
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



