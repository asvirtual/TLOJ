package com.tloj.game.rooms;

import com.tloj.game.utilities.Coordinates;
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
    /**
     * Default constructor to allow Jackson to deserialize JSON.
     */
    public StartRoom() {}

    public StartRoom(Coordinates coordinates) {
        super(coordinates);
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
        return this.visited ? "\u2229" : "\u00A0";
    }
}
