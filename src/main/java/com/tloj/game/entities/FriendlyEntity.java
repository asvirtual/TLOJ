package com.tloj.game.entities;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonProperty;
import com.fasterxml.jackson.annotation.JsonTypeInfo;
import com.tloj.game.game.Coordinates;


/**
 * An abstract class representing a friendly entity in the game.<br>
 * @see com.tloj.game.entities.npcs.Merchant
 * @see com.tloj.game.entities.npcs.Smith
 */  
// Needed to serialize/deserialize subclasses of Character, by including the class name in the JSON
@JsonTypeInfo(
  use = JsonTypeInfo.Id.CLASS, 
  include = JsonTypeInfo.As.PROPERTY, 
  property = "@class")
public abstract class FriendlyEntity extends Entity {
    @JsonIgnore
    protected Character player;
    @JsonProperty
    protected String name;

    protected FriendlyEntity(Coordinates position) {
        super(position);
    }

    protected FriendlyEntity(Coordinates position, String name) {
        super(position);
        this.name = name;
    }

    /**
     * Starts an interaction with the player.
     * @param player The player interacting with the entity.
     */
    public void interact(Character player) {
        this.player = player;
    }

    /**
     * Ends the interaction with the player.
     */
    public void endInteraction() {
        this.player = null;
    }

    @Override
    public String getName() {
        return this.name;
    }
}
