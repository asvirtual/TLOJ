package com.tloj.game.collectables.weapons;

import com.tloj.game.collectables.Weapon;
import com.tloj.game.effects.DiceReroller;


/**
 * Represents the NanoDirk Weapon in the game.<br>
 * The NanoDirk is a light weapon with a short blade that can be found in the game. <br>
 * It is paired with the {@link DiceReroller} ability, that allows its holder to roll twice its dice per attack<br>
 * It weighs {@value #WEIGHT}, and is equipped with a D{@value #DICE_FACES}
 * @see LaserBlade
 * @see CyberKatana
 * @see PulseStaff
 * @see PlasmaGreatSword
 * @see NaniteLeechBlade
 */
public class NanoDirk extends Weapon {
    private static final double WEIGHT = 0.5;
    private static final int DICE_FACES = 6;

    public NanoDirk() {
        super(WEIGHT, DICE_FACES); 
        this.effect = new DiceReroller(this);
    }

    @Override
    public String toString() {
        return "Nano Dirk";
    }
}
