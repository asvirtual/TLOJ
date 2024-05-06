package com.tloj.game.collectables;

import com.tloj.game.utilities.Dice;


public abstract class Weapon extends Item {
    protected Dice dice;

    public Weapon(double weight, int diceFaces) {
        super(weight);
        this.dice = new Dice(diceFaces);
    }

    public abstract void useEffect(Object ...args);
}
