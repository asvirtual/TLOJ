package com.tloj.game.collectables;

/**
 * Represents the Consumable Item NorthStar in the game.<br>
 * The NorthStar can print the entire game map, it can be found or purchased in the game.<br>
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
 * @see SpecialKey
 */

public class NorthStar extends PurchasableItem implements ConsumableItem {
    private static final double WEIGHT = 0;
    private static final int PRICE=0;

    public NorthStar() {
        super(PRICE, WEIGHT); 
    }
    
    @Override
    public void consume() {
        // Implementation of consume method goes here
    }
}

