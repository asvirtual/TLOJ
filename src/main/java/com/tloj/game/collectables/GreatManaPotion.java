package com.tloj.game.collectables;

/**
 * Represents the Consumable Item Great Mana Potion in the game.<br>
 * The Great Mana Potion can restore the Mana of the player by 30, it can be found or purchased in the game.<br>
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

    public GreatManaPotion() {
        super(PRICE, WEIGHT); 
    }
    
    @Override
    public void consume() {
        // Implementation of consume method goes here
    }
}
