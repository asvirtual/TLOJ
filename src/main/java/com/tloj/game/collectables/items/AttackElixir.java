package com.tloj.game.collectables.items;

import com.tloj.game.collectables.ConsumableItem;
import com.tloj.game.collectables.PurchasableItem;
import com.tloj.game.entities.Character;
import com.tloj.game.utilities.Constants;
import com.fasterxml.jackson.annotation.JsonCreator;

/**
 * Represents the Consumable Item Attack Elixir in the game.<br>
 * The Attack Elixir can boost the attack stat of the player of {@value #ATTACK_BOOST} for the duration of a fight, it can be found or purchased in the game.<br>
 * It weighs {@value #WEIGHT}, with an in game cost of {@value #PRICE}
 * @see GreatHealthPotion
 * @see HealthPotion
 * @see ManaPotion
 * @see GreatManaPotion
 * @see DefenseElixir
 * @see Emp
 * @see WeaponShard
 * @see Ragu
 * @see SpecialKey
 * @see NorthStar
 */


public class AttackElixir extends PurchasableItem implements ConsumableItem {
    private static final double WEIGHT = 0.2;
    private static final int PRICE = 15;
    private static final int ATTACK_BOOST = 5;
    private static final double DROP_CHANCE = 0.1;
    private static final int DROP_MONEY = PRICE / 2;
    private static final int ID = 5;

    @JsonCreator
    public AttackElixir() {
        super(PRICE, WEIGHT, DROP_MONEY, ID, DROP_CHANCE); 
    }
    
    @Override
    public void consume(Character consumer) {
        consumer.setCurrentFightAtk(consumer.getAtk() + ATTACK_BOOST);
        consumer.removeInventoryItem(this);
    }

    @Override
    public String getASCII() {
        return Constants.ATTACK_ELIXIR;
    }

    @Override
    public String describe() {
        return "An elixir that boosts your attack by " + ATTACK_BOOST + " for the duration of a fight";
    }
}

