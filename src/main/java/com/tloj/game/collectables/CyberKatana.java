package com.tloj.game.collectables;

import com.tloj.game.utilities.Dice;

/**
 * Represents the CyberKatana Weapon in the game.<br>
 * The CyberKatana is a light but sharp blade that can be found in the game. It can inflicts great damage, but it has no ability<br>
 * It weights {@value #WEIGHT}, with a D10 attack points.
 * @see Item
 * @see Weapon
 * @see LaserBlade
 * @see PulseStaff
 * @see NanoDirk
 * @see PlasmaGreatSword
 * @see NaniteLeechBlade

 */


public class CyberKatana extends Weapon{
    private static final double WEIGHT=0.8;
    private static final Dice DICE=new Dice(10);
    private static Object ability=null;



    public CyberKatana() {
        super(WEIGHT,DICE,ability); 
    }
}
