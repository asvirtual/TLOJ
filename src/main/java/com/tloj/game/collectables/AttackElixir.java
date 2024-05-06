package com.tloj.game.collectables;

/**
 * Represents the Consumable Item Attack Elixir in the game.<br>
 * The Attack Elixir can boost the attack stat of the player, it can be found or purchased in the game.<br>
 * It weighs {@value #WEIGHT}, with an in game cost of {@value #PRICE}
 * @see GreatHealthPotion
 * @see HealthPotion
 * @see ManaPotion
 * @see GreatManaPotion
 * @see DefenceElixir
 * @see Lockpick
 * @see WeaponShard
 * @see Ragù
 * @see SpecialKey
 * @see NorthStar
 */
public class AttackElixir extends PurchasableItem implements ConsumableItem {
    private static final double WEIGHT = 0.2;
    private static final int PRICE = 10;

    public AttackElixir() {
        super(PRICE, WEIGHT); 
    }
    
    @Override
    public void consume() {
        // Implementation of consume method goes here
    }
}

