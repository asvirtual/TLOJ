package com.tloj.game.collectables.items;

import com.tloj.game.collectables.PurchasableItem;

/**
 * Represents the Consumable Item Weapon Shard in the game.<br>
 * The Weapon Shard upgrades the current weapon of the player if given to a smith, it can be found or purchased in the game.<br>
 * It weighs {@value #WEIGHT}, with an in game cost of {@value #PRICE}
 * @see GreatHealthPotion
 * @see HealthPotion
 * @see ManaPotion
 * @see GreatManaPotion
 * @see AttackElixir
 * @see DefenseElixir
 * @see Lockpick
 * @see Ragù
 * @see SpecialKey
 * @see NorthStar
 */
public class WeaponShard extends PurchasableItem {
    private static final double WEIGHT = 0.5;
    private static final int PRICE = 15;
    private static final double DROP_CHANCE = 0.6;

    public WeaponShard() {
        super(PRICE, WEIGHT); 
    }
    @Override
    public double getDropChance(){
        return DROP_CHANCE;
    }

    @Override
    public String toString() {
        return "Weapon Shard";
    }
}

