package com.tloj.game.entities;

import com.tloj.game.utilities.Coordinates;


/**
 * An abstract class that represents an entity in the game.<br>
 * An entity is anything that can be interacted with in the game.<br>
 * This includes the {@link Character} (the player), the {@link Mob}s and the {@link Boss}es<br>
 */
public abstract class Entity {
    protected int hp;
    protected int atk;
    protected int def;
    /** The Entity's current {@link Coordinates} in the game */
    protected Coordinates position;

    protected Entity(int hp, int atk, int def, Coordinates position) {
        this.hp = hp;
        this.atk = atk;
        this.def = def;
        this.position = position;
    }
}
