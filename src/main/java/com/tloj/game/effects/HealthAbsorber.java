package com.tloj.game.effects;

import com.tloj.game.collectables.NaniteLeechBlade;
import com.tloj.game.collectables.Weapon;
import com.tloj.game.game.PlayerAttack;


/**
 * An effect that absorbs health from the target and heals the character holding the weapon<br>
 * This effect is always applied and does not require any mana to use<br>
 * It is paired with the Nanite Leech Blade<br>
 * @see NaniteLeechBlade
 */
public class HealthAbsorber extends WeaponEffect {
    public HealthAbsorber(Weapon weapon) {
        super(weapon);
    }

    @Override
    public boolean apply(PlayerAttack attack) {
        int damage = this.weapon.diceRoll();
        attack.setWeaponRoll(damage);
        attack.setOnHit(new Runnable() {
            @Override
            public void run() {
                int totalDamage = attack.getTotalDamage();
                if (totalDamage > 0) attack.getAttacker().heal(totalDamage / 2);
            }
        });

        return true;
        
    }
}
