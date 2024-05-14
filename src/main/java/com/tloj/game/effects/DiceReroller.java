package com.tloj.game.effects;

import com.tloj.game.game.PlayerAttack;
import com.tloj.game.utilities.ConsoleHandler;
import com.tloj.game.collectables.Weapon;
import com.tloj.game.collectables.weapons.NanoDirk;
import com.fasterxml.jackson.annotation.JsonCreator;


/**
 * An effect that rerolls the weapon's dice and applies the damage twice to the target<br>
 * This effect is always applied and does not require any mana to use<br>
 * It is paired with the Nano Dirk<br>
 * @see NanoDirk
*/
public class DiceReroller extends WeaponEffect {
    @JsonCreator
    public DiceReroller(Weapon weapon) {
        super(weapon);
    }

    @Override
    public boolean apply(PlayerAttack attack) {
        int roll = this.weapon.diceRoll();
        attack.setWeaponRoll(roll);
        
        if (Math.random() > 0.7) return this.used = false;
        
        attack.setWeaponRoll(
            roll + 
            this.weapon.diceRoll()
        );

        this.activationMessage = ConsoleHandler.YELLOW_BRIGHT + this.weapon.getName() + "'s dice reroller activated! Double hit!\n" + ConsoleHandler.RESET;
        return this.used = true;
    }

    @Override
    public String toString() {
        return super.toString() + "dice thrown twice";
    }

    public static String describe() {
        return "Dice Reroller: dice thrown twice";
    }
}
