package com.tloj.game.effects;

import com.tloj.game.game.PlayerAttack;
import com.tloj.game.collectables.Weapon;
import com.tloj.game.collectables.weapons.NanoDirk;


/**
 * An effect that rerolls the weapon's dice and applies the damage twice to the target<br>
 * This effect is always applied and does not require any mana to use<br>
 * It is paired with the Nano Dirk<br>
 * @see NanoDirk
*/
public class DiceReroller extends WeaponEffect {
    public DiceReroller(Weapon weapon) {
        super(weapon);
    }

    @Override
    public boolean apply(PlayerAttack attack) {
        attack.setWeaponRoll(
            this.weapon.diceRoll() + 
            this.weapon.diceRoll()
        );
        
        return true;
    }

    @Override
    public String toString() {
        return super.toString() + "dice thrown twice";
    }

    public static String describe() {
        return "Dice Reroller: dice thrown twice";
    }
}
