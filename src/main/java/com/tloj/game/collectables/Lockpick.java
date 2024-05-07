package com.tloj.game.collectables;

import com.tloj.game.entities.Character;


/**
 * Represents the Consumable Item Lockpick in the game.<br>
 * The Lockpick allows the player to skip trap rooms without dice rolls, it can be found or purchased in the game.<br>
 * It weighs {@value #WEIGHT}, with an in game cost of {@value #PRICE}
 * @see GreatHealthPotion
 * @see HealthPotion
 * @see ManaPotion
 * @see GreatManaPotion
 * @see AttackElixir
 * @see DefenseElixir
 * @see WeaponShard
 * @see Ragù
 * @see SpecialKey
 * @see NorthStar
*/
public class Lockpick extends PurchasableItem implements ConsumableItem {
    private static final double WEIGHT = 0.1;
    private static final int PRICE = 50;

    public Lockpick() {
        super(PRICE, WEIGHT); 
    }
    
    @Override
    public void consume(Character consumer) {
        // Implementation of consume method goes here
    }
}

