package com.tloj.game.collectables;

import com.tloj.game.game.PlayerAttack;
import com.tloj.game.utilities.Dice;
import com.fasterxml.jackson.annotation.JsonIgnore;
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

    @JsonIgnore
    protected WeaponEffect effect;
    protected Dice dice;
    protected int lvl;

    public Weapon(double weight, int diceFaces, int ID) {
        super(weight, DROP_MONEY, ID);
        this.dice = new Dice(diceFaces);
        this.lvl = 0;
    }

    public WeaponEffect getEffect() {
        return effect;
    }

    public int getDiceFaces() {
        return dice.getFaces();
    }

    public void upgrade(int lvl) {
        this.lvl += lvl;
    }
    
    public int diceRoll() {
        return dice.roll() + this.lvl;
    }

    /**
     * Swing the weapon at a Mob <br>
     * If the weapon has an effect, apply it, otherwise deal standard weapon dice roll damage to the target<br>
     * @param attack The attack to modify
     * @see WeaponEffect#apply(PlayerAttack)
     */
    public void modifyAttack(PlayerAttack attack) {
        if (this.effect != null) this.effect.apply(attack);
        else attack.setWeaponRoll(this.diceRoll());
    }

    @Override 
    public String toString() {
        return super.toString() + " +" + this.lvl;
    }

    @Override
    public String getASCII() {
        return "";
    }

    public abstract String describe();
}
