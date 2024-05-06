 package com.tloj.game.collectables;


/**
 * Represents the LaserBlade Weapon in the game.<br>
 * The LaserBlade is an average light weapon with a medium range blade that can be found in the game. 
 * It can also inflicts average damage, and it has no ability<br>
 * It weights {@value #WEIGHT}, with a D8 attack points with no bonus.
 * @see Item
 * @see Weapon
 * @see NanoDirk
 * @see CyberKatana
 * @see PulseStaff
 * @see PlasmaGreatSword
 * @see NaniteLeechBlade

 */


public class LaserBlade extends Weapon{
    private static final double WEIGHT= 1;
    private static final int DICE_FACES = 8;    


    public LaserBlade() {
        super(WEIGHT, DICE_FACES); 
    }

    @Override
    public void useEffect(Object ...args) {
        // Add effect here
    }
}
