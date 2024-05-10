package com.tloj.game.rooms;

import com.tloj.game.utilities.Coordinates;

import java.util.ArrayList;

import com.fasterxml.jackson.annotation.JsonCreator;
import com.fasterxml.jackson.annotation.JsonProperty;
import com.tloj.game.collectables.ConsumableItem;
import com.tloj.game.collectables.Item;
import com.tloj.game.collectables.Weapon;
import com.tloj.game.entities.Mob;
import com.tloj.game.game.PlayerRoomVisitor;

/**
 * Class that represents a loot room in the game<br>
 * Contains an item ({@link Weapon}, {@link ConsumableItem})that the player can pick up<br>
 * @see Room
 * @see BossRoom
 * @see HealingRoom
 * @see TrapRoom
 * @see HostileRoom
 * @see StartRoom
 */

public class LootRoom extends Room {
    private Item item;

    public LootRoom(Coordinates coordinates) {
        super(coordinates);
        this.item = Item.getRandomItem();
        this.locked = false;
    }

    public LootRoom(Coordinates coordinates, boolean locked) {
        super(coordinates);
        this.item = Item.getRandomItem();
        this.locked = locked;
    }

    @JsonCreator
    public LootRoom(
        @JsonProperty("coordinates") Coordinates coordinates, 
        @JsonProperty("locked") boolean locked,
        @JsonProperty("item") Item item
    ) {
        super(coordinates);
        this.item = item == null ? Item.getRandomItem() : item;
        this.locked = locked;
    }

    @Override
    public RoomType getType() {
        return RoomType.LOOT_ROOM;
    }

    @Override
    public void accept(PlayerRoomVisitor visitor) {
        visitor.visit(this);
    }
  
    public void clear() {
        super.clear();
        this.item = null;
    }

    public Item getItem() {
        return this.item;
    }

    @Override
    public String toString() {
        // return this.visited ? "\u255A" : "\u00A0";
        return this.visited ? "L" : "*";
    }
}
