 package com.tloj.game.collectables.weapons;

import com.tloj.game.collectables.Weapon;

/**
 * Represents the LaserBlade Weapon in the game.<br>
 * The LaserBlade is an average light weapon with a medium range blade that can be found in the game.<br>
 * It comes without an effect. <br>
 * It weighs {@value #WEIGHT}, and is equipped with a D{@value #DICE_FACES}
 * @see NanoDirk
 * @see CyberKatana
 * @see PulseStaff
 * @see PlasmaGreatSword
 * @see NaniteLeechBlade
 */
public class LaserBlade extends Weapon {
    private static final double WEIGHT = 1;
    private static final int DICE_FACES = 8;    

    public LaserBlade() {
        super(WEIGHT, DICE_FACES); 
    }

    @Override
    public String toString() {
        return "Laser Blade";
    }
}
