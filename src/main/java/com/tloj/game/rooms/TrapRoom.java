package com.tloj.game.rooms;

import com.tloj.game.utilities.Coordinates;
import com.tloj.game.utilities.Dice;
import com.tloj.game.game.PlayerRoomVisitor;
import com.tloj.game.rooms.roomeffects.RoomEffect;
import com.tloj.game.entities.Character;

/**
 * Class that represents a trap room in the game<br>
 * There is a trap in the room that will trigger when the player enters it<br>
 * The effects are either damage or stealing money, which can be overcome by the player's {@link Dice} roll<br>
 * @see Room
 * @see BossRoom
 * @see HealingRoom
 * @see LootRoom
 * @see StartRoom
 * @see HostileRoom
 */
public class TrapRoom extends Room {
    private RoomEffect effect;

    public TrapRoom(Coordinates coordinates, RoomEffect effect) {
        super(coordinates);
        this.effect = effect;
    }

    @Override
    public RoomType getType() {
        return RoomType.TRAP_ROOM;
    }

    public void triggerTrap(Character character) {
        this.effect.applyEffect(character);
    }

    @Override
    public void accept(PlayerRoomVisitor visitor) {
        visitor.visit(this);
    }

    @Override
    public String toString() {
        return this.visited ? "\u2566" : "\u00A0";
    }
}
