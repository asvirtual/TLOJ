package com.tloj.game.entities;

import com.tloj.game.entities.bosses.Boss;
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
}
