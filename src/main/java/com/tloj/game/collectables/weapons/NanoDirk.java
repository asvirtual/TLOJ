package com.tloj.game.collectables.weapons;

import com.tloj.game.collectables.Weapon;
import com.tloj.game.collectables.weapons.LaserBlade;
import com.tloj.game.collectables.weapons.NaniteLeechBlade;
import com.tloj.game.collectables.weapons.CyberKatana;
import com.tloj.game.collectables.weapons.PlasmaGreatsword;
import com.tloj.game.collectables.weapons.PulseStaff;
import com.tloj.game.effects.DiceReroller;
import com.fasterxml.jackson.annotation.JsonCreator;
import com.fasterxml.jackson.annotation.JsonProperty;


/**
 * Represents the NanoDirk Weapon in the game.<br>
 * The NanoDirk is a light weapon with a short blade that can be found in the game. <br>
 * It is paired with the {@link DiceReroller} ability, that allows its holder to roll twice its dice per attack<br>
 * It weighs {@value #WEIGHT}, and is equipped with a D{@value #DICE_FACES}
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

    public static String describe() {
        return "Nano Dirk (D6)";
    }
}
