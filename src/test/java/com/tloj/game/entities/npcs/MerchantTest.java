package com.tloj.game.entities.npcs;

import static org.junit.jupiter.api.Assertions.*;
import org.junit.jupiter.api.Test;
import java.io.ByteArrayInputStream;
import java.util.ArrayList;

import com.tloj.game.entities.characters.BasePlayer;
import com.tloj.game.game.Controller;
import com.tloj.game.utilities.Coordinates;
import com.tloj.game.collectables.weapons.LaserBlade;
import com.tloj.game.entities.Character;
import com.tloj.game.collectables.Item;
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

      ArrayList<Item> inventory = new ArrayList<Item>();

      Controller mockController = Controller.getInstance();
      Merchant mockMerchant = new Merchant(new Coordinates(0, 0));
      Character mockCharacter = new BasePlayer(20, 4, 4, 10, 0, 1, 5, 100, null, null, new LaserBlade(), inventory, null);

      mockMerchant.interact(mockCharacter);
      mockMerchant.buy(1);    
      assertTrue( mockCharacter.hasItem(new HealthPotion()));
   }


}