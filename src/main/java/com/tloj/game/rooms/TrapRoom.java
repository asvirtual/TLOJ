package com.tloj.game.rooms;

import com.tloj.game.utilities.ConsoleColors;
import com.tloj.game.utilities.Coordinates;
import com.tloj.game.utilities.Dice;
import com.tloj.game.game.PlayerRoomVisitor;
import com.tloj.game.rooms.roomeffects.RoomEffect;

import java.util.ArrayList;

import com.fasterxml.jackson.annotation.JsonCreator;
import com.fasterxml.jackson.annotation.JsonProperty;
import com.tloj.game.entities.Character;
import com.tloj.game.entities.Mob;

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
    @JsonProperty
    private RoomEffect effect;

    @JsonCreator
    public TrapRoom(
        @JsonProperty("coordinates") Coordinates coordinates, 
        @JsonProperty("effect") RoomEffect effect
    ) {
        super(coordinates);
        this.effect = effect;
    }

    @Override
    public RoomType getType() {
        return RoomType.TRAP_ROOM;
    }

    public boolean triggerTrap(Character character) {
        return this.effect.applyEffect(character);
    }

    public void executeSideEffect() {
        this.effect.executeSideEffect();
    }

    @Override
    public void accept(PlayerRoomVisitor visitor) {
        visitor.visit(this);
    }

    @Override
    public void clear(Character player) {
        super.clear(player);
        player.heal(1);
        player.restoreMana(1);
    }

    @Override
    public String toString() {
        return this.visited ? ConsoleColors.PURPLE + "\u2566" + ConsoleColors.RESET : "\u00A0";
        // return this.visited ? "T" : " ";
    }
}
