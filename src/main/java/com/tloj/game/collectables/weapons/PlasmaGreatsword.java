package com.tloj.game.collectables.weapons;

import com.tloj.game.collectables.Weapon;

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
    private static final double WEIGHT = 2.5;
    private static final int DICE_FACES = 15;

    public PlasmaGreatsword() {
        super(WEIGHT, DICE_FACES); 
    }
}
