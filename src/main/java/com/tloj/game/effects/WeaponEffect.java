package com.tloj.game.effects;

import com.tloj.game.entities.Character;
import com.tloj.game.entities.Mob;
import com.tloj.game.collectables.Weapon;


/**
 * An abstract class representing an effect that can be applied to a weapon<br>
 * This class is meant to be extended by specific weapon effects, guaranteeing modularity<br>
 * @see Weapon
 */
public abstract class WeaponEffect {
    protected Weapon weapon;

    protected WeaponEffect(Weapon weapon) {
        this.weapon = weapon;
    }

    /**
     * Apply the effect to the target
     * @param holder The character holding the weapon
     * @param enemy The target of the effect
     * @param params Additional parameters for the effect
     * @return true if the effect was applied successfully, false otherwise
     */
    public abstract boolean apply(Character holder, Mob enemy);
}
