package com.tloj.game.collectables;

import com.tloj.game.entities.Mob;
import com.tloj.game.utilities.Dice;
import com.tloj.game.abilities.WeaponEffect;
import com.tloj.game.entities.Character;


/**
 * An abstract class that represents a weapon that can be used to attack enemies<br>
 * Different weapons have different effects that are applied to the target when they are hit<br>
 * They are also equipped with a dice that is rolled to determine the damage dealt to the target if no effects are to be applied<br>
 * @see WeaponEffect
*/
public abstract class Weapon extends Item {
    protected WeaponEffect effect;
    protected Dice dice;
    protected Character character;

    public Weapon(double weight, int diceFaces) {
        super(weight);
        this.dice = new Dice(diceFaces);
    }

    public Dice getDice() {
        return dice;
    }

    public int diceRoll() {
        return dice.roll();
    }

    public void assignTo(Character character) {
        this.character = character;
    }

    /**
     * Swing the weapon at a target <br>
     * If the weapon has an effect, apply it, otherwise deal standard weapon dice roll damage to the target<br>
     * @param target The target to hit
     * @see WeaponEffect#apply(Character, Mob)
     */
    public void hit(Mob target) {
        // If the weapon has an effect, try to apply it to the target, otherwise deal standard damage to the target
        if (this.effect == null || !this.effect.apply(this.character, target)) 
            target.takeDamage(this.diceRoll());
    }
}
