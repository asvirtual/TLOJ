package com.tloj.game.collectables.weapons;

import com.tloj.game.collectables.Weapon;
import com.tloj.game.collectables.weapons.LaserBlade;
import com.tloj.game.collectables.weapons.PulseStaff;
import com.tloj.game.utilities.Constants;
import com.tloj.game.collectables.weapons.NanoDirk;
import com.tloj.game.collectables.weapons.PlasmaGreatsword;
import com.tloj.game.collectables.weapons.NaniteLeechBlade;
import com.fasterxml.jackson.annotation.JsonCreator;


/**
 * Represents the CyberKatana Weapon in the game.<br>
 * The CyberKatana is a light but sharp blade that can be found in the game.<br>
 * It comes without an effect. <br>
 * It weighs {@value #WEIGHT}, and is equipped with a D{@value #DICE_FACES}
 * @see LaserBlade
 * @see PulseStaff
 * @see NanoDirk
 * @see PlasmaGreatsword
 * @see NaniteLeechBlade
 */
public class CyberKatana extends Weapon {
    private static final double WEIGHT = 0.8;
    private static final int DICE_FACES = 10;
    private static final int ID = 13;

    @JsonCreator
    public CyberKatana() {
        super(WEIGHT, DICE_FACES, ID); 
    }

    public static String weaponInfo() {
        return "Cyber Katana - D10 - No effect";
    }

    @Override
    public String describe() {
        return "Cyber Katana, built with a D10 dice, it's the Samurai best and only friend" + "\n" +
               "Its effect is his master's skill and precision";
    }

    @Override
    public String getASCII() {
        return Constants.CYBER_CATANA;
    }
}
