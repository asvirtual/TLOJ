package com.tloj.game.entities.mobs;

import com.fasterxml.jackson.annotation.JsonCreator;
import com.fasterxml.jackson.annotation.JsonProperty;
import com.tloj.game.entities.Mob;
import com.tloj.game.utilities.Constants;
import com.tloj.game.utilities.Coordinates;
import com.tloj.game.abilities.RatBiteAbility;


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
    private static final int ATTACK = 3;
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
        this.ability = new RatBiteAbility(this);
    }

    @Override
    protected void levelUp(int lvl) {
        switch (lvl) {
            case 2:
                this.atk += 3; // Total attack = 6
                this.def += 2;  // Total defense = 4
                break;
            case 3:
                this.atk += 6;  // Total attack = 12
                this.def += 2;  // Total defense = 6
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
        return Constants.MECHA_RAT_STATIC;
    }

    @Override
    public String getCombatASCII() {
        return Constants.MECHA_RAT_ATTACK;
    }
}
