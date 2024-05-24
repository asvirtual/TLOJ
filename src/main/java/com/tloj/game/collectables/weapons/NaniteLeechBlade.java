package com.tloj.game.collectables.weapons;

import com.tloj.game.collectables.Weapon;
import com.tloj.game.collectables.weaponeffects.HealthAbsorber;
import com.tloj.game.utilities.Constants;
import com.fasterxml.jackson.annotation.JsonCreator;

/**
 * Represents the NaniteLeechBlade Weapon in the game.<br>
 * The NaniteLeechBlade is a light weapon with a short range blade that can be found in the game. <br>
 * It is paired with the {@link HealthAbsorber} ability<br>
 * It weighs {@value #WEIGHT}, and is equipped with a D{@value #DICE_FACES}
 * @see NanoDirk
 * @see CyberKatana
 * @see PulseStaff
 * @see LaserBlade
 * @see PlasmaGreatsword
 */
public class NaniteLeechBlade extends Weapon {
    private static final double WEIGHT = 0.8;
    private static final int DICE_FACES = 12;
    private static final int ID = 16;

    @JsonCreator
    public NaniteLeechBlade() {
        super(WEIGHT, DICE_FACES, ID); 
        this.effect = new HealthAbsorber(this);
    }

    public static String weaponInfo() {
        return "Nanite Leech Blade - D12 - Steal health from your enemies";
    }

    @Override
    public String describe() {
        return "Nanite Leech Blade, built with a D12 dice, it's a legendary scythe sealed away for its cursed power" + "\n" +
               "Its effect is stealing health from the enemies, turning half of the damage dealt into health for the wielder"
               + "\n It weights: " + WEIGHT + " Mb";
    }

    @Override
    public String getASCII() {
        return Constants.NANITE_LEECH_BLADE;
    }
}
