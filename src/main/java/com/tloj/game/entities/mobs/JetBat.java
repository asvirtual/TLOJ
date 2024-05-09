package com.tloj.game.entities.mobs;

import com.tloj.game.entities.Mob;
import com.tloj.game.utilities.Coordinates;


/**
 * Represents the JetBat Mob entity in the game.<br>
 * The JetBat is a flying enemy that can be found in the game.<br>
 * It has {@value #HP} health points, {@value #ATTACK} attack points, {@value #DEFENSE} defense points, {@value #DICE_FACES} dice faces, {@value #XP_DROP} experience points drop, {@value #MONEY_DROP} money drop.
 * @see Mob
 * @see CyberGoblin
 * @see JunkSlime
 * @see MechaRat
 */
public class JetBat extends Mob {
    private static final int HP = 6;
    private static final int ATTACK = 3;
    private static final int DEFENSE = 1;
    private static final int DICE_FACES = 6;
    private static final int XP_DROP = 2;
    private static final int MONEY_DROP = 1;

    /**
     * Default constructor to allow Jackson to deserialize JSON.
     */
    public JetBat() {}

    public JetBat(Coordinates position, int lvl) {
        super(HP, ATTACK, DEFENSE, DICE_FACES, lvl, XP_DROP, MONEY_DROP, position);
    }
}
