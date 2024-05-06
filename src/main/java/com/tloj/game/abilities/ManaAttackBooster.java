package com.tloj.game.abilities;

import com.tloj.game.collectables.PulseStaff;
import com.tloj.game.collectables.Weapon;
import com.tloj.game.entities.Character;
import com.tloj.game.utilities.Dice;
import com.tloj.game.entities.Mob;


/**
 * An effect that boosts the weapon's damage by 3 mana points<br>
 * This effect requires 3 mana points to use<br>
 * It is paired with the Pulse Staff<br>
 * @see PulseStaff
 */
public class ManaAttackBooster extends WeaponEffect {
    public ManaAttackBooster(Weapon weapon) {
        super(weapon);
    }

    /**
     * Apply the effect to the target<br>
     * @return true if the player had enough mana, false otherwise
     */
    @Override
    public boolean apply(Character holder, Mob enemy) {
        if (holder.getMana() < 3) return false;

        Dice dice = this.weapon.getDice();
        enemy.takeDamage(dice.roll());
        holder.useMana(3);

        return true;
    }
}
