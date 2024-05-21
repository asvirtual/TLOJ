package com.tloj.game.rooms;

import com.tloj.game.utilities.ConsoleHandler;
import com.tloj.game.utilities.Coordinates;
import com.tloj.game.game.PlayerRoomVisitor;

import com.fasterxml.jackson.annotation.JsonCreator;
import com.fasterxml.jackson.annotation.JsonProperty;

/**
 * Class that represents the start room in the game<br>
 * @see Room
 * @see BossRoom
 * @see HealingRoom
 * @see LootRoom
 * @see TrapRoom
 * @see HostileRoom
 */

/**
 * Represents the starting room in the game.<br>
 * This room is always visited since the game starts here.
 */
public class StartRoom extends Room {

    /**
     * Constructs a StartRoom object with the specified coordinates.
     *
     * @param coordinates the coordinates of the room
     */
    @JsonCreator
    public StartRoom(
        @JsonProperty("coordinates") Coordinates coordinates
    ) {
        super(coordinates);
        this.visited = true; // StartRoom is always visited, since we start here
    }

    /**
     * Returns the type of the room.
     *
     * @return the type of the room
     */
    @Override
    public RoomType getType() {
        return RoomType.START_ROOM;
    }

    /**
     * Accepts a visitor and calls the appropriate visit method.
     *
     * @param visitor the visitor to accept
     */
    @Override
    public void accept(PlayerRoomVisitor visitor) {
        visitor.visit(this);
    }

    /**
     * Returns a string representation of the room.
     * If the room is visited, it returns a yellow triangle symbol, otherwise a space.
     *
     * @return a string representation of the room
     */
    @Override
    public String toString() {
        return this.visited ? this.getRoomRepresentation() : "\u00A0";
        // return this.visited ? "S" : " ";
    }

    @Override
    public String getRoomRepresentation() {
        return ConsoleHandler.YELLOW + "\u2229" + ConsoleHandler.RESET;
    }
}
