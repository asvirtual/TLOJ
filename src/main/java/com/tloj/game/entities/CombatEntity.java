package com.tloj.game.entities;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.tloj.game.game.Attack;
import com.tloj.game.utilities.Coordinates;


/**
 * An abstract class representing an entity that can attack and be attacker<br>
 * This includes the {@link Character} (the player), the {@link Mob}s and the {@link Boss}es<br>
 * All combat entities have a health pool, an attack stat and a defense stat<br>
 */
public abstract class CombatEntity extends Entity {
    protected int hp;
    protected int maxHp;
    protected int atk;
    protected int def;
    protected int currentFightAtk;
    protected int currentFightDef;

    /**
     * Default constructor to allow Jackson to deserialize JSON.
     */
    public CombatEntity() {}

    protected CombatEntity(int hp, int atk, int def, Coordinates position) {
        super(position);
        this.hp = hp;
        this.maxHp = hp;
        this.atk = atk;
        this.def = def;
    }

    public int getAtk() {
        return this.atk;
    }

    public int getDef() {
        return this.def;
    }

    public int getCurrentFightAtk() {
        return this.currentFightAtk;
    }

    public int getCurrentFightDef() {
        return this.currentFightDef;
    }

    public void resetFightStats() {
        this.currentFightAtk = this.atk;
        this.currentFightDef = this.def;
    }

    public void setCurrentFightAtk(int atk) {
        this.currentFightAtk = atk;
    }

    public void setCurrentFightDef(int def) {
        this.currentFightDef = def;
    }

    public void takeDamage(int damage) {
        this.hp -= damage;
        if (this.hp <= 0) 
            this.die();
    };

    @JsonIgnore
    public boolean isAlive() {
        return this.hp > 0;
    }

    public void defend(Attack attack) {
        attack.setTargetDef(this.currentFightDef);
    };

    public abstract void attack(CombatEntity target);
    public abstract void die();
}
