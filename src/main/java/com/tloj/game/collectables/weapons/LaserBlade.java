 package com.tloj.game.collectables.weapons;

import com.tloj.game.collectables.Weapon;
import com.tloj.game.utilities.Constants;

import com.fasterxml.jackson.annotation.JsonCreator;


/**
 * Represents the LaserBlade Weapon in the game.<br>
 * It comes without an effect. <br>
 * It weighs {@value #WEIGHT} and is equipped with a D{@value #DICE_FACES}
 * @see NanoDirk
 * @see CyberKatana
 * @see PulseStaff
 * @see PlasmaGreatsword
 * @see NaniteLeechBlade
 */

public class LaserBlade extends Weapon {
    private static final double WEIGHT = 1;
    private static final int DICE_FACES = 8;
    private static final int ID = 12;    

    @JsonCreator
    public LaserBlade() {
        super(WEIGHT, DICE_FACES, ID); 
    }

    public static String weaponInfo() {
        return "Laser Blade - D8 - No effect";
    }

    @Override
    public String describe() {
        return "Laser Blade, built with a D8 dice, the most famous in all universe" + "\n" +
               "No effect, just swing it and cut everything in your way!"
               + "\n It weights: " + WEIGHT + " Mb";
    }

    @Override
    public String getASCII() {
        return Constants.LASER_BLADE;
    }
}
