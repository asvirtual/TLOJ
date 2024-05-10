package com.tloj.game.rooms.roomeffects;

import com.fasterxml.jackson.annotation.JsonTypeInfo;

import com.tloj.game.entities.Character;


// Needed to serialize/deserialize subclasses of Character, by including the class name in the JSON
@JsonTypeInfo(
  use = JsonTypeInfo.Id.CLASS, 
  include = JsonTypeInfo.As.PROPERTY, 
  property = "@class")

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
