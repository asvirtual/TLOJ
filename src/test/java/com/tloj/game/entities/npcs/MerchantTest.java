package com.tloj.game.entities.npcs;

import static org.junit.jupiter.api.Assertions.*;

import java.io.ByteArrayInputStream;
import java.io.InputStream;

import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import com.tloj.game.entities.characters.BasePlayer;
import com.tloj.game.game.Controller;
import com.tloj.game.game.Coordinates;
import com.tloj.game.game.Dice;
import com.tloj.game.game.MockController;
import com.tloj.game.entities.Character;
import com.tloj.game.collectables.PurchasableItem;
import com.tloj.game.collectables.items.ManaPotion;
import com.tloj.game.collectables.items.HealthPotion;


class MerchantTest {
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