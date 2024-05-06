package com.tloj.game.collectables;


/**
 * Represents the PulseStaff Weapon in the game.<br>
 * The PulseStaff is an average heavy weapon with a long blade that can be found in the game. 
 * If you spend 3 mana to attack it inflicts damage + bonus, more is the mana, then more is the damage. If there's no mana left, then it inflicts no bonus damage<br>
 * It weights {@value #WEIGHT}, with a D5 attack points with no bonus, D10 with max bonus.
 * @see Item
 * @see Weapon
 * @see LaserBlade
 * @see CyberKatana
 * @see NanoDirk
 * @see PlasmaGreatSword
 * @see NaniteLeechBlade
 */
public class PulseStaff extends Weapon{
    private static final double WEIGHT = 1.2;
    private static final int DICE_FACES = 5;    //5 standard value if no mana then -->

    public PulseStaff() {
        super(WEIGHT, DICE_FACES); 
    }

    @Override
    public void useEffect(Object ...args) {
        // Add effect here
    }
}
