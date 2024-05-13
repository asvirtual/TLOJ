package com.tloj.game.collectables.weapons;

import com.tloj.game.collectables.Weapon;
import com.tloj.game.collectables.weapons.LaserBlade;
import com.tloj.game.collectables.weapons.CyberKatana;
import com.tloj.game.collectables.weapons.NanoDirk;
import com.tloj.game.collectables.weapons.PlasmaGreatsword;
import com.tloj.game.collectables.weapons.NaniteLeechBlade;
import com.tloj.game.effects.ManaAttackBooster;
import com.tloj.game.utilities.Constants;
import com.fasterxml.jackson.annotation.JsonCreator;
import com.fasterxml.jackson.annotation.JsonProperty;


/**
 * Represents the PulseStaff Weapon in the game.<br>
 * The PulseStaff is an rare heavy weapon with a long blade that can be found in the game. <br>
 * It is paired with the {@link ManaAttackBooster} ability, that allows its holder to inflict from 5 to 10 more damage points by spending 3 mana points<br>
 * It weighs {@value #WEIGHT}, and is equipped with a D{@value #DICE_FACES}
 * @see LaserBlade
 * @see CyberKatana
 * @see NanoDirk
 * @see PlasmaGreatsword
 * @see NaniteLeechBlade
 */
public class PulseStaff extends Weapon {
    private static final double WEIGHT = 1.2;
    private static final int DICE_FACES = 5;    //5 standard value if no mana then -->
    private static final int ID = 16;

    @JsonCreator
    public PulseStaff() {
        super(WEIGHT, DICE_FACES, ID);
        this.effect = new ManaAttackBooster(this); 
    }

    public static String weaponInfo() {
        return "Pulse-Staff - (D5-10) - Consumes 2 mana points to deal 5 to 10 extra damage";
    }

    @Override
    public String describe() {
        return "Pulse-Staff, built with a special dice that can roll between 5 and 10, a special staff that exploits its user mana reserves" + "\n" +
               "Its effect is consuming 2 mana points to deal 5 to 10 extra damage points";
    }

    @Override
    public String getASCII() {
        return Constants.PULSE_STAFF;
    }
}
