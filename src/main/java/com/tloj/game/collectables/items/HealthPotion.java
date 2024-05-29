package com.tloj.game.collectables.items;

import com.tloj.game.collectables.ConsumableItem;
import com.tloj.game.collectables.PurchasableItem;
import com.tloj.game.entities.Character;
import com.tloj.game.utilities.ConsoleHandler;
import com.tloj.game.utilities.Constants;
import com.fasterxml.jackson.annotation.JsonCreator;


/**
 * Represents the Consumable Item Health Potion in the game.<br>
 * The Health Potion can restore the Health Points of the player by {@value #HEAL_AMOUNT}, it can be found or purchased in the game.<br>
 * It weighs {@value #WEIGHT}, with an in game cost of {@value #PRICE}
 * @see GreatHealthPotion
 * @see ManaPotion
 * @see GreatManaPotion
 * @see AttackElixir
 * @see DefenseElixir
 * @see Emp
 * @see WeaponShard
 * @see Ragu
 * @see SpecialKey
 * @see NorthStar
 */

public class HealthPotion extends PurchasableItem implements ConsumableItem {
    private static final double WEIGHT = 0.2;
    private static final int PRICE = 10;
    private static final int HEAL_AMOUNT = 20;
    private static final double DROP_CHANCE = 0.17;
    private static final int ID = 1;

    @JsonCreator
    public HealthPotion() {
        super(PRICE, WEIGHT, ID, DROP_CHANCE); 
    }
    
    /**
     * Restores the Health Points of the player by {@value #HEAL_AMOUNT}.
     * @param consumer The character that consumes the item
     */
    @Override
    public void consume(Character consumer) {
        consumer.heal(HEAL_AMOUNT);
        consumer.removeInventoryItem(this);
    }

    @Override
    public String getASCII() {
        return Constants.HEALTH_POTION;
    }

    @Override
    public String describe() {
        return this.getASCII() + "\n" +
                "A potion that restores " + HEAL_AMOUNT + " Health Points"
                + "\n It weights: " + WEIGHT + " Mb"; 
    }

    @Override
    public String shortInfo() {
        return ConsoleHandler.RED + " (+" + HEAL_AMOUNT + " HP)" + ConsoleHandler.RESET;
    }

    @Override
    public String toString() {
        return super.toString() + shortInfo() ;
    }
}
