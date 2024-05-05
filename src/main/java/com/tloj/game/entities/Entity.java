package com.tloj.game.entities;

import com.tloj.game.utilities.Coordinates;


public abstract class Entity {
    protected int hp;
    protected int atk;
    protected int def;
    protected Coordinates position;

    protected Entity(int hp, int atk, int def, Coordinates position) {
        this.hp = hp;
        this.atk = atk;
        this.def = def;
        this.position = position;
    }
}
