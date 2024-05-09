package com.tloj.game.collectables.weapons;

import com.tloj.game.collectables.Weapon;
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
    private static final int ID = 16;

    public NaniteLeechBlade() {
        super(WEIGHT, DICE_FACES, ID); 
        this.effect = new HealthAbsorber(this);
    }

    @Override
    public String toString() {
        return "Nanite Leech Blade";
    }
}
