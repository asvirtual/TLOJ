package com.tloj.game.rooms;

import com.tloj.game.utilities.ConsoleColors;
import com.tloj.game.utilities.Coordinates;

import com.fasterxml.jackson.annotation.JsonCreator;
import com.fasterxml.jackson.annotation.JsonProperty;
import com.tloj.game.entities.Character;
import com.tloj.game.collectables.ConsumableItem;
import com.tloj.game.collectables.Item;
import com.tloj.game.collectables.Weapon;
import com.tloj.game.game.PlayerRoomVisitor;

/**
 * Class that represents a loot room in the game.
 * Contains an item ({@link Weapon}, {@link ConsumableItem}) that the player can pick up.
 * 
 * @see Room
 * @see BossRoom
 * @see HealingRoom
 * @see TrapRoom
 * @see HostileRoom
 * @see StartRoom
 * @see EndRoom
 */
public class LootRoom extends Room {
    // The item in the room
    private Item item;

    /**
     * Constructs a LootRoom object with the specified coordinates.
     * The room is initially unlocked and contains a random item.
     * 
     * @param coordinates the coordinates of the room
     */
    public LootRoom(Coordinates coordinates) {
        super(coordinates);
        this.item = Item.getRandomItem();
        this.locked = false;
    }

    /**
     * Constructs a LootRoom object with the specified coordinates and lock status.
     * The room contains a random item.
     * 
     * @param coordinates the coordinates of the room
     * @param locked      the lock status of the room
     */
    public LootRoom(Coordinates coordinates, boolean locked) {
        super(coordinates);
        this.item = Item.getRandomItem();
        this.locked = locked;
    }

    /**
     * Constructs a LootRoom object with the specified coordinates, lock status, and item.
     * If the item is null, a random item will be assigned.
     * 
     * @param coordinates the coordinates of the room
     * @param locked      the lock status of the room
     * @param item        the item in the room
     */
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

    /**
     * Returns the type of the room.
     * 
     * @return the room type
     */
    @Override
    public RoomType getType() {
        return RoomType.LOOT_ROOM;
    }

    /**
     * Accepts a visitor and performs the corresponding action.
     * 
     * @param visitor the visitor object
     */
    @Override
    public void accept(PlayerRoomVisitor visitor) {
        visitor.visit(this);
    }
  
    /**
     * Clears the room and sets the item to null.
     * 
     * @param player the player character
     */
    public void clear(Character player) {
        super.clear(player);
        this.item = null;
    }

    /**
     * Returns the item in the room.
     * 
     * @return the item in the room
     */
    public Item getItem() {
        return this.item;
    }

    /**
     * Returns a string representation of the room.<br>
     * If the room has been visited, it returns a green corner character.
     * Otherwise, it returns a space character.
     * 
     * @return the string representation of the room
     */
    @Override
    public String toString() {
        return this.visited ? ConsoleColors.GREEN + "\u255A" + ConsoleColors.RESET : "\u00A0";
        // return this.visited ? "L" : " ";
    }
}
