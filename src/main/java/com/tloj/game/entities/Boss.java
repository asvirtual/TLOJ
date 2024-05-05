package com.tloj.game.entities;

import com.tloj.game.utilities.Coordinates;


public abstract class Boss extends Mob {
    protected Object ability;

    protected Boss(
        int hp,
        int atk,
        int def,
        int diceFaces,
        int lvl,
        int xpDrop,
        int moneyDrop,
        Object ability,
        Coordinates position
    ) {
        super(hp, atk, def, diceFaces, lvl, xpDrop, moneyDrop, position);
        this.ability = ability;
    }

    /* Overrides of the CombatEntity method will be needed as the combat dinamycs of the Boss differ from the Mob's */

    @Override
    public void attack(CombatEntity target) {
        target.takeDamage(this.atk);
    }

    @Override
    public void defend() {
        
    }

    @Override
    public void takeDamage(int damage) {
        this.hp -= damage;
    }

    @Override
    public void die() {
        
    }
}
