package com.tloj.game.rooms;

import com.tloj.game.utilities.ConsoleColors;
import com.tloj.game.utilities.Coordinates;
import com.tloj.game.game.PlayerRoomVisitor;
import com.tloj.game.rooms.roomeffects.RoomEffect;
import com.tloj.game.entities.Character;

import com.fasterxml.jackson.annotation.JsonCreator;
import com.fasterxml.jackson.annotation.JsonProperty;


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
/**
 * Represents a trap room in the game.
 * Trap rooms have a specific effect that can be triggered by a character.
 * They also have a side effect that can be executed.
 */
public class TrapRoom extends Room {
    //The effect of the trap room.
    @JsonProperty
    private RoomEffect effect;

    /**
     * Constructs a new TrapRoom object.
     *
     * @param coordinates The coordinates of the trap room.
     * @param effect      The effect of the trap room.
     */
    @JsonCreator
    public TrapRoom(
        @JsonProperty("coordinates") Coordinates coordinates,
        @JsonProperty("effect") RoomEffect effect
    ) {
        super(coordinates);
        this.effect = effect;
    }

    /**
     * Gets the type of the room.
     *
     * @return The room type (TRAP_ROOM).
     */
    @Override
    public RoomType getType() {
        return RoomType.TRAP_ROOM;
    }

    /**
     * Triggers the trap effect on a character.
     *
     * @param character The character triggering the trap.
     * @return true if the trap effect was successfully applied, false otherwise.
     */
    public boolean triggerTrap(Character character) {
        return this.effect.applyEffect(character);
    }

    /**
     * Executes the side effect of the trap room.
     */
    public void executeSideEffect() {
        this.effect.executeSideEffect();
    }

    /**
     * Accepts a player room visitor.
     *
     * @param visitor The player room visitor.
     */
    @Override
    public void accept(PlayerRoomVisitor visitor) {
        visitor.visit(this);
    }

    /**
     * Clears the trap room and performs additional actions on the player.
     *
     * @param player The player character.
     */
    @Override
    public void clear(Character player) {
        super.clear(player);
        player.restoreMana(1);
    }

    /**
     * Returns a string representation of the trap room.
     *
     * @return The string representation of the trap room.
     */
    @Override
    public String toString() {
        return this.visited ? this.getRoomRepresentation() : "\u00A0";
        // return this.visited ? "T" : " ";
    }

    @Override
    public String getRoomRepresentation() {
        return ConsoleColors.PURPLE + "\u2566" + ConsoleColors.RESET;
    }
}
