package com.tloj.game.collectables.items;

import com.tloj.game.collectables.ConsumableItem;
import com.tloj.game.collectables.PurchasableItem;
import com.tloj.game.entities.Character;


/**
 * Represents the Consumable Item Health Potion in the game.<br>
 * The Health Potion can restore the Health Points of the player by {@value #HEAL_AMOUNT}, it can be found or purchased in the game.<br>
 * It weighs {@value #WEIGHT}, with an in game cost of {@value #PRICE}
 * @see GreatHealthPotion
 * @see ManaPotion
 * @see GreatManaPotion
 * @see AttackElixir
 * @see DefenceElixir
 * @see Lockpick
 * @see WeaponShard
 * @see Ragù
 * @see SpecialKey
 * @see NorthStar
 */
public class HealthPotion extends PurchasableItem implements ConsumableItem {
    private static final double WEIGHT = 0.1;
    private static final int PRICE =  7;
    private static final int HEAL_AMOUNT = 20;
    private static final double DROP_CHANCE = 0.18;

    public HealthPotion() {
        super(PRICE, WEIGHT); 
    }
    
    @Override
    public void consume(Character consumer) {
        consumer.heal(HEAL_AMOUNT);
        if (consumer.getHp() > consumer.getMaxHp()) consumer.setHp(consumer.getMaxHp());
        
        consumer.removeInventoryItem(this);
    }
    @Override
    public double getDropChance() {
        return DROP_CHANCE;
    }

    @Override
    public String toString() {
        return "Health Potion";
    }
}
