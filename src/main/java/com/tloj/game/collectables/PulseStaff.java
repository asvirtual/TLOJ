package com.tloj.game.collectables;

import com.tloj.game.abilities.ManaAttackBooster;


/**
 * Represents the PulseStaff Weapon in the game.<br>
 * The PulseStaff is an rare heavy weapon with a long blade that can be found in the game. <br>
 * It is paired with the {@link ManaAttackBooster} ability, that allows its holder to inflict from 5 to 10 more damage points by spending 3 mana points<br>
 * It weighs {@value #WEIGHT}, and is equipped with a D{@value #DICE_FACES}
 * @see LaserBlade
 * @see CyberKatana
 * @see NanoDirk
 * @see PlasmaGreatSword
 * @see NaniteLeechBlade
 */
public class PulseStaff extends Weapon {
    private static final double WEIGHT = 1.2;
    private static final int DICE_FACES = 5;    //5 standard value if no mana then -->

    public PulseStaff() {
        super(WEIGHT, DICE_FACES);
        this.effect = new ManaAttackBooster(this); 
    }
}
