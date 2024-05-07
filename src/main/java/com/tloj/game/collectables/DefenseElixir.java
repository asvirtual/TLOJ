package com.tloj.game.collectables;

import com.tloj.game.entities.Character;


/**
 * Represents the Consumable Item Defense Elixir in the game.<br>
 * The Defense Elixir can boost the defense stat of the player of {@value #DEFENSE_BOOST} for the duration of a fight, it can be found or purchased in the game.<br>
 * It weighs {@value #WEIGHT}, with an in game cost of {@value #PRICE}
 * @see GreatHealthPotion
 * @see HealthPotion
 * @see ManaPotion
 * @see GreatManaPotion
 * @see AttackElixir
 * @see Lockpick
 * @see WeaponShard
 * @see Ragù
 * @see SpecialKey
 * @see NorthStar
 */
public class DefenseElixir extends PurchasableItem implements ConsumableItem {
    private static final double WEIGHT = 0.2;
    private static final int PRICE = 10;
    private static final int DEFENSE_BOOST = 5;

    public DefenseElixir() {
        super(PRICE, WEIGHT); 
    }
    
    @Override
    public void consume(Character consumer) {
        consumer.setCurrentFightDef(consumer.getDef() + DEFENSE_BOOST);
        consumer.removeInventoryItem(this);
    }
}

