package com.tloj.game.collectables.weaponeffects;

import com.tloj.game.game.PlayerAttack;
import com.tloj.game.abilities.BossAbility;
import com.tloj.game.collectables.Weapon;


/**
 * An abstract class representing an effect that can be applied to a weapon<br>
 * As with the {@link BossAbility} class, this class applies an adaptation of the Strategy pattern to the weapon effects, allowing for easy addition of new effects<br>
 * This class is meant to be extended by specific weapon effects, guaranteeing modularity<br>
 * @see Weapon
 */
public abstract class WeaponEffect {
    protected Weapon weapon;
    protected String activationMessage;
    protected boolean used = false;

    protected WeaponEffect(Weapon weapon) {
        this.weapon = weapon;
    }

    public String getActivationMessage() {
        return this.activationMessage;
    }

    public boolean wasUsed() {
        return this.used;
    }

    /*
     * Split the class name by capital letters and join the resulting array with spaces
     */
    @Override
    public String toString() {  
        return String.join(" ", this.getClass().getSimpleName().split("(?=[A-Z])")) + ": "; 
    };

    /**
     * Apply the effect to the attack
     * @param attack the attack to apply the effect to
     * @return true if the effect was applied successfully, false otherwise
     */
    public abstract boolean apply(PlayerAttack attack);
}
