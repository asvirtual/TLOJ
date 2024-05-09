package com.tloj.game.collectables.items;

import com.tloj.game.collectables.Item;

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
public class NorthStar extends Item {
    private static final double WEIGHT = 0;
    private static final double DROP_CHANCE = 0.025;
    private static final int DROP_MONEY = 50;
    private static final int ID = 9;

    public NorthStar() {
        super(WEIGHT, DROP_MONEY, ID, DROP_CHANCE); 
    }


    @Override
    public String toString() {
        return "North Star";
    }
}

