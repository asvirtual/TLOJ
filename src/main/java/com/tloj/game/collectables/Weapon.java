package com.tloj.game.collectables;

import com.tloj.game.game.PlayerAttack;
import com.tloj.game.utilities.Dice;
import com.fasterxml.jackson.annotation.JsonCreator;
import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonProperty;
import com.fasterxml.jackson.annotation.JsonTypeInfo;
import com.tloj.game.effects.WeaponEffect;


// Needed to serialize/deserialize subclasses of Character, by including the class name in the JSON
@JsonTypeInfo(
  use = JsonTypeInfo.Id.CLASS, 
  include = JsonTypeInfo.As.PROPERTY, 
  property = "@class")

/**
 * An abstract class that represents a weapon that can be used to attack enemies<br>
 * Different weapons have different effects that are applied to the target when they are hit<br>
 * They are also equipped with a {@link Dice} that is rolled to determine the damage dealt to the target if no effects are to be applied<br>
 * @param lvl is the weapon's level that is increased via {@link WeaponShard} and {@link Smith}<br>
 * level up means adding the level to the dice roll<br>
 * @see WeaponEffect
*/
public abstract class Weapon extends Item {
    private static final int DROP_MONEY = 0;
    public static final int MAX_LEVEL = 5;

    @JsonIgnore
    protected WeaponEffect effect;
    @JsonProperty("dice")
    protected Dice dice;
    @JsonProperty("level")
    protected int lvl;

    public Weapon(double weight, int diceFaces, int id) {
        super(weight, DROP_MONEY, id);
        this.dice = new Dice(diceFaces);
        this.lvl = 0;
    }

    public Weapon(double weight, int diceMin, int diceMax, int id) {
        super(weight, DROP_MONEY, id);
        this.dice = new Dice(diceMin, diceMax);
        this.lvl = 0;
    }

    public WeaponEffect getEffect() {
        return effect;
    }
    
    public void setLevel(int lvl) {
        this.lvl = lvl;
    }

    @JsonIgnore
    public int getDiceMax() {
        return dice.getMax();
    }

    @JsonIgnore
    public int getDiceFaces() {
        return dice.getFaces();
    }

    public void upgrade(int lvl) {
        this.lvl += lvl;
    }
    
    public int diceRoll() {
        return dice.roll() + this.lvl;
    }

    public int getLevel() {
        return this.lvl;
    }

    /**
     * Swing the weapon at a Mob <br>
     * If the weapon has an effect, apply it, otherwise deal standard weapon dice roll damage to the target<br>
     * @param attack The attack to modify
     * @see WeaponEffect#apply(PlayerAttack)
     */
    public void modifyAttack(PlayerAttack attack) {
        if (this.effect == null) attack.setWeaponRoll(this.diceRoll());
        else {
            this.effect.apply(attack);
            attack.setWeaponEffect(this.effect);
        }
    }

    @JsonIgnore
    public String getName() {
        return super.toString();
    }

    @Override 
    public String toString() {
        return super.toString() + " +" + this.lvl;
    }

    public abstract String describe();
}
