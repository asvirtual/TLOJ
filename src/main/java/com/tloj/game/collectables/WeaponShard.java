package com.tloj.game.collectables;

/**
 * TODO: Establish whether this item should be a ConsumableItem or not
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
public class WeaponShard extends PurchasableItem implements ConsumableItem {
    private static final double WEIGHT = 0.5;
    private static final int PRICE = 15;

    public WeaponShard() {
        super(PRICE, WEIGHT); 
    }
    
    @Override
    public void consume() {
        // Implementation of consume method goes here
    }
}

