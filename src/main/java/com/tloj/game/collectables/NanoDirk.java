package com.tloj.game.collectables;


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
    private static final double WEIGHT = 0.5;
    private static final int DICE_FACES = 6;

    public NanoDirk() {
        super(WEIGHT, DICE_FACES); 
    }

    @Override
    public void useEffect(Object ...args) {
        // Add effect here
    }
}
