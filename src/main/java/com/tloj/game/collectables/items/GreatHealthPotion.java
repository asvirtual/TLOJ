package com.tloj.game.collectables.items;

import com.tloj.game.collectables.ConsumableItem;
import com.tloj.game.collectables.PurchasableItem;
import com.tloj.game.entities.Character;
import com.tloj.game.utilities.Constants;
import com.fasterxml.jackson.annotation.JsonCreator;
import com.fasterxml.jackson.annotation.JsonProperty;


/**
 * Represents the Consumable Item Great Health Potion in the game.<br>
 * The Health Potion can restore the Health Points of the player by {@value #HEAL_AMOUNT}, it can be found or purchased in the game.<br>
 * It weighs {@value #WEIGHT}, with an in game cost of {@value #PRICE}
 * @see HealthPotion
 * @see ManaPotion
 * @see GreatManaPotion
 * @see AttackElixir
 * @see DefenseElixir
 * @see Lockpick
 * @see WeaponShard
 * @see Ragu
 * @see SpecialKey
 * @see NorthStar
 */
public class GreatHealthPotion extends PurchasableItem implements ConsumableItem {
    private static final double WEIGHT = 0.3;
    private static final int PRICE = 20;
    private static final int HEAL_AMOUNT = 50;
    private static final double DROP_CHANCE = 0.12;
    private static final int DROP_MONEY = PRICE / 2;
    private static final int ID = 2;

    @JsonCreator
    public GreatHealthPotion() {
        super(PRICE, WEIGHT, DROP_MONEY, ID, DROP_CHANCE); 
    }
    
    @Override
    public void consume(Character consumer) {
        consumer.heal(HEAL_AMOUNT);
        if (consumer.getHp() > consumer.getMaxHp()) consumer.setHp(consumer.getMaxHp());
        
        consumer.removeInventoryItem(this);
    }

    @Override
    public String getASCII() {
        return Constants.GREAT_HEALTH_POTION;
    }

    @Override
    public String describe() {
        return "A potion that restores " + HEAL_AMOUNT + " Health Points";
    }
}