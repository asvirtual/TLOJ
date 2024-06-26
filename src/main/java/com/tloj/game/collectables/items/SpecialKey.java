package com.tloj.game.collectables.items;

import com.tloj.game.collectables.ConsumableItem;
import com.tloj.game.collectables.Item;
import com.tloj.game.entities.Character;
import com.tloj.game.utilities.Constants;

import com.fasterxml.jackson.annotation.JsonCreator;


/**
 * Represents the Consumable Item Special Key in the game.<br>
 * The Special Key opens a special locked loot room, it can be found or purchased in the game.<br>
 * It weighs {@value #WEIGHT}, with an in game cost of D{@value #PRICE}
 * @see GreatHealthPotion
 * @see HealthPotion
 * @see ManaPotion
 * @see GreatManaPotion
 * @see AttackElixir
 * @see DefenseElixir
 * @see Emp
 * @see WeaponShard
 * @see Ragu
 * @see NorthStar
 */

public class SpecialKey extends Item implements ConsumableItem {
    private static final double WEIGHT = 0;
    private static final int ID = 11;    

    @JsonCreator
    public SpecialKey() {
        super(WEIGHT, ID); 
    }
    
    /**
     * Consumes the Special Key, removing it from the inventory of the character.<br>
     * The logic to open the special loot room is implemented in the {@link com.tloj.game.game.PlayerRoomVisitor#visit(com.tloj.game.rooms.LootRoom)} method
     * @param consumer The character that consumes the item
     */
    @Override
    public void consume(Character consumer) {
        consumer.removeInventoryItem(this);
    }

    @Override
    public String getASCII() {
        return Constants.SPECIAL_KEY;
    }

    @Override
    public String describe() {
        return this.getASCII() + "\n" +
                "An ancient key, guarded by the lost glitched adventurer, that opens a special locked loot room somewhere in the dungeon..."
                + "\n This item has no weight.";
    }

    @Override
    public boolean equals(Object obj) {
        return obj instanceof SpecialKey;
    }
}

