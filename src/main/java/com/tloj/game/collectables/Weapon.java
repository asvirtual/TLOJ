package com.tloj.game.collectables;

import com.tloj.game.entities.Mob;
import com.tloj.game.utilities.Dice;
import com.tloj.game.abilities.WeaponEffect;
import com.tloj.game.entities.Character;


public abstract class Weapon extends Item {
    protected WeaponEffect ability;
    protected Dice dice;
    protected Character character;

    public Weapon(int weight, Dice dice, Object effect) {
        super(weight, effect);
        this.dice = dice;
    }

    public Dice getDice() {
        return dice;
    }

    public int diceRoll() {
        return dice.roll();
    }

    public void assign(Character character) {
        this.character = character;
    }

    public void swing(Mob target) {
        if (ability == null) target.takeDamage(this.diceRoll());
        else this.ability.apply();
    }
}
