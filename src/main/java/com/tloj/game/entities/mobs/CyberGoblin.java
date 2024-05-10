package com.tloj.game.entities.mobs;

import com.fasterxml.jackson.annotation.JsonCreator;
import com.fasterxml.jackson.annotation.JsonProperty;
import com.tloj.game.entities.Mob;
import com.tloj.game.utilities.Coordinates;


/**
 * Represents the CyberGoblin Mob entity in the game.<br>
 * The CyberGoblin is a weak but quick enemy that can be found in the game. It has average health and low defense, but inflicts some damage<br>
 * It has {@value #HP} health points, {@value #ATTACK} attack points, {@value #DEFENSE} defense points, {@value #DICE_FACES} dice faces, {@value #XP_DROP} experience points drop, {@value #MONEY_DROP} money drop.
 * @see Mob
 * @see JetBat
 * @see JunkSlime
 * @see MechaRat
 */
public class CyberGoblin extends Mob {
    private static final int HP = 10;
    private static final int ATTACK = 3;
    private static final int DEFENSE = 1;
    private static final int DICE_FACES = 6;
    private static final int XP_DROP = 5;
    private static final int MONEY_DROP = 2;

    @JsonCreator
    public CyberGoblin(
        @JsonProperty("position") Coordinates position,
        @JsonProperty("lvl") int lvl
    ) {
        super(HP, ATTACK, DEFENSE, DICE_FACES, lvl, XP_DROP, MONEY_DROP, position);
    }    
}
