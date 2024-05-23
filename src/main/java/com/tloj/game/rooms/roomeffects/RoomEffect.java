package com.tloj.game.rooms.roomeffects;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonTypeInfo;

import com.tloj.game.entities.Character;




/**
 * Interface for room effects<br>
 * This interface is meant to be implemented by classes that represent effects that can be applied to a character when they enter a room, guaranteeing modularity.<br>
 * @see InflictDamage
 * @see StealMoney
 * @see Teleport
 */

// Needed to serialize/deserialize subclasses of Character, by including the class name in the JSON
@JsonTypeInfo(
  use = JsonTypeInfo.Id.CLASS, 
  include = JsonTypeInfo.As.PROPERTY, 
  property = "@class")

public abstract class RoomEffect {
    // Runnable side effect to be executed when the effect is applied
    protected Runnable sideEffect;

    protected RoomEffect() {}

    public void executeSideEffect() {
        if (sideEffect != null) sideEffect.run();
    };

    @JsonIgnore
    public abstract String getASCII();
    public abstract boolean applyEffect(Character character);
}
