package com.tloj.game.entities.mobs;

import com.fasterxml.jackson.annotation.JsonCreator;
import com.fasterxml.jackson.annotation.JsonProperty;
import com.tloj.game.abilities.CyberPoisonAbility;
import com.tloj.game.entities.Mob;
import com.tloj.game.game.Attack;
import com.tloj.game.game.PlayerAttack;
import com.tloj.game.utilities.Constants;
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
    private static final int ATTACK = 3;
    private static final int DEFENSE = 3;
    private static final int DICE_FACES = 4;
    private static final int XP_DROP = 6;
    private static final int MONEY_DROP = 2;

    @JsonCreator
    public JunkSlime(
        @JsonProperty("position") Coordinates position,
        @JsonProperty("lvl") int lvl
    ) {
        super(HP, ATTACK, DEFENSE, DICE_FACES, lvl, XP_DROP, MONEY_DROP, position);
        this.ability = new CyberPoisonAbility(this);
    }
    
    @Override
    public String getASCII(){
        return Constants.JUNK_SLIME_STATIC;
    }

    @Override
    public String getCombatASCII() {
        return Constants.JUNK_SLIME_ATTACK;
    }
}
