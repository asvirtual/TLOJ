package com.tloj.game.collectables;

import com.tloj.game.utilities.Dice;


public abstract class Weapon extends Item {
    protected Object ability;
    protected Dice dice;

    public Weapon(double weight, Dice dice, Object effect) {
        super(weight, effect);
        this.dice = dice;
    }
}
