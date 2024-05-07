package com.tloj.game.collectables;

import com.tloj.game.entities.Character;


/**
 * Represents the Consumable Item Great Health Potion in the game.<br>
 * The Health Potion can restore the Health Points of the player by {@value #HEAL_AMOUNT}, it can be found or purchased in the game.<br>
 * It weighs {@value #WEIGHT}, with an in game cost of {@value #PRICE}
 * @see HealthPotion
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
public class GreatHealthPotion extends PurchasableItem implements ConsumableItem {
    private static final double WEIGHT = 0.2;
    private static final int PRICE = 20;
    private static final int HEAL_AMOUNT = 50;

    public GreatHealthPotion() {
        super(PRICE, WEIGHT); 
    }
    
    @Override
    public void consume(Character consumer) {
        consumer.heal(HEAL_AMOUNT);
        if (consumer.getHp() > consumer.getMaxHp()) consumer.setHp(consumer.getMaxHp());
        
        consumer.getInventory().remove(this);
    }
}