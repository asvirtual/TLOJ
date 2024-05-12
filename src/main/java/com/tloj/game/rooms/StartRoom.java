package com.tloj.game.rooms;

import com.tloj.game.utilities.ConsoleColors;
import com.tloj.game.utilities.Coordinates;

import java.util.ArrayList;

import com.fasterxml.jackson.annotation.JsonCreator;
import com.fasterxml.jackson.annotation.JsonProperty;
import com.tloj.game.entities.Mob;
import com.tloj.game.game.PlayerRoomVisitor;

/**
 * Class that represents the start room in the game<br>
 * @see Room
 * @see BossRoom
 * @see HealingRoom
 * @see LootRoom
 * @see TrapRoom
 * @see HostileRoom
 */

public class StartRoom extends Room {
    @JsonCreator
    public StartRoom(
        @JsonProperty("coordinates") Coordinates coordinates
    ) {
        super(coordinates);
        this.visited = true; // StartRoom is always visited, since we start here
    }

    @Override
    public RoomType getType() {
        return RoomType.START_ROOM;
    }

    @Override
    public void accept(PlayerRoomVisitor visitor) {
        visitor.visit(this);
    }

    @Override
    public String toString() {
        return this.visited ? ConsoleColors.YELLOW + "\u2229" + ConsoleColors.RESET : "\u00A0";
        // return this.visited ? "S" : " ";
    }
}
