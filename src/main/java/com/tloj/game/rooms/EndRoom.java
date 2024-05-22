package com.tloj.game.rooms;

import com.fasterxml.jackson.annotation.JsonCreator;
import com.fasterxml.jackson.annotation.JsonProperty;
import com.tloj.game.game.Coordinates;
import com.tloj.game.game.PlayerRoomVisitor;

/**
 * Represents the end room in the game.<br>
 * This room marks the end of the game.
 * Inherits from the StartRoom class.
 * @see Room
 * @see BossRoom
 * @see LootRoom
 * @see TrapRoom
 * @see HostileRoom
 * @see HealingRoom
 * @see StartRoom
 */
public class EndRoom extends StartRoom {

    /**
     * Constructs a new EndRoom object with the specified coordinates.
     *
     * @param coordinates the coordinates of the room
     */
    @JsonCreator
    public EndRoom(@JsonProperty("coordinates") Coordinates coordinates) {
        super(coordinates);
    }

    /**
     * Returns the type of the room.
     *
     * @return the type of the room
     */
    @Override
    public RoomType getType() {
        return RoomType.END_ROOM;
    }

    /**
     * Accepts a visitor and performs an action based on the visitor's type.
     *
     * @param visitor the visitor to accept
     */
    @Override
    public void accept(PlayerRoomVisitor visitor) {
        visitor.visit(this);
    }

    /**
     * Returns a string representation of the room.
     *
     * @return a string representation of the room
     */
    @Override
    public String toString() {
        return "E";
    }

    @Override
    public String getRoomRepresentation() {
        return "E";
    }
}
