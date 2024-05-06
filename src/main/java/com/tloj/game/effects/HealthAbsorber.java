package com.tloj.game.effects;

import com.tloj.game.collectables.Weapon;
import com.tloj.game.entities.Character;
import com.tloj.game.entities.Mob;


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
    public boolean apply(Character holder, Mob enemy) {
        int damage = this.weapon.diceRoll();
        enemy.takeDamage(damage);
        holder.heal(damage / 2);

        return true;
    }
}
