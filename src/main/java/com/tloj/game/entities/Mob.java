package com.tloj.game.entities;

import com.fasterxml.jackson.annotation.JsonTypeInfo;

import com.tloj.game.utilities.Coordinates;
import com.tloj.game.utilities.Dice;
import com.tloj.game.collectables.Item;
import com.tloj.game.game.MobAttack;


// Needed to serialize/deserialize subclasses of Character, by including the class name in the JSON
@JsonTypeInfo(
  use = JsonTypeInfo.Id.CLASS, 
  include = JsonTypeInfo.As.PROPERTY, 
  property = "@class")

  
/**
 * Represents a mob in the game. <br>
 * Mobs are entities that can be attacked and defeated by the player. <br>
 * @see CombatEntity
 * @see Attack
 */

public abstract class Mob extends CombatEntity {
    /** Mobs have a 10% chance to drop an item */
    public static final int DROP_CHANCE = 10;
    /** Mobs give 10 Score Points */
    public static final int SCORE_DROP = 10;
    /** The mob's level */
    protected int lvl;
    /** How many xp points are gained by the player upon defeating the Mob */
    protected int xpDrop;
    /** How many money are earned by the player upon defeating the Mob */
    protected int moneyDrop;
    /** Each Mob has a standard D6 dice */
    protected Dice dice;
    /** Mob has a chance to drop a random item to the player upon defeating */
    protected Item drop;
    
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
        super(hp * lvl, atk * Mob.levelUpFactor(lvl), def * Mob.levelUpFactor(lvl), position);
        this.lvl = lvl;
        this.xpDrop = xpDrop;
        this.moneyDrop = moneyDrop;
        this.dice = new Dice(diceFaces);
        this.drop = Item.getRandomItem();
    }

    /**
     * @param hp The mob's health points<br>
     * @param atk The mob's attack points<br>
     * @param def The mob's defense points<br>
     * @param diceFaces The number of faces on the mob's dice<br>
     * @param lvl The mob's level<br>
     * @param xpDrop The amount of experience points the mob drops when defeated<br>
     * @param moneyDrop The amount of money the mob drops when defeated<br>
     * @param position The mob's position<br>
     * @param drop The mob's drop item<br>
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
        Coordinates position,
        Item drop
        
    ) {
        super(hp * lvl, atk * Mob.levelUpFactor(lvl) , def * Mob.levelUpFactor(lvl), position);
        this.lvl = lvl;
        
        this.xpDrop = xpDrop;
        this.moneyDrop = moneyDrop;
        this.dice = new Dice(diceFaces);
        this.drop = drop;   
    }

    /*
     * All getters and setters needed from Jackson
     */
    public int getLvl() {
        return this.lvl;
    }

    public void setLvl(int lvl) {
        this.lvl = lvl;
    }

    public int getXpDrop() {
        return this.xpDrop;
    }

    public void setXpDrop(int xpDrop) {
        this.xpDrop = xpDrop;
    }

    public int getMoneyDrop() {
        return this.moneyDrop;
    }

    public void setMoneyDrop(int moneyDrop) {
        this.moneyDrop = moneyDrop;
    }

    public Dice getDice() {
        return this.dice;
    }

    public void setDice(Dice dice) {
        this.dice = dice;
    }

    public void setDrop(Item drop) {
        this.drop = drop;
    }

    private static int levelUpFactor(int lvl){
        return
            lvl = lvl == 1 ? 1
            : (int) (1 + Math.log(lvl) / (int) Math.log(8));
    }
    
    @Override
    public void attack(CombatEntity t) throws IllegalArgumentException {
        if (!(t instanceof Character)) throw new IllegalArgumentException("Mobs can only attack Characters");
        
        Character target = (Character) t;
        MobAttack attack = new MobAttack(this, target);

        System.out.println(this + " attacks you back!");
        System.out.println(this.getCombatASCII());
        
        attack.setDiceRoll(this.dice.roll());
        attack.perform();

        System.out.println(this.getPrettifiedStatus());
    }
       
    public Item getDrop() {
        Dice dice = new Dice(DROP_CHANCE);
        if (dice.roll() < 2) return this.drop;
        return null;
    }

    public String getPrettifiedStatus() {
        return 
            this + ":\n" +
            "HP: " + this.getHpBar() + " " + this.getHP() + "/" + this.getMaxHP() + "\n";
    }

    @Override
    public String toString() {
        return String.join(" ", this.getClass().getSimpleName().split("(?=[A-Z])"));
    }
}
