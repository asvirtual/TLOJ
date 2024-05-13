package com.tloj.game.entities.mobs;

import com.fasterxml.jackson.annotation.JsonCreator;
import com.fasterxml.jackson.annotation.JsonProperty;
import com.tloj.game.entities.Mob;
import com.tloj.game.utilities.Constants;
import com.tloj.game.utilities.Coordinates;


/**
 * Represents the MechaRat Mob entity in the game.<br>
 * The MechaRat is a small enemy that can be found in the game. It has low hp, but some attack and defense<br>
 * It has {@value #HP} health points, {@value #ATTACK} attack points, {@value #DEFENSE} defense points, {@value #DICE_FACES} dice faces, {@value #XP_DROP} experience points drop, {@value #MONEY_DROP} money drop.
 * @see Mob
 * @see JetBat
 * @see JunkSlime
 * @see CyberGoblin
 */
public class MechaRat extends Mob  {
    private static final int HP = 8;
    private static final int ATTACK = 2;
    private static final int DEFENSE = 2;
    private static final int DICE_FACES = 8;
    private static final int XP_DROP = 5;
    private static final int MONEY_DROP = 2;

    @JsonCreator
    public MechaRat(
        @JsonProperty("position") Coordinates position,
        @JsonProperty("lvl") int lvl
    ) {
        super(HP, ATTACK, DEFENSE, DICE_FACES, lvl, XP_DROP, MONEY_DROP, position);
    }
    
    @Override
    public String getASCII(){
        return Constants.MECHA_RAT_STATIC;
    }

    @Override
    public String getCombatASCII() {
        return Constants.MECHA_RAT_ATTACK;
    }
}
