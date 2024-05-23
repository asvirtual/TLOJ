package com.tloj.game.entities.mobs;

import com.fasterxml.jackson.annotation.JsonCreator;
import com.fasterxml.jackson.annotation.JsonProperty;
import com.tloj.game.abilities.DodgeFlying;
import com.tloj.game.entities.Mob;
import com.tloj.game.game.Coordinates;
import com.tloj.game.utilities.Constants;


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
    private static final int DICE_FACES = 5;
    private static final int XP_DROP = 4;
    private static final int MONEY_DROP = 2;

    @JsonCreator
    public JetBat(
        @JsonProperty("position") Coordinates position,
        @JsonProperty("lvl") int lvl
    ) {
        super(HP, ATTACK, DEFENSE, DICE_FACES, lvl, XP_DROP, MONEY_DROP, position);
        this.ability = new DodgeFlying(this);
    }

    @Override
    public void levelUp(int lvl) {
        switch (lvl) {
            case 2:
                this.atk += 3; // Total attack = 6
                this.def += 1;  // Total defense = 2
                break;
            case 3:
                this.atk += 8;  // Total attack = 14
                this.def += 3;  // Total defense = 5
                break;
            default:
                this.atk += 3 * (lvl - 3);  // Increase attack by 3 for each level beyond 3
                this.def += lvl - 3;  // Increase defense by 1 for each level beyond 3
                break;
        }

        super.levelUp(lvl);
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
