package com.tloj.game.collectables.items;

import com.tloj.game.collectables.PurchasableItem;
import com.tloj.game.utilities.Constants;

import com.fasterxml.jackson.annotation.JsonCreator;


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
 * @see Emp
 * @see Ragu
 * @see SpecialKey
 * @see NorthStar
 */
public class WeaponShard extends PurchasableItem {
    private static final double WEIGHT = 0.5;
    private static final int PRICE = 40;
    private static final double DROP_CHANCE = 0.07;
    private static final int DROP_MONEY = PRICE / 2;
    private static final int ID = 10;

    @JsonCreator
    public WeaponShard() {
        super(PRICE, WEIGHT, DROP_MONEY, ID); 
    }
    
    @Override
    public double getDropChance(){
        return DROP_CHANCE;
    }

    @Override
    public String toString() {
        return "WeaponShard";
    }

    @Override
    public String getASCII() {
        return Constants.WEAPON_SHARD;
    }

    @Override
    public String describe() {
        return this.getASCII() + "\n" +
               "A shard that can upgrade your weapon + \n" +
               "Nothing is created, nothing is destroyed, everything can be recycled!"
               + "\n It weights: " + WEIGHT + " Mb";
    }

    @Override
    public String shortInfo() {
        return "";
    }
}

