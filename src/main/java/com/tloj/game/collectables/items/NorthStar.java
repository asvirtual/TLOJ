package com.tloj.game.collectables.items;

import com.tloj.game.collectables.Item;
import com.tloj.game.utilities.Constants;

import com.fasterxml.jackson.annotation.JsonCreator;


/**
 * Represents the Consumable Item NorthStar in the game.<br>
 * The NorthStar can print the entire game map, it can be found as a special reward.<br>
 * It weighs {@value #WEIGHT}<br>
 * @see GreatHealthPotion
 * @see HealthPotion
 * @see ManaPotion
 * @see GreatManaPotion
 * @see AttackElixir
 * @see DefenseElixir
 * @see Emp
 * @see WeaponShard
 * @see Ragu
 * @see SpecialKey
*/

public class NorthStar extends Item {
    private static final double WEIGHT = 0;
    private static final double DROP_CHANCE = 0.025;
    private static final int ID = 9;

    @JsonCreator
    public NorthStar() {
        super(WEIGHT, ID, DROP_CHANCE); 
    }

    @Override
    public String getASCII() {
        return Constants.NORTH_STAR;
    }

    @Override
    public String describe() {
        return this.getASCII() + "\n" +
                "A magical star that guides heroes to their destination, revealing the entire map"
                + "\n This item has no weight, it's a special reward.";
    }
}

