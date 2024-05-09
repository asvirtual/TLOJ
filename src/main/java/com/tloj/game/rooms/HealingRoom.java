package com.tloj.game.rooms;

import com.tloj.game.utilities.Coordinates;
import com.tloj.game.game.PlayerRoomVisitor;

/**
 * Class that represents a healing room in the game<br>
 * It is situated between each floor and allows the player to heal completely<br>
 * You can also buy items from a merchant or upgrade your weapon from a smith<br>
 * @see Room
 * @see BossRoom
 * @see LootRoom
 * @see TrapRoom
 * @see HostileRoom
 * @see StartRoom
 */

public class HealingRoom extends Room {
    /**
     * Default constructor to allow Jackson to deserialize JSON.
     */
    public HealingRoom() {}

    public HealingRoom(Coordinates coordinates) {
        super(coordinates);
    }

    @Override
    public RoomType getType() {
        return RoomType.HEALING_ROOM;
    }

    @Override
    public void accept(PlayerRoomVisitor visitor) {
        visitor.visit(this);
    }

    @Override
    public String toString() {
        return this.visited ? "\u256C" : "\u00A0";
    }
}
