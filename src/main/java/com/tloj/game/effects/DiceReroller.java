package com.tloj.game.effects;

import com.tloj.game.entities.Character;
import com.tloj.game.entities.Mob;
import com.tloj.game.collectables.NanoDirk;
import com.tloj.game.collectables.Weapon;


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
    public boolean apply(Character holder, Mob enemy) {
        enemy.takeDamage(this.weapon.diceRoll());
        enemy.takeDamage(this.weapon.diceRoll());
        
        return true;
    }
}
