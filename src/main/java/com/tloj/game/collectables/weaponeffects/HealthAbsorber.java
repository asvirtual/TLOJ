package com.tloj.game.collectables.weaponeffects;

import com.tloj.game.collectables.weapons.NaniteLeechBlade;
import com.tloj.game.collectables.Weapon;
import com.tloj.game.game.PlayerAttack;
import com.tloj.game.utilities.ConsoleHandler;

import com.fasterxml.jackson.annotation.JsonCreator;


/**
 * An effect that absorbs health from the target and heals the character holding the weapon<br>
 * This effect is has a 66,6% chance to apply but it doesn't consume mana<br>
 * It is paired with the Nanite Leech Blade<br>
 * @see NaniteLeechBlade
 */

public class HealthAbsorber extends WeaponEffect {
    @JsonCreator
    public HealthAbsorber(Weapon weapon) {
        super(weapon);
    }

    /**
     * Absorbs health from the target and heals the character holding the weapon<br>
     * @return true if the effect was applied, false otherwise
     */
    @Override
    public boolean apply(PlayerAttack attack) {
        int damage = this.weapon.diceRoll();
        attack.setWeaponRoll(damage);

        if (!this.weapon.evaluateProbability(0.666)) return this.used = false;

        attack.setOnHit(new Runnable() {
            @Override
            public void run() {
                int totalDamage = attack.getTotalDamage();
                if (totalDamage > 0) attack.getAttacker().heal(totalDamage / 2);
                HealthAbsorber.this.activationMessage = ConsoleHandler.YELLOW_BRIGHT + HealthAbsorber.this.weapon.getName() + "'s health Absorber activated! Jordan was healed for " + totalDamage / 2 + " hp" + ConsoleHandler.RESET + "\n";
            }
        });

        return this.used = true;
    }

    @Override
    public String toString() {
        return super.toString() + "heals for half the damage dealt";
    }
}
