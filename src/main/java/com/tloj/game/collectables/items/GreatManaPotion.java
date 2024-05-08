package com.tloj.game.collectables.items;

import com.tloj.game.collectables.ConsumableItem;
import com.tloj.game.collectables.PurchasableItem;
import com.tloj.game.entities.Character;


/**
 * Represents the Consumable Item Great Mana Potion in the game.<br>
 * The Great Mana Potion can restore the Mana of the player by {@value #MANA_RESTORE}, it can be found or purchased in the game.<br>
 * It weighs {@value #WEIGHT}, with an in game cost of {@value #PRICE}
 * @see GreatHealthPotion
 * @see HealthPotion
 * @see ManaPotion
 * @see AttackElixir
 * @see DefenceElixir
 * @see Lockpick
 * @see WeaponShard
 * @see Ragù
 * @see SpecialKey
 * @see NorthStar
 */
public class GreatManaPotion extends PurchasableItem implements ConsumableItem {
    private static final double WEIGHT = 0.2;
    private static final int PRICE = 20;
    private static final int MANA_RESTORE = 30;
    private static final double DROP_CHANCE = 0.13;

    public GreatManaPotion() {
        super(PRICE, WEIGHT); 
    }
    
    @Override
    public void consume(Character consumer) {
        consumer.restoreMana(MANA_RESTORE);
        if (consumer.getMana() > consumer.getMaxMana()) consumer.setMana(consumer.getMaxMana());
        
        consumer.removeInventoryItem(this);
    }
    @Override
    public double getDropChance() {
        return DROP_CHANCE;
    }
}
