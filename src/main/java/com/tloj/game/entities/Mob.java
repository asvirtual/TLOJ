package com.tloj.game.entities;

import org.fusesource.jansi.Ansi;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonTypeInfo;
import com.tloj.game.utilities.ConsoleHandler;
import com.tloj.game.abilities.MobAbility;
import com.tloj.game.collectables.Item;
import com.tloj.game.game.Attack;
import com.tloj.game.game.Coordinates;
import com.tloj.game.game.Dice;
import com.tloj.game.game.MobAttack;
import com.tloj.game.game.PlayerAttack;
import com.tloj.game.skills.CharacterSkill;


/**
 * Represents a mob in the game. <br>
 * Mobs are entities that can be attacked and defeated by the player. <br>
 * @see CombatEntity
 * @see MobAttack
 */
// Needed to serialize/deserialize subclasses of Mob, by including the class name in the JSON
@JsonTypeInfo(
  use = JsonTypeInfo.Id.CLASS, 
  include = JsonTypeInfo.As.PROPERTY, 
  property = "@class")

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
    /** Mob might have an ability assigned */
    @JsonIgnore
    protected MobAbility ability;
    
    /**
     * Constructor for the Mob class with a random drop<br>
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
        super(
            hp * lvl, 
            atk, 
            def, 
            // atk * lvl,
            // def * lvl,
            position
        );

        for (int i = 2; i <= lvl; i++) 
            this.levelUp(i);

        this.lvl = lvl;
        this.xpDrop = xpDrop;
        this.moneyDrop = moneyDrop;
        this.dice = new Dice(diceFaces);
        this.drop = Item.getRandomItem();
    }

    /**
     * Constructor for the Mob class with a specific drop<br>
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
        super(
            hp * lvl, 
            atk, 
            def, 
            position
        );

        for (int i = 2; i <= lvl; i++) 
            this.levelUp(i);

        this.lvl = lvl;
        this.xpDrop = xpDrop;
        this.moneyDrop = moneyDrop;
        this.dice = new Dice(diceFaces);
        this.drop = drop;
    }

    @JsonIgnore
    public int getDiceFaces() {
        return this.dice.getFaces();
    }

    public int getLvl() {
        return this.lvl;
    }

    public void setLvl(int lvl) {
        this.lvl = lvl;
    }

    public int getXpDrop() {
        return this.xpDrop;
    }

    public int dropXp() {
        return this.xpDrop * this.lvl;
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

    public void setDice(Dice dice) {
        this.dice = dice;
    }

    public void setDrop(Item drop) {
        this.drop = drop;
    }

    public MobAbility getAbility() {
        return this.ability;
    }

    /**
     * The level up factor for the mobs.<br>
     * To ensure game balancement it must differ for each mob<br>
     */
    protected void levelUp(int lvl) {
        this.currentFightAtk = this.atk;
        this.currentFightDef = this.def;
    };
    
    public int rollDice() {
        return this.dice.roll();
    }

    public boolean evaluateProbability(double probability) {
        return this.dice.evaluateProbability(probability);
    }
    
    @Override
    public void attack(CombatEntity t) throws IllegalArgumentException {
        if (!(t instanceof Character)) throw new IllegalArgumentException("Mobs can only attack Characters");
        
        Character target = (Character) t;
        MobAttack attack = new MobAttack(this, target);
        
        ConsoleHandler.clearConsole();
        
        ConsoleHandler.println(this.getASCII());
        ConsoleHandler.println(this + " attacks you back!");
        
        ConsoleHandler.clearConsole(1500);
                
        attack.setDiceRoll(this.dice.roll());
        
        CharacterSkill characterSkill = target.getSkill();
        if (characterSkill != null) characterSkill.execute(attack);

        attack.perform();

        if (!target.isAlive()) target.notifyDefeat();
    }

    @Override
    public void defend(Attack attack) throws IllegalArgumentException {
        if (!(attack instanceof PlayerAttack)) throw new IllegalArgumentException("Mobs can only be attacked by Characters");
        
        super.defend(attack);

        if (this.ability == null) return;
        PlayerAttack playerAttack = (PlayerAttack) attack;
        this.ability.use(playerAttack);
    }
       
    public Item getDrop() {
        Dice dice = new Dice(DROP_CHANCE);
        if (dice.roll() < 2) return this.drop;
        return null;
    }

    @JsonIgnore
    public String getPrettifiedStatus() {
        return 
            this + " - " + ConsoleHandler.GREEN + "Lvl " + this.lvl + ConsoleHandler.RESET + ":\n\n" +
            " ⸭ HP: " + Ansi.ansi().fg(Ansi.Color.RED).a(this.getHpBar() + " " + this.hp + "/" + this.maxHp).reset() + "\n";
    }

    @Override
    public String toString() {
        return this.getName();
    }
}
