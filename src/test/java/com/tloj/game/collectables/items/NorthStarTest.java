package com.tloj.game.collectables.items;

import com.tloj.game.entities.Character;
import com.tloj.game.entities.characters.BasePlayer;
import com.tloj.game.game.Controller;
import com.tloj.game.game.Dice;
import com.tloj.game.game.Floor;
import com.tloj.game.game.Game;
import com.tloj.game.utilities.Constants;
import com.tloj.game.utilities.JsonParser;

import org.junit.jupiter.api.Test;
import static org.junit.jupiter.api.Assertions.*;

import java.io.ByteArrayInputStream;
import java.io.InputStream;
import java.util.ArrayList;

import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;

public class NorthStarTest {

    private final InputStream originalSystemIn = System.in;

    @BeforeEach
    public void setUpInput() {
        try {
            Thread.sleep(100); 
            
            String input = "y";
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
    void testConsume() {
        ArrayList<Floor> mockLevels = JsonParser.deserializeMapFromFile(Constants.MAP_FILE_PATH);
        Game mockGame = new Game(mockLevels, 1);
        Controller.getInstance().setGame(mockGame);


        Character mockCharacter = new BasePlayer(null);

        mockGame.setPlayer(mockCharacter);

        NorthStar item = new NorthStar();

        mockCharacter.addInventoryItem(item);

        String [] mockMap=mockGame.generateMapLines();
        
        
        
        assertEquals(0, mockGame);
    }
}
