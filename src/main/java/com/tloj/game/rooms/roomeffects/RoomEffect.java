package com.tloj.game.rooms.roomeffects;

import com.tloj.game.entities.Character;
/**
 * Interface for room effects<br>
 * This interface is meant to be implemented by classes that represent effects that can be applied to a character when they enter a room, guaranteeing modularity.<br>
 * @see InflictDamage
 * @see StealMoney
 * @see TpEffect
 */


public interface RoomEffect {
    public void applyEffect(Character character);
}
