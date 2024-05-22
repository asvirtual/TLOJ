package com.tloj.game.rooms;

import java.util.ArrayList;

import com.fasterxml.jackson.annotation.JsonCreator;
import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonProperty;
import com.tloj.game.entities.Mob;
import com.tloj.game.entities.Character;
import com.tloj.game.utilities.ConsoleHandler;
import com.tloj.game.game.Coordinates;
import com.tloj.game.game.PlayerRoomVisitor;

/**
 * Class that represents a hostile room in the game<br>
 * Contains the mobs of the room<br>
 * Inherits from the Room class.
 * @see Room
 * @see BossRoom
 * @see HealingRoom
 * @see LootRoom
 * @see TrapRoom
 * @see StartRoom
 * @see EndRoom
 */

public class HostileRoom extends Room {
    /**
     * The list of mobs present in the room.
     */
    @JsonProperty
    protected ArrayList<Mob> mobs;

    /**
     * Constructs a HostileRoom object with the specified coordinates.
     * Initializes the mobs list as an empty ArrayList.
     *
     * @param coordinates The coordinates of the room.
     */
    public HostileRoom(Coordinates coordinates) {
        super(coordinates);
        this.mobs = new ArrayList<Mob>();
    }

    /**
     * Constructs a HostileRoom object with the specified coordinates and mobs.
     *
     * @param coordinates The coordinates of the room.
     * @param mobs        The list of mobs present in the room.
     */
    @JsonCreator
    public HostileRoom(
            @JsonProperty("coordinates") Coordinates coordinates,
            @JsonProperty("mobs") ArrayList<Mob> mobs
    ) {
        super(coordinates);
        this.mobs = mobs;
    }

    /**
     * Constructs a HostileRoom object with the specified coordinates and a single mob.
     * Initializes the mobs list with the provided mob.
     *
     * @param coordinates The coordinates of the room.
     * @param mob         The mob to add to the room.
     */
    public HostileRoom(Coordinates coordinates, Mob mob) {
        super(coordinates);
        this.mobs = new ArrayList<Mob>();
        this.mobs.add(mob);
    }

    /**
     * Returns the type of the room.
     *
     * @return The type of the room.
     */
    @Override
    public RoomType getType() {
        return RoomType.HOSTILE_ROOM;
    }

    /**
     * Accepts a PlayerRoomVisitor and calls the visit method for this HostileRoom.
     *
     * @param visitor The PlayerRoomVisitor to accept.
     */
    @Override
    public void accept(PlayerRoomVisitor visitor) {
        visitor.visit(this);
    }

    /**
     * Returns the first mob in the room.
     *
     * @return The first mob in the room.
     */
    @JsonIgnore
    public Mob getMob() {
        if (this.mobs.isEmpty()) return null;            
        
        return this.mobs.get(0);
    }

    /**
     * Returns the number of mobs in the room.
     *
     * @return The number of mobs in the room.
     */
    @JsonIgnore
    public int getMobsCount() {
        return this.mobs.size();
    }

    /**
     * Removes the specified mob from the room.
     *
     * @param mob The mob to remove.
     */
    public void removeMob(Mob mob) {
        this.mobs.remove(mob);
    }

    /**
     * Adds the specified mob to the top of the mobs list.
     *
     * @param mob The mob to add.
     */
    public void addMobToTop(Mob mob) {
        this.mobs.add(0, mob);
    }

    /**
     * Clears the room, removing all mobs and performing additional actions on the player.
     *
     * @param player The player character.
     */
    public void clear(Character player) {
        super.clear(player);
        player.heal(1);
        player.restoreMana(1);
        this.mobs.clear();
    }

    /**
     * Returns a string representation of the room.
     * If the room has been visited, returns a red square symbol, otherwise returns a space character.
     *
     * @return A string representation of the room.
     */
    @Override
    public String toString() {
        return this.visited ? this.getRoomRepresentation() : "\u00A0";
    }

    @Override
    public String getRoomRepresentation() {
        return ConsoleHandler.RED + "\u25A0" + ConsoleHandler.RESET;
    }
}
