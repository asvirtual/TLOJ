package com.tloj.game.collectables.items;

import com.tloj.game.collectables.ConsumableItem;
import com.tloj.game.collectables.PurchasableItem;
import com.tloj.game.entities.Character;


/**
 * Represents the Consumable Item Ragu in the game.<br>
 * Item Ragu restore all HP and Mana, boosts def stat and the damage inflicted by a {@value #STAT_BOOST} factor for the duration of a fight, it can be found or purchased in the game.<br>
 * It weighs {@value #WEIGHT}, with an in game cost of {@value #PRICE}
 * @see GreatHealthPotion
 * @see HealthPotion
 * @see ManaPotion
 * @see GreatManaPotion
 * @see AttackElixir
 * @see DefenseElixir
 * @see Lockpick
 * @see WeaponShard
 * @see SpecialKey
 * @see NorthStar
 */
public class Ragu extends PurchasableItem implements ConsumableItem {
    private static final double WEIGHT = 1;
    private static final int PRICE = 0;
    private static final int STAT_BOOST = 3;
    private static final double DROP_CHANCE = 0.075;
    private static final int DROP_MONEY = PRICE / 2;
    private static final int ID = 7;
    

    public Ragu() {
        super(PRICE, WEIGHT, DROP_MONEY, ID, DROP_CHANCE); 
    }
    
    @Override
    public void consume(Character consumer) {
        consumer.setHp(consumer.getMaxHp());
        consumer.setMana(consumer.getMaxMana());
        consumer.setCurrentFightDef(consumer.getDef() + STAT_BOOST);
        consumer.setCurrentFightAtk(consumer.getAtk() + STAT_BOOST);

        consumer.removeInventoryItem(this);   
    }

    @Override
    public String toString() {
        return "Ragu'";
    }
}

