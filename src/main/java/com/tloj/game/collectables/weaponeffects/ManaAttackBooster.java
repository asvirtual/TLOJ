package com.tloj.game.collectables.weaponeffects;

import com.tloj.game.collectables.Weapon;
import com.tloj.game.entities.Character;
import com.tloj.game.game.PlayerAttack;
import com.tloj.game.utilities.ConsoleHandler;

import com.fasterxml.jackson.annotation.JsonCreator;


/**
 * An effect that allows the weapon to inflict from 5 to 10 dmg points<br>
 * It requires {@value #MANA_COST} mana points to be used<br>
 * It is paired with the Pulse Staff<br>
 * @see PulseStaff
 */

public class ManaAttackBooster extends WeaponEffect {
    public static final int MANA_COST = 3;

    @JsonCreator
    public ManaAttackBooster(Weapon weapon) {
        super(weapon);
    }

    /**
     * Allows the weapon to inflict from 5 to 10 damage points, requires {@value #MANA_COST} mana points to be used
     * @return true if the player had enough mana, false otherwise
     */
    @Override
    public boolean apply(PlayerAttack attack) {
        Character holder = attack.getAttacker();
        if (holder.getMana() < MANA_COST) return this.used = false;

        attack.setWeaponRoll(this.weapon.diceRoll());
        holder.useMana(MANA_COST);

        this.activationMessage = ConsoleHandler.YELLOW_BRIGHT + this.weapon.getName() + "'s critical mana damage activated!" + ConsoleHandler.RESET + "\n";
        return this.used = true;
    }

    @Override
    public String toString() {
        return super.toString() + "costs " + MANA_COST + " mana to use";
    }

    public static String describe() {
        return "costs " + MANA_COST + " mana to use";
    }
}
