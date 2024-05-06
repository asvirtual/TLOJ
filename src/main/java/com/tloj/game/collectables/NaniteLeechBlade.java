package com.tloj.game.collectables;

import com.tloj.game.utilities.Dice;

/**
 * Represents the NaniteLeechBlade Weapon in the game.<br>
 * The NaniteLeechBlade is a light weapon with a short range blade that can be found in the game. 
 * It can also inflicts average damage, and it has no ability<br>
 * It weights {@value #WEIGHT}, with a D12 attack points with no bonus.
 * @see Item
 * @see Weapon
 * @see NanoDirk
 * @see CyberKatana
 * @see PulseStaff
 * @see LaserBlade
 * @see PlasmaGreatSword

 */


public class NaniteLeechBlade extends Weapon{
    private static final double WEIGHT=0.8;
    private static final Dice DICE=new Dice(12);    
    private static Object ability=null; //TODO: implement ability



    public NaniteLeechBlade() {
        super(WEIGHT,DICE,ability); 
    }
}
