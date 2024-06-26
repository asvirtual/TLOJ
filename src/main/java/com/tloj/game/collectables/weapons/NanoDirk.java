package com.tloj.game.collectables.weapons;

import com.tloj.game.collectables.Weapon;
import com.tloj.game.collectables.weaponeffects.DiceReroller;
import com.tloj.game.utilities.Constants;

import com.fasterxml.jackson.annotation.JsonCreator;


/**
 * Represents the NanoDirk Weapon in the game.<br>
 * It is paired with the {@link DiceReroller} ability, that allows its holder to roll twice its dice per attack<br>
 * It weighs {@value #WEIGHT} and is equipped with a D{@value #DICE_FACES}
 * @see LaserBlade
 * @see CyberKatana
 * @see PulseStaff
 * @see PlasmaGreatsword
 * @see NaniteLeechBlade
 */

public class NanoDirk extends Weapon {
    private static final double WEIGHT = 0.5;
    private static final int DICE_FACES = 6;
    private static final int ID = 14;

    @JsonCreator
    public NanoDirk() {
        super(WEIGHT, DICE_FACES, ID); 
        this.effect = new DiceReroller(this);
    }

    public static String weaponInfo() {
        return "Nano Dirk - D6 - Chance to roll twice";
    }

    @Override
    public String describe() {
        return "Nano Dirk, built with a D6 dice, it's a small dagger that can cut through anything" + "\n" +
               "Its effect is the possibility (70%) to roll twice the dice per attack"
               + "\n It weights: " + WEIGHT + " Mb";
    }

    @Override
    public String getASCII() {
        return Constants.NANO_DIRK;
    }
}
