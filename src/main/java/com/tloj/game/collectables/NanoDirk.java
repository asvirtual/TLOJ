package com.tloj.game.collectables;

import com.tloj.game.utilities.Dice;

/**
 * Represents the NanoDirk Weapon in the game.<br>
 * The NanoDirk is a light weapon with a short blade that can be found in the game. 
 * It can inflicts average damage, it has the ability to thrown the dice twice if it was a bad roll<br>
 * It weights {@value #WEIGHT}, with a D6 attack points with no bonus.
 * @see Item
 * @see Weapon
 * @see LaserBlade
 * @see CyberKatana
 * @see PulseStaff
 * @see PlasmaGreatSword
 * @see NaniteLeechBlade

 */


public class NanoDirk extends Weapon{
    private static final double WEIGHT=0.5;
    private static final Dice DICE=new Dice(6);    
    private static Object ability=null; //TODO: implement ability



    public NanoDirk() {
        super(WEIGHT,DICE,ability); 
    }
}
