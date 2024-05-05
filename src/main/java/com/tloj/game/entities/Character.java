package com.tloj.game.entities;

import java.util.ArrayList;

import com.tloj.game.collectables.Item;
import com.tloj.game.collectables.Weapon;
import com.tloj.game.utilities.Coordinates;


public abstract class Character extends Entity implements MovingEntity, CombatEntity {
    protected int mana;
    protected int xp;
    protected int lvl;
    protected int maxWeight;
    protected int money;
    protected ArrayList<Item> inventory;
    protected Weapon weapon;

    protected Object ability;
    protected Object passiveAbility;

    protected Character(
        int hp,
        int atk,
        int def,
        int mana,
        int xp,
        int maxWeight,
        int money,
        Weapon weapon,
        Object ability,
        Object passiveAbility,
        Coordinates position
    ) {
        super(hp, atk, def, position);
        this.mana = mana;
        this.xp = xp;
        this.maxWeight = maxWeight;
        this.money = money;
        this.inventory = new ArrayList<Item>();
        this.weapon = weapon;
        this.ability = ability;
        this.passiveAbility = passiveAbility;
        this.lvl = 1;
    }

    @Override
    public void move(Coordinates to) {
        this.position = to;
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
