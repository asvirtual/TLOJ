package com.tloj.game.entities.mobs;

import com.tloj.game.entities.Mob;
import com.tloj.game.utilities.Coordinates;


/**
 * Represents the JunkSlime Mob entity in the game.<br>
 * The JunkSlime is a resilient enemy that can be found in the game. It deals low damage, but has a high health and defense.<br>
 * It has {@value #HP} health points, {@value #ATTACK} attack points, {@value #DEFENSE} defense points, {@value #DICE_FACES} dice faces, {@value #XP_DROP} experience points drop, {@value #MONEY_DROP} money drop.
 * @see Mob
 * @see JetBat
 * @see CyberGoblin
 * @see MechaRat
 */
public class JunkSlime extends Mob {
    private static final int HP = 16;
    private static final int ATTACK = 1;
    private static final int DEFENSE = 3;
    private static final int DICE_FACES = 6;
    private static final int XP_DROP = 3;
    private static final int MONEY_DROP = 1;

    /**
     * Default constructor to allow Jackson to deserialize JSON.
     */
    public JunkSlime() {}

    public JunkSlime(Coordinates position, int lvl) {
        super(HP, ATTACK, DEFENSE, DICE_FACES, lvl, XP_DROP, MONEY_DROP, position);
    }
}
