package com.tloj.game.collectables.items;

import com.tloj.game.collectables.ConsumableItem;
import com.tloj.game.collectables.PurchasableItem;
import com.tloj.game.entities.Character;
import com.fasterxml.jackson.annotation.JsonCreator;
import com.fasterxml.jackson.annotation.JsonProperty;


/**
 * Represents the Consumable Item Great Mana Potion in the game.<br>
 * The Great Mana Potion can restore the Mana of the player by {@value #MANA_RESTORE}, it can be found or purchased in the game.<br>
 * It weighs {@value #WEIGHT}, with an in game cost of {@value #PRICE}
 * @see GreatHealthPotion
 * @see HealthPotion
 * @see ManaPotion
 * @see AttackElixir
 * @see DefenseElixir
 * @see Lockpick
 * @see WeaponShard
 * @see Ragu
 * @see SpecialKey
 * @see NorthStar
 */
public class GreatManaPotion extends PurchasableItem implements ConsumableItem {
    private static final double WEIGHT = 0.2;
    private static final int PRICE = 20;
    private static final int MANA_RESTORE = 30;
    private static final double DROP_CHANCE = 0.12;
    private static final int DROP_MONEY = PRICE / 2;
    private static final int ID = 4;

    @JsonCreator
    public GreatManaPotion() {
        super(PRICE, WEIGHT, DROP_MONEY, ID, DROP_CHANCE); 
    }
    
    @Override
    public void consume(Character consumer) {
        consumer.restoreMana(MANA_RESTORE);
        if (consumer.getMana() > consumer.getMaxMana()) consumer.setMana(consumer.getMaxMana());
        
        consumer.removeInventoryItem(this);
    }
}
