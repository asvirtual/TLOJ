package com.tloj.game.rooms;

import java.util.ArrayList;

import com.fasterxml.jackson.annotation.JsonCreator;
import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonProperty;
import com.tloj.game.entities.Mob;
import com.tloj.game.utilities.Coordinates;
import com.tloj.game.game.PlayerRoomVisitor;

/**
 * Class that represents a hostile room in the game<br>
 * Contains the mobs of the room<br>
 * @see BossRoom
 * @see HealingRoom
 * @see LootRoom
 * @see TrapRoom
 * @see StartRoom
 */

public class HostileRoom extends Room {
    @JsonProperty
    protected ArrayList<Mob> mobs;

    public HostileRoom(Coordinates coordinates) {
        super(coordinates);
        this.mobs = new ArrayList<Mob>();
    }

    public HostileRoom(
        @JsonProperty("coordinates") Coordinates coordinates, 
        @JsonProperty("mobs") ArrayList<Mob> mobs
    ) {
        super(coordinates);
        this.mobs = mobs;
    }

    public HostileRoom(Coordinates coordinates,  Mob mob) {
        super(coordinates);
        this.mobs = new ArrayList<Mob>();
        this.mobs.add(mob);
    }

    @Override
    public RoomType getType() {
        return RoomType.HOSTILE_ROOM;
    }

    @Override
    public void accept(PlayerRoomVisitor visitor) {
        visitor.visit(this);
    }

    @JsonIgnore
    public Mob getMob() {
        return this.mobs.get(0);
    }

    @JsonIgnore
    public int getMobsCount() {
        return this.mobs.size();
    }

    public void removeMob(Mob mob) {
        this.mobs.remove(mob);
    }

    public void addMobToTop(Mob mob) {
        this.mobs.add(0, mob);
    }

    public void clear() {
        super.clear();
        this.mobs.clear();
    }
    
    @Override
    public String toString() {
        // return this.visited ? "\u25A0" : "\u00A0";
        return this.visited ? "M" : "*";
    }
}
