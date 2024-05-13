package com.tloj.game.entities.mobs;

import com.fasterxml.jackson.annotation.JsonCreator;
import com.fasterxml.jackson.annotation.JsonProperty;
import com.tloj.game.entities.Mob;
import com.tloj.game.game.Attack;
import com.tloj.game.game.PlayerAttack;
import com.tloj.game.utilities.Constants;
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
    private static final int HP = 7;
    private static final int ATTACK = 3;
    private static final int DEFENSE = 1;
    private static final int DICE_FACES = 4;
    private static final int XP_DROP = 4;
    private static final int MONEY_DROP = 2;

    @JsonCreator
    public JetBat(
        @JsonProperty("position") Coordinates position,
        @JsonProperty("lvl") int lvl
    ) {
        super(HP, ATTACK, DEFENSE, DICE_FACES, lvl, XP_DROP, MONEY_DROP, position);
    }

    @Override
    public void defend(Attack attack) {
        super.defend(attack);
        if (!(attack instanceof PlayerAttack)) return;
        PlayerAttack playerAttack = (PlayerAttack) attack;

        if (Math.random() > 0.35) return;
        playerAttack.setWeaponRoll(0);
    }
    
    @Override
    public String getASCII(){
        return Constants.JET_BAT_STATIC;
    }

    @Override
    public String getCombatASCII() {
        return Constants.JET_BAT_ATTACK;
    }
}
