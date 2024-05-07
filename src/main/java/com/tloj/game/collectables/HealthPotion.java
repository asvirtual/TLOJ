package com.tloj.game.collectables;

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
    private static final int PRICE=  7;
    private static final int HEAL_AMOUNT = 20;

    public HealthPotion() {
        super(PRICE, WEIGHT); 
    }
    
    @Override
    public void consume(Character consumer) {
        consumer.heal(HEAL_AMOUNT);
        if (consumer.getHp() > consumer.getMaxHp()) consumer.setHp(consumer.getMaxHp());
        
        consumer.getInventory().remove(this);
    }
}
