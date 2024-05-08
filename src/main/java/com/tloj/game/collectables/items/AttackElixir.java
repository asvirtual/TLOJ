package com.tloj.game.collectables.items;

import com.tloj.game.collectables.ConsumableItem;
import com.tloj.game.collectables.PurchasableItem;
import com.tloj.game.entities.Character;


/**
 * Represents the Consumable Item Attack Elixir in the game.<br>
 * The Attack Elixir can boost the attack stat of the player of {@value #ATTACK_BOOST} for the duration of a fight, it can be found or purchased in the game.<br>
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
    private static final int ATTACK_BOOST = 5;
    private static final double DROP_CHANCE = 0.1;

    public AttackElixir() {
        super(PRICE, WEIGHT); 
    }
    
    @Override
    public void consume(Character consumer) {
        consumer.setCurrentFightAtk(consumer.getAtk() + ATTACK_BOOST);

        consumer.removeInventoryItem(this);
    }
    @Override
    public double getDropChance() {
        return DROP_CHANCE;
    }
}
