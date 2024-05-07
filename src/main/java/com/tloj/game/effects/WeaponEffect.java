package com.tloj.game.effects;

import com.tloj.game.game.PlayerAttack;
import com.tloj.game.collectables.Weapon;


/**
 * An abstract class representing an effect that can be applied to a weapon<br>
 * As with the {@link BossAbility} class, this class applies the Strategy pattern to the weapon effects, allowing for easy addition of new effects<br>
 * This class is meant to be extended by specific weapon effects, guaranteeing modularity<br>
 * @see Weapon
 */
public abstract class WeaponEffect {
    protected Weapon weapon;

    protected WeaponEffect(Weapon weapon) {
        this.weapon = weapon;
    }

    /**
     * Apply the effect to the attack
     * @param attack the attack to apply the effect to
     * @return true if the effect was applied successfully, false otherwise
     */
    public abstract boolean apply(PlayerAttack attack);
}
