package com.tloj.game.collectables;


/**
 * Represents the PlasmaGreatsword Weapon in the game.<br>
 * The PlasmaGreatsword is an heavy weapon with a long range blade that can be found in the game. 
 * It can also inflicts average damage, and it has no ability<br>
 * It weights {@value #WEIGHT}, with a D15 attack points with no bonus.
 * @see Item
 * @see Weapon
 * @see NanoDirk
 * @see CyberKatana
 * @see PulseStaff
 * @see LaserBlade
 * @see NaniteLeechBlade

 */


public class PlasmaGreatsword extends Weapon{
    private static final double WEIGHT = 2.5;
    private static final int DICE_FACES = 15;

    public PlasmaGreatsword() {
        super(WEIGHT, DICE_FACES); 
    }

    @Override
    public void useEffect(Object ...args) {
        // Add effect here
    }
}
