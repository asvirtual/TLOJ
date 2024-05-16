package com.tloj.game.rooms;

import java.util.ArrayList;

import com.tloj.game.utilities.Coordinates;
import com.fasterxml.jackson.annotation.JsonCreator;
import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonProperty;
import com.tloj.game.entities.Boss;
import com.tloj.game.entities.Mob;
import com.tloj.game.game.PlayerRoomVisitor;

/**
 * Class that represents a boss room in the game<br>
 * Contains the boss of the floor and is always the last room of the floor<br>
 * @see Room
 * @see HealingRoom
 * @see LootRoom
 * @see TrapRoom
 * @see HostileRoom
 * @see StartRoom
 * @see EndRoom
 */
/**
 * Represents a room in the game that contains a boss.
 */
public class BossRoom extends HostileRoom {
    /**
     * Constructs a BossRoom object with the given coordinates and bosses.
     *
     * @param coordinates The coordinates of the room.
     * @param bosses The bosses in the room.
     */
    @JsonCreator
    public BossRoom(
        @JsonProperty("coordinates") Coordinates coordinates, 
        @JsonProperty("mobs") ArrayList<Boss> bosses
    ) {
        super(coordinates, new ArrayList<Mob>(bosses));
    }

    /**
     * Constructs a BossRoom object with the given coordinates and boss.
     *
     * @param coordinates The coordinates of the room.
     * @param boss The boss in the room.
     */
    public BossRoom(
        Coordinates coordinates, 
        Boss boss
    ) {
        super(coordinates, boss);
    }

    /**
     * Retrieves the boss in the room.
     *
     * @return The boss in the room.
     */
    @JsonIgnore
    public Boss getBoss() {
        return (Boss) this.mobs.get(0);
    }

    /**
     * Returns the type of the room.
     *
     * @return The type of the room.
     */
    @Override
    public RoomType getType() {
        return RoomType.BOSS_ROOM;
    }

    /**
     * Accepts a player room visitor and performs the corresponding action.
     *
     * @param visitor The player room visitor.
     */
    @Override
    public void accept(PlayerRoomVisitor visitor) {
        visitor.visit(this);
    }

    /**
     * Returns a string representation of the room.
     *
     * @return A string representation of the room.
     */
    @Override
    public String toString() {
        return this.visited ? this.getRoomRepresentation() : "\u00A0";
        // return this.visited ? "B" : " ";
    }

    @Override
    public String getRoomRepresentation() {
        return "\u00DF";
    }
}
