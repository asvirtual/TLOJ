package com.tloj.game.entities;

import com.tloj.game.utilities.Coordinates;


public abstract class Mob extends Entity implements CombatEntity {
    protected int lvl;
    protected int xpDrop;
    protected int moneyDrop;
    
    protected Mob(
        int hp,
        int atk,
        int def,
        int lvl,
        int xpDrop,
        int moneyDrop,
        Coordinates position
    ) {
        super(hp, atk, def, position);
        this.lvl = lvl;
        this.xpDrop = xpDrop;
        this.moneyDrop = moneyDrop;
    }

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
