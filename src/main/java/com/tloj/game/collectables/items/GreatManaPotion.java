package com.tloj.game.collectables.items;

import com.tloj.game.collectables.ConsumableItem;
import com.tloj.game.collectables.PurchasableItem;
import com.tloj.game.entities.Character;
import com.tloj.game.utilities.ConsoleHandler;
import com.tloj.game.utilities.Constants;

import com.fasterxml.jackson.annotation.JsonCreator;


/**
 * Represents the Consumable Item Great Mana Potion in the game.<br>
 * The Great Mana Potion can restore the Mana of the player by {@value #MANA_RESTORE}, it can be found or purchased in the game.<br>
 * It weighs {@value #WEIGHT}, with an in game cost of {@value #PRICE}
 * @see GreatHealthPotion
 * @see HealthPotion
 * @see ManaPotion
 * @see AttackElixir
 * @see DefenseElixir
 * @see Emp
 * @see WeaponShard
 * @see Ragu
 * @see SpecialKey
 * @see NorthStar
 */

public class GreatManaPotion extends PurchasableItem implements ConsumableItem {
    private static final double WEIGHT = 0.3;
    private static final int PRICE = 18;
    private static final int MANA_RESTORE = 30;
    private static final double DROP_CHANCE = 0.12;
    private static final int ID = 4;

    @JsonCreator
    public GreatManaPotion() {
        super(PRICE, WEIGHT, ID, DROP_CHANCE); 
    }
    
    /**
     * Restores the Mana of the player by {@value #MANA_RESTORE}.
     * @param consumer The character that consumes the item
     */
    @Override
    public void consume(Character consumer) {
        consumer.restoreMana(MANA_RESTORE);
        consumer.removeInventoryItem(this);
    }

    @Override
    public String getASCII() {
        return Constants.GREAT_MANA_POTION;
    }

    @Override
    public String describe() {
        return this.getASCII() + "\n" +
                "A potion that restores " + MANA_RESTORE + " Mana Points"
                + "\n It weights: " + WEIGHT + " Mb";
    }

    @Override  
    public String shortInfo() {
        return ConsoleHandler.BLUE + " (+" + MANA_RESTORE + " Mana)" + ConsoleHandler.RESET;
    }

    @Override
    public String toString() {
        return super.toString() + shortInfo() ;
    }
}
