package com.tloj.game.entities.npcs;

import static org.junit.jupiter.api.Assertions.*;

import java.io.ByteArrayInputStream;
import java.io.InputStream;

import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import com.tloj.game.entities.characters.BasePlayer;
import com.tloj.game.game.Controller;
import com.tloj.game.utilities.Coordinates;
import com.tloj.game.utilities.Dice;
import com.tloj.game.entities.Character;
import com.tloj.game.collectables.PurchasableItem;
import com.tloj.game.collectables.items.ManaPotion;
import com.tloj.game.collectables.items.HealthPotion;


class MerchantTest {
    private final InputStream originalSystemIn = System.in;
    private Thread inputThread;
    
    @BeforeEach
    public void setUpInput() {
        this.inputThread =  new Thread(() -> {
            while (true) {
                System.setIn(new ByteArrayInputStream("\n\n".getBytes()));
                try {
                    Thread.sleep(100);  // Sleep for a short time to ensure the input is read
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
            }
        });

        inputThread.start();
        Dice.setSeed(1);
        Controller.getInstance();
    }

    @AfterEach
    public void restoreSystemIn() {
        this.inputThread.interrupt();
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