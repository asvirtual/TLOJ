package com.tloj.game.entities;

import com.tloj.game.game.Attack;
import com.tloj.game.utilities.Coordinates;


/**
 * An abstract class representing an entity that can attack and be attacker<br>
 * This includes the {@link Character} (the player), the {@link Mob}s and the {@link Boss}es<br>
 * All combat entities have a health pool, an attack stat and a defense stat<br>
 */
public abstract class CombatEntity extends Entity {
    protected int hp;
    protected int atk;
    protected int def;

    protected CombatEntity(int hp, int atk, int def, Coordinates position) {
        super(position);
        this.hp = hp;
        this.atk = atk;
        this.def = def;
    }

    public int getAtk() {
        return atk;
    }

    public int getDef() {
        return def;
    }

    public void takeDamage(int damage) {
        this.hp -= damage;
        if (this.hp <= 0) 
            this.die();
    };

    public boolean isAlive() {
        return this.hp > 0;
    }

    public void defend(Attack attack) {
        attack.setTargetDef(this.def);
    };

    public abstract void attack(CombatEntity target);
    public abstract void die();
}
