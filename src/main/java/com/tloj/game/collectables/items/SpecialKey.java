
package com.tloj.game.collectables.items;

import com.tloj.game.collectables.ConsumableItem;
import com.tloj.game.collectables.PurchasableItem;
import com.tloj.game.entities.Character;


/**
 * TODO: Establish whether this item should be a ConsumableItem or not
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

    public SpecialKey() {
        super(PRICE, WEIGHT, DROP_MONEY, ID); 
    }
    
    @Override
    public void consume(Character consumer) {
        // Implementation of consume method goes here
    }

    @Override
    public String toString() {
        return "Special Key";
    }
}

