package com.tloj.game.collectables;

import com.tloj.game.entities.Mob;
import com.tloj.game.game.PlayerAttack;
import com.tloj.game.utilities.Dice;
import com.tloj.game.collectables.items.WeaponShard;
import com.tloj.game.effects.WeaponEffect;
import com.tloj.game.entities.Character;


/**
 * An abstract class that represents a weapon that can be used to attack enemies<br>
 * Different weapons have different effects that are applied to the target when they are hit<br>
 * They are also equipped with a dice that is rolled to determine the damage dealt to the target if no effects are to be applied<br>
 * @param lvl is the weapon's level that is increased via {@link WeaponShard} and {@link Smith}<br>
 * level up means adding the level to the dice roll<br>
 * @see WeaponEffect
*/
public abstract class Weapon extends Item {
    private static final int DROP_MONEY = 0;

    protected WeaponEffect effect;
    protected Dice dice;
    protected Character character;
    protected int lvl;

    public Weapon(double weight, int diceFaces) {
        super(weight, DROP_MONEY);
        this.dice = new Dice(diceFaces);
        this.lvl = 0;
    }

    public Dice getDice() {
        return dice;
    }

    public WeaponEffect getEffect() {
        return effect;
    }

    public void upgrade(int lvl) {
        this.lvl += lvl;
    }
    
    public int diceRoll() {
        return dice.roll() + lvl;
    }

    public void assignTo(Character character) {
        this.character = character;
    }

    public Character getHolder() {
        return this.character;
    }

    /**
     * Swing the weapon at a Mob <br>
     * If the weapon has an effect, apply it, otherwise deal standard weapon dice roll damage to the target<br>
     * @param target The target to hit
     * @see WeaponEffect#apply(Character, Mob)
     */
    public void modifyAttack(PlayerAttack attack) {
        if (this.effect != null) this.effect.apply(attack);
        else attack.setWeaponRoll(this.diceRoll());
    }
}
