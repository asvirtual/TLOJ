package com.tloj.game.entities;

import com.tloj.game.utilities.Coordinates;
import com.tloj.game.utilities.Dice;
import com.tloj.game.game.Attack;
import com.tloj.game.game.MobAttack;


/**
 * Represents a mob in the game. <br>
 * Mobs are entities that can be attacked and defeated by the player. <br>
 */
public abstract class Mob extends CombatEntity {
    protected int lvl;
    /** How many xp points are gained by the player upon defeating the Mob */
    protected int xpDrop;
    /** How many money are earned by the player upon defeating the Mob */
    protected int moneyDrop;
    protected Dice dice;
    
    /**
     * @param hp The mob's health points<br>
     * @param atk The mob's attack points<br>
     * @param def The mob's defense points<br>
     * @param diceFaces The number of faces on the mob's dice<br>
     * @param lvl The mob's level<br>
     * @param xpDrop The amount of experience points the mob drops when defeated<br>
     * @param moneyDrop The amount of money the mob drops when defeated<br>
     * @param position The mob's position<br>
     * @see Entity
     * @see CombatEntity
     */
    protected Mob(
        int hp,
        int atk,
        int def,
        int diceFaces,
        int lvl,
        int xpDrop,
        int moneyDrop,
        Coordinates position
    ) {
        super(hp, atk, def, position);
        this.lvl = lvl;
        this.xpDrop = xpDrop;
        this.moneyDrop = moneyDrop;
        this.dice = new Dice(diceFaces);
    }

    @Override
    public void attack(CombatEntity t) throws IllegalArgumentException {
        if (!(t instanceof Character)) throw new IllegalArgumentException("Mobs can only attack Characters");
        
        Character target = (Character) t;
        MobAttack attack = new MobAttack(this, target);

        attack.setDiceRoll(this.dice.roll());
        attack.perform();
    }
    
    @Override
    public void defend(Attack attack) {}
    
    @Override
    public void die() {}
}
