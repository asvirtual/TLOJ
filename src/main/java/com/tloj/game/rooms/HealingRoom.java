package com.tloj.game.rooms;

import com.tloj.game.utilities.Coordinates;

import com.fasterxml.jackson.annotation.JsonCreator;
import com.fasterxml.jackson.annotation.JsonProperty;
import com.tloj.game.entities.FriendlyEntity;
import com.tloj.game.entities.npcs.Merchant;
import com.tloj.game.entities.npcs.Smith;
import com.tloj.game.game.PlayerRoomVisitor;

/**
 * Class that represents a healing room in the game.<br>
 * It is situated between each floor and allows the player to heal completely and meet some {@link FriendlyEntity}.<br>
 * You can also buy items from {@link Merchant} or upgrade your weapon from a {@link Smith}<br>
 * @see Room
 * @see BossRoom
 * @see LootRoom
 * @see TrapRoom
 * @see HostileRoom
 * @see StartRoom
 * @see EndRoom
 */

public class HealingRoom extends StartRoom {

    /**
     * Constructs a Healing Room object with the given coordinates.
     *
     * @param coordinates the coordinates of the Healing Room
     */
    @JsonCreator
    public HealingRoom(
        @JsonProperty("coordinates") Coordinates coordinates
    ) {
        super(coordinates);
        this.friendlyEntities.add(new Smith(this.coordinates));
        this.friendlyEntities.add(new Merchant(this.coordinates));
    }

    /**
     * Returns the type of the room.
     *
     * @return the type of the room
     */
    @Override
    public RoomType getType() {
        return RoomType.HEALING_ROOM;
    }

    /**
     * Accepts a PlayerRoomVisitor and calls the appropriate visit method.
     *
     * @param visitor the PlayerRoomVisitor to accept
     */
    @Override
    public void accept(PlayerRoomVisitor visitor) {
        visitor.visit(this);
    }

    /**
     * Returns a string representation of the Healing Room.
     * If the room has been visited, it returns the Unicode character for a horizontal line.
     * If the room has not been visited, it returns a space character.
     *
     * @return a string representation of the Healing Room
     */
    @Override
    public String toString() {
        return this.visited ? this.getRoomRepresentation() : "\u00A0";
        // return this.visited ? "H" : " ";
    }
    
    @Override
    public String getRoomRepresentation() {
        return "\u256C";
    }
}
