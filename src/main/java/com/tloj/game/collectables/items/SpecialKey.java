
package com.tloj.game.collectables.items;

import com.tloj.game.collectables.ConsumableItem;
import com.tloj.game.collectables.PurchasableItem;
import com.tloj.game.entities.Character;
import com.fasterxml.jackson.annotation.JsonCreator;
import com.fasterxml.jackson.annotation.JsonProperty;



/**
 * Represents the Consumable Item Special Key in the game.<br>
 * The Special Key opens a special locked loot room, it can be found or purchased in the game.<br>
 * It weighs {@value #WEIGHT}, with an in game cost of D{@value #PRICE}
 * @see GreatHealthPotion
 * @see HealthPotion
 * @see ManaPotion
 * @see GreatManaPotion
 * @see AttackElixir
 * @see DefenseElixir
 * @see Lockpick
 * @see WeaponShard
 * @see Ragu
 * @see NorthStar
 */
public class SpecialKey extends PurchasableItem implements ConsumableItem {
    private static final double WEIGHT = 0;
    private static final int PRICE = 0;
    private static final int DROP_MONEY = PRICE / 2;
    private static final int ID = 11;    

    @JsonCreator
    public SpecialKey() {
        super(PRICE, WEIGHT, DROP_MONEY, ID); 
    }
    
    @Override
    public void consume(Character consumer) {
        System.out.println("You've used the Special Key to unlock a special loot room!");
        consumer.removeInventoryItem(this);
    }
}

