package com.tloj.game.collectables;

import com.tloj.game.utilities.Dice;


public abstract class Weapon {
    protected Object ability;
    protected Dice dice;

    public Weapon(Dice dice, Object ability) {  //should we add weight to the weapons?
        this.ability = ability;
        this.dice = dice;
    }
}
