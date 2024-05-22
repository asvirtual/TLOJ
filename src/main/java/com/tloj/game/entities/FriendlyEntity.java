package com.tloj.game.entities;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonProperty;
import com.fasterxml.jackson.annotation.JsonTypeInfo;
import com.tloj.game.game.Coordinates;


// Needed to serialize/deserialize subclasses of Character, by including the class name in the JSON
@JsonTypeInfo(
  use = JsonTypeInfo.Id.CLASS, 
  include = JsonTypeInfo.As.PROPERTY, 
  property = "@class")


/**
 *An abstract class representing a friendly entity in the game.<br>
*@see Merchant
*@see Smith
*/  

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

    public void interact(Character player) {
        this.player = player;
    }

    public void endInteraction() {
        this.player = null;
    }

    public String getName() {
        return this.name;
    }
}
