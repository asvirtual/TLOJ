package com.tloj.game.entities.npcs;

import static org.junit.jupiter.api.Assertions.*;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import com.tloj.game.entities.characters.BasePlayer;
import com.tloj.game.game.Controller;
import com.tloj.game.game.Coordinates;
import com.tloj.game.game.Dice;
import com.tloj.game.game.ControllerHandler;
import com.tloj.game.entities.Character;
import com.tloj.game.collectables.PurchasableItem;
import com.tloj.game.collectables.items.ManaPotion;
import com.tloj.game.collectables.items.HealthPotion;


class MerchantTest {
   
    @BeforeEach
    public void setUpInput() {
        Dice.setSeed(1);
        ControllerHandler.deleteController();
        Controller.getInstance();
      
    }

   @Test
   void buyTest(){

      Controller.getInstance();
      Merchant mockMerchant = new Merchant(new Coordinates(0, 0));
      Character mockCharacter = new BasePlayer(null);
      mockCharacter.removeInventoryItem(0);
      
      int STARTING_MONEY = 100;
      mockCharacter.setMoney(STARTING_MONEY);

      mockMerchant.interact(mockCharacter);
      mockMerchant.buy(1);    
      PurchasableItem boughtItem = (PurchasableItem) mockCharacter.getInventoryItem(0);

      assertTrue(mockCharacter.hasItem(boughtItem));
      assertEquals(STARTING_MONEY - boughtItem.getPrice(), mockCharacter.getMoney());
   }

   @Test
   void noMoneyBuyTest(){
      Merchant mockMerchant = new Merchant(new Coordinates(0, 0));
      Character mockCharacter = new BasePlayer(null);
      mockCharacter.removeInventoryItem(0);
      
      int STARTING_MONEY = 5;
      mockCharacter.setMoney(STARTING_MONEY);

      mockMerchant.interact(mockCharacter);
      mockMerchant.buy(1);    

      assertEquals(0, mockCharacter.getInventorySize());
      assertEquals(STARTING_MONEY, mockCharacter.getMoney());
   }

   @Test
   void notEnoughtWeightBuyTest(){
      Merchant mockMerchant = new Merchant(new Coordinates(0, 0));
      Character mockCharacter = new BasePlayer(null);
      mockCharacter.removeInventoryItem(0);
      
      int STARTING_MONEY = 100;
      mockCharacter.setMoney(STARTING_MONEY);      
      
      while(mockCharacter.canCarry(new ManaPotion()))
         mockCharacter.addInventoryItem(new ManaPotion());

      mockMerchant.interact(mockCharacter);
      mockMerchant.buy(0);    

      assertTrue(!mockCharacter.hasItem(new HealthPotion()));
      assertEquals(STARTING_MONEY, mockCharacter.getMoney());
   }
}