package com.tloj.game.collectables.items;

import com.tloj.game.collectables.PurchasableItem;

/**
 * Represents the Consumable Item NorthStar in the game.<br>
 * The NorthStar can print the entire game map, it can be found as a special reward.<br>
 * It weighs {@value #WEIGHT}, with an in game cost of {@value #PRICE}
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
public class NorthStar extends PurchasableItem {
    private static final double WEIGHT = 0;
    private static final int PRICE = 0;
    private static final double DROP_CHANCE = 0.025;

    public NorthStar() {
        super(PRICE, WEIGHT); 
    }

    public NorthStar(NorthStar northStar) {
        super(PRICE, WEIGHT);
    }
    @Override
    public double getDropChance() {
        return DROP_CHANCE;
    }

    @Override
    public String toString() {
        return "North Star";
    }
}

