package com.tloj.game.collectables;

import com.tloj.game.effects.HealthAbsorber;


/**
 * Represents the NaniteLeechBlade Weapon in the game.<br>
 * The NaniteLeechBlade is a light weapon with a short range blade that can be found in the game. <br>
 * It is paired with the {@link HealthAbsorber} ability<br>
 * It weighs {@value #WEIGHT}, and is equipped with a D{@value #DICE_FACES}
 * @see NanoDirk
 * @see CyberKatana
 * @see PulseStaff
 * @see LaserBlade
 * @see PlasmaGreatSword
 */
public class NaniteLeechBlade extends Weapon {
    private static final double WEIGHT = 0.8;
    private static final int DICE_FACES = 12;

    public NaniteLeechBlade() {
        super(WEIGHT, DICE_FACES); 
        this.effect = new HealthAbsorber(this);
    }
}
