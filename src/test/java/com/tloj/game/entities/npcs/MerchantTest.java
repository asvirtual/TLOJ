package com.tloj.game.entities.npcs;

import static org.junit.jupiter.api.Assertions.*;
import org.junit.jupiter.api.Test;

import com.tloj.game.entities.characters.BasePlayer;
import com.tloj.game.game.Controller;
import com.tloj.game.utilities.Coordinates;
import com.tloj.game.entities.Character;
import com.tloj.game.collectables.items.HealthPotion;



class MerchantTest {
    /**
     * 
        String input = "mock input";
        ByteArrayInputStream testIn = new ByteArrayInputStream(input.getBytes());
        System.setIn(testIn);
     */

   @Test
   void buyTest(){
      /*
      String input = "mock input";
      ByteArrayInputStream testIn = new ByteArrayInputStream(input.getBytes());
      System.setIn(testIn);
       */

      Controller.getInstance();
      Merchant mockMerchant = new Merchant(new Coordinates(0, 0));
      Character mockCharacter = new BasePlayer(null);

      mockMerchant.interact(mockCharacter);
      mockMerchant.buy(1);    
      assertTrue( mockCharacter.hasItem(new HealthPotion()));
   }


}