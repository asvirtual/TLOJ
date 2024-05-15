package com.tloj.game.effects;

import com.tloj.game.collectables.Weapon;
import com.tloj.game.collectables.weapons.PulseStaff;
import com.tloj.game.entities.Character;
import com.tloj.game.game.PlayerAttack;
import com.tloj.game.utilities.ConsoleHandler;
import com.fasterxml.jackson.annotation.JsonCreator;


/**
 * An effect that boosts allows the weapon to inflict from 5 to 10 dmg points<br>
 * It requires 3 mana points to be used<br>
 * It is paired with the Pulse Staff<br>
 * @see PulseStaff
 */
public class ManaAttackBooster extends WeaponEffect {
    private static final int MANA_COST = 3;

    @JsonCreator
    public ManaAttackBooster(Weapon weapon) {
        super(weapon);
    }

    /**
     * Apply the effect to the attack<br>
     * @return true if the player had enough mana, false otherwise
     */
    @Override
    public boolean apply(PlayerAttack attack) {
        Character holder = attack.getAttacker();
        if (holder.getMana() < MANA_COST) return this.used = false;

        attack.setWeaponRoll(this.weapon.diceRoll());
        holder.useMana(MANA_COST);

        this.activationMessage = ConsoleHandler.YELLOW_BRIGHT + this.weapon.getName() + "'s critical mana damage activated!" + ConsoleHandler.RESET;
        return this.used = true;
    }

    @Override
    public String toString() {
        return super.toString() + "costs 2 mana to use";
    }

    public static String describe() {
        return "costs 2 mana to use";
    }
}
