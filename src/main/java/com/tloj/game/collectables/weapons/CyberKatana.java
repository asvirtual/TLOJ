package com.tloj.game.collectables.weapons;

import com.tloj.game.collectables.Weapon;

/**
 * Represents the CyberKatana Weapon in the game.<br>
 * The CyberKatana is a light but sharp blade that can be found in the game. It can inflicts great damage, but it has no ability<br>
 * It weighs {@value #WEIGHT}, and is equipped with a D{@value #DICE_FACES}
 * @see LaserBlade
 * @see PulseStaff
 * @see NanoDirk
 * @see PlasmaGreatSword
 * @see NaniteLeechBlade
 */
public class CyberKatana extends Weapon {
    private static final double WEIGHT = 0.8;
    private static final int DICE_FACES = 10;

    public CyberKatana() {
        super(WEIGHT, DICE_FACES); 
    }
}
