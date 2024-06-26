package com.tloj.game.collectables.weapons;

import com.tloj.game.collectables.Weapon;
import com.tloj.game.collectables.weaponeffects.ManaAttackBooster;
import com.tloj.game.utilities.Constants;

import com.fasterxml.jackson.annotation.JsonCreator;


/**
 * Represents the PulseStaff Weapon in the game.<br>
 * The PulseStaff is an rare heavy weapon with a long blade that can be found in the game. <br>
 * It is paired with the {@link ManaAttackBooster} ability, that allows its holder to inflict from 5 to 10 more damage points by spending 3 mana points<br>
 * It weighs {@value #WEIGHT} and is equipped with a D{@value #DICE_MIN}-{@value #DICE_MAX}
 * @see LaserBlade
 * @see CyberKatana
 * @see NanoDirk
 * @see PlasmaGreatsword
 * @see NaniteLeechBlade
 */

public class PulseStaff extends Weapon {
    private static final double WEIGHT = 1.2;
    private static final int DICE_MIN = 5;
    private static final int DICE_MAX = 10;
    private static final int ID = 16;

    @JsonCreator
    public PulseStaff() {
        super(WEIGHT, DICE_MIN, DICE_MAX, ID);
        this.effect = new ManaAttackBooster(this); 
    }

    public static String weaponInfo() {
        return "Pulse-Staff - (D5-10) - Consumes 2 mana points to deal 5 to 10 extra damage";
    }

    @Override
    public String describe() {
        return "Pulse-Staff, built with a special dice that can roll between 5 and 10, a special staff that exploits its user mana reserves" + "\n" +
               "Its effect is consuming" + ManaAttackBooster.MANA_COST + "mana points to deal 5 to 10 extra damage points"
               + "\n It weights: " + WEIGHT + " Mb";
    }

    @Override
    public String getASCII() {
        return Constants.PULSE_STAFF;
    }
}
