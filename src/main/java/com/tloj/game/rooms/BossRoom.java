package com.tloj.game.rooms;

import java.util.ArrayList;

import com.tloj.game.utilities.Coordinates;
import com.fasterxml.jackson.annotation.JsonCreator;
import com.fasterxml.jackson.annotation.JsonProperty;
import com.tloj.game.abilities.BossAbility;
import com.tloj.game.entities.Boss;
import com.tloj.game.entities.Mob;
import com.tloj.game.game.PlayerRoomVisitor;

/**
 * Class that represents a boss room in the game<br>
 * Boss Room is an extension of the Hostile Room, as Boss'es are hostile mobs{@link BossAbility}<br>
 * Contains the boss of the floor and is always the last room of the floor<br>
 * @see Room
 * @see HealingRoom
 * @see LootRoom
 * @see TrapRoom
 * @see HostileRoom
 * @see StartRoom
 */
public class BossRoom extends HostileRoom {
    @JsonCreator
    public BossRoom(
        @JsonProperty("coordinates") Coordinates coordinates, 
        @JsonProperty("bosses") ArrayList<Mob> bosses
    ) {
        super(coordinates, bosses);
    }

    public BossRoom(
        Coordinates coordinates, 
        Boss boss
    ) {
        super(coordinates, boss);
    }

    public Boss getBoss() {
        return (Boss) this.mobs.get(0);
    }

    @Override
    public RoomType getType() {
        return RoomType.BOSS_ROOM;
    }

    @Override
    public void accept(PlayerRoomVisitor visitor) {
        visitor.visit(this);
    }

    @Override 
    public void clear() {
        super.clear();
        this.mobs.set(0, null);
    }

    @Override
    public String toString() {
        // return this.visited ? "\u00DF" : "\u00A0";
        return this.visited ? "B" : " ";
    }
}
