package com.tloj.game.collectables;

/**
 * Represents the Consumable Item Ragu in the game.<br>
 * Item Ragu restore all HP and Mana, boosts def stat and the damage inflicted by a +3 factor, it can be found or purchased in the game.<br>
 * It weighs {@value #WEIGHT}, with an in game cost of D{@value #PRICE}
 * @see GreatHealthPotion
 * @see HealthPotion
 * @see ManaPotion
 * @see GreatManaPotion
 * @see AttackElixir
 * @see DefenseElixir
 * @see Lockpick
 * @see WeaponShard
 * @see SpecialKey
 * @see NorthStar
 */

public class Ragu extends PurchasableItem implements ConsumableItem {
    private static final double WEIGHT = 1;
    private static final int PRICE=0;

    public Ragu() {
        super(PRICE, WEIGHT); 
    }
    
    @Override
    public void consume() {
        // Implementation of consume method goes here
    }
}

