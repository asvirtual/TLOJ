package com.tloj.game.entities;

import java.util.ArrayList;

import com.tloj.game.collectables.Item;
import com.tloj.game.collectables.Weapon;
import com.tloj.game.utilities.Coordinates;


/**
 * Abstract class to represent a character in the game.<br>
 * A Character is a moving entity that can engage in combat, and it is controlled by the user.<br>
 * {@inheritDoc}
 * @see MovingEntity
 * @see CombatEntity
 */
public abstract class Character extends Entity implements MovingEntity, CombatEntity {
    /** Used for abilities */
    protected int mana; 
    /** Experience points, needed to level up */
    protected int xp;
    /** Current level */
    protected int lvl;
    /** The maximum weight a Character can carry. The sum of the items' weight in the {@link inventory} shall be lower or equal than this field */
    protected int maxWeight;
    protected int money;
    /** A collection of {@link Item}s the Character carries during the game */
    protected ArrayList<Item> inventory;
    protected Weapon weapon;

    protected Object ability;
    protected Object passiveAbility;

    /**
     * Constructor to create a Character from loaded data<br>
     * @param hp The character's health points<br>
     * @param atk The character's attack points<br>
     * @param def The character's defence points<br>
     * @param mana The character's mana points<br>
     * @param xp The character's experience points<br>
     * @param lvl The character's level<br>
     * @param maxWeight The character's maximum weight capacity<br>
     * @param money The character's money<br>
     * @param weapon The character's weapon<br>
     * @param ability The character's ability<br>
     * @param passiveAbility The character's passive ability<br>
     * @param inventory The character's inventory<br>
     * @param position The character's position<br>
     * @see com.tloj.game.entities.Entity
     * @see com.tloj.game.entities.MovingEntity
     * @see com.tloj.game.entities.CombatEntity 
     */
    protected Character(
        int hp,
        int atk,
        int def,
        int mana,
        int xp,
        int lvl,
        int maxWeight,
        int money,
        Weapon weapon,
        Object ability,
        Object passiveAbility,
        ArrayList<Item> inventory,
        Coordinates position
    ) {
        super(hp, atk, def, position);
        this.mana = mana;
        this.xp = xp;
        this.lvl = lvl;
        this.maxWeight = maxWeight;
        this.money = money;
        this.inventory = inventory;
        this.weapon = weapon;
        this.ability = ability;
        this.passiveAbility = passiveAbility;
    }

    /** 
     * Constructor to create an entirely new Character<br> 
     * @param hp The character's health points<br>
     * @param atk The character's attack points<br>
     * @param def The character's defence points<br>
     * @param mana The character's mana points<br>
     * @param maxWeight The character's maximum weight capacity<br>
     * @param money The character's money<br>
     * @param weapon The character's weapon<br>
     * @param ability The character's ability<br>
     * @param passiveAbility The character's passive ability<br>
     * @param position The character's position<br>
     * @see Entity
     * @see MovingEntity
     * @see CombatEntity
    */
    protected Character(
        int hp,
        int atk,
        int def,
        int mana,
        int maxWeight,
        int money,
        Weapon weapon,
        Object ability,
        Object passiveAbility,
        Coordinates position
    ) {
        super(hp, atk, def, position);
        this.mana = mana;
        this.xp = 1;
        this.lvl = 1;
        this.maxWeight = maxWeight;
        this.money = money;
        this.inventory = new ArrayList<Item>();
        this.weapon = weapon;
        this.ability = ability;
        this.passiveAbility = passiveAbility;
    }

    @Override
    public void move(Coordinates to) {
        this.position = to;
    }

    public void goNorth() {

    }

    public void goSouth() {

    }

    public void goWest() {

    }

    public void goEast() {

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
