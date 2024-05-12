package com.tloj.game.entities;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.tloj.game.utilities.Constants;
import com.tloj.game.utilities.Coordinates;


/**
 * An abstract class that represents an entity in the game.<br>
 * An entity is anything that can be interacted with in the game.<br>
 * This includes the {@link Character} (the player), the {@link Mob}s and the {@link Boss}es<br>
 */
public abstract class Entity {
    /** The Entity's current {@link Coordinates} in the game */
    protected Coordinates position;

    protected Entity(Coordinates position) {
        this.position = position;
    }

    public Coordinates getPosition() {
        return position;
    }

    public void setPosition(Coordinates position) {
        this.position = position;
    }

    // Returns a string representation of the Entity's health bar
    public String getBar(int current, int max) {
        // Calculate the percentage of the health bar that should be filled
        int percentage = (int) (((double) current / max) * Constants.BAR_LENGTH);   
        StringBuilder bar = new StringBuilder();
        for (int i = 0; i < Constants.BAR_LENGTH; i++) {
            if (i < percentage) bar.append("█");
            else bar.append("░");   
        }
        
        return bar.toString();
    }

    @JsonIgnore
    public abstract String getASCII();
}
