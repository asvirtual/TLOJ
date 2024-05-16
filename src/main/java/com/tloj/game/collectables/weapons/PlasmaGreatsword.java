package com.tloj.game.collectables.weapons;

import com.tloj.game.collectables.Weapon;
import com.tloj.game.collectables.weapons.NanoDirk;
import com.tloj.game.collectables.weapons.CyberKatana;
import com.tloj.game.collectables.weapons.PulseStaff;
import com.tloj.game.utilities.Constants;
import com.tloj.game.collectables.weapons.LaserBlade;
import com.tloj.game.collectables.weapons.NaniteLeechBlade;
import com.fasterxml.jackson.annotation.JsonCreator;

/**
 * Represents the PlasmaGreatsword Weapon in the game.<br>
 * The PlasmaGreatsword is an heavy weapon with a long range blade that can be found in the game. <br>
 * It comes without an effect. <br>
 * It weighs {@value #WEIGHT}, and is equipped with a D{@value #DICE_FACES}
 * @see NanoDirk
 * @see CyberKatana
 * @see PulseStaff
 * @see LaserBlade
 * @see NaniteLeechBlade
 */
public class PlasmaGreatsword extends Weapon {
    private static final double WEIGHT = 2;
    private static final int DICE_FACES = 15;
    private static final int ID = 15;

    @JsonCreator
    public PlasmaGreatsword() {
        super(WEIGHT, DICE_FACES, ID); 
    }

    public static String weaponInfo() {
        return "Plasma Great Sword - D15 - No effect";
    }

    @Override
    public String describe() {
        return "Plasma Great Sword, built with a D15 dice, it's strong, cool and MechaKnight's companion" + "\n" +
               "No effect, muscle power only!"
               + "\n It weights: " + WEIGHT + " Mb";
    }

    @Override
    public String getASCII() {
        return Constants.PLASMA_GREATSWORD;
    }
}
