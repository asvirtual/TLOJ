package com.tloj.game.entities;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.tloj.game.game.Attack;
import com.tloj.game.game.Coordinates;


/**
 * An abstract class representing an entity that can either attack and be attacked<br>
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

    protected CombatEntity(int hp, int atk, int def, Coordinates position) {
        super(position);
        this.hp = hp;
        this.maxHp = hp;
        this.atk = atk;
        this.def = def;

        this.currentFightAtk = atk;
        this.currentFightDef = def;
    }

    public int getHp() {
        return this.hp;
    }

    public int getMaxHp() {
        return this.maxHp;
    }

    public void setHp(int hp) {
        this.hp = hp;
    }

    public int getAtk() {
        return this.atk;
    }

    public int getDef() {
        return this.def;
    }
    
    public void heal(int amount) {
        if (this.hp + amount > this.maxHp) this.hp = this.maxHp;
        else this.hp += amount;
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
        if (this.hp - damage < 0) this.hp = 0;
        else this.hp -= damage;
    };

    @JsonIgnore
    public boolean isAlive() {
        return this.hp > 0;
    }

    public void defend(Attack attack) {
        attack.setTargetDef(this.currentFightDef);
    };

    @JsonIgnore
    public String getHpBar() {
        return this.getBar(this.hp, this.maxHp);
    }

    @JsonIgnore
    public String getXpBar() {
        return this.getBar(this.hp, this.maxHp);
    }

    @JsonIgnore
    public abstract String getCombatASCII();
    public abstract void attack(CombatEntity target);
}
