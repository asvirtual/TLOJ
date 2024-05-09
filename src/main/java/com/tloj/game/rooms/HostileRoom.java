package com.tloj.game.rooms;

import java.util.ArrayList;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonProperty;
import com.tloj.game.entities.Mob;
import com.tloj.game.utilities.Coordinates;
import com.tloj.game.game.PlayerRoomVisitor;

/**
 * Class that represents a hostile room in the game<br>
 * Contains the mobs of the room<br>
 * @see Room
 * @see BossRoom
 * @see HealingRoom
 * @see LootRoom
 * @see TrapRoom
 * @see StartRoom
 */

public class HostileRoom extends Room {
    @JsonProperty
    protected ArrayList<Mob> mobs;

    /**
     * Default constructor to allow Jackson to deserialize JSON.
     */
    public HostileRoom() {}

    public HostileRoom(Coordinates coordinates) {
        super(coordinates);
        this.mobs = new ArrayList<Mob>();
    }

    public HostileRoom(Coordinates coordinates, ArrayList<Mob> mobs) {
        super(coordinates);
        this.mobs = mobs;
    }

    public HostileRoom(Coordinates coordinates, Mob mob) {
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

    @Override
    public void exit() {
        // TODO Auto-generated method stub
        throw new UnsupportedOperationException("Unimplemented method 'exit'");
    }

    @Override
    public String toString() {
        if(this.isVisited())
            return "\u25A0" + " ";
        else
            return "\u00A0" + " ";
    }

    @JsonIgnore
    public Mob getMob() {
        return this.mobs.get(0);
    }

    public void removeMob(Mob mob) {
        this.mobs.remove(mob);
    }

    public void addMobTop(Mob mob) {
        this.mobs.add(0, mob);
    }

    public void clear() {
        super.clear();
        for (Mob mob : this.mobs) this.removeMob(mob);
    }
}
