package com.tloj.game.effects;

import com.tloj.game.collectables.Weapon;
import com.tloj.game.collectables.weapons.PulseStaff;
import com.tloj.game.entities.Character;
import com.tloj.game.game.PlayerAttack;
import com.tloj.game.utilities.Dice;
import com.fasterxml.jackson.annotation.JsonCreator;
import com.fasterxml.jackson.annotation.JsonProperty;


/**
 * An effect that boosts allows the weapon to inflict from 5 to 10 dmg points<br>
 * It requires 3 mana points to be used<br>
 * It is paired with the Pulse Staff<br>
 * @see PulseStaff
 */
public class ManaAttackBooster extends WeaponEffect {
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
        if (holder.getMana() < 3) return false;

        attack.setBonusDamage(this.weapon.diceRoll() + 5);
        holder.useMana(3);

        System.out.println("Critical mana damage!");
        return true;
    }

    @Override
    public String toString() {
        return super.toString() + "costs 3 mana to use";
    }

    public static String describe() {
        return "costs 3 mana to use";
    }
}
