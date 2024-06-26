package com.tloj.game.entities.mobs;

import com.fasterxml.jackson.annotation.JsonCreator;
import com.fasterxml.jackson.annotation.JsonProperty;
import com.tloj.game.entities.Mob;
import com.tloj.game.game.Coordinates;
import com.tloj.game.utilities.Constants;
import com.tloj.game.abilities.StealItem;


/**
 * Represents the CyberGoblin Mob entity in the game.<br>
 * The CyberGoblin is a weak but quick enemy that can be found in the game. It has average health and low defense, but inflicts some damage<br>
 * It has {@value #HP} health points <br>
 * {@value #ATTACK} attack points <br>
 * {@value #DEFENSE} defense points <br>
 * {@value #DICE_FACES} dice faces <br>
 * {@value #XP_DROP} experience points drop <br>
 * {@value #MONEY_DROP} money drop <br>
 * It has the {@link StealItem} ability<br>
 * @see Mob
 * @see JetBat
 * @see JunkSlime
 * @see MechaRat
 */
public class CyberGoblin extends Mob {
    private static final int HP = 10;
    private static final int ATTACK = 4;
    private static final int DEFENSE = 2;
    private static final int DICE_FACES = 7;
    private static final int XP_DROP = 5;
    private static final int MONEY_DROP = 3;

    @JsonCreator
    public CyberGoblin(
        @JsonProperty("position") Coordinates position,
        @JsonProperty("lvl") int lvl
    ) {
        super(HP, ATTACK, DEFENSE, DICE_FACES, lvl, XP_DROP, MONEY_DROP, position);
        this.ability = new StealItem(this);
    }
    
    @Override
    public void levelUp(int lvl) {
        switch (lvl) {
            case 2:
                this.atk += 4; // Total attack = 8
                this.def += 2;  // Total defense = 4
                break;
            case 3:
                this.atk += 6;  // Total attack = 14
                this.def += 3;  // Total defense = 7
                break;
            default:
                this.atk += 4 * (lvl - 3);  // Increase attack by 4 for each level beyond 3
                this.def += 2 * (lvl - 3);  // Increase defense by 2 for each level beyond 3
                break;
        }

        super.levelUp(lvl);
    }

    @Override
    public String getASCII() {
        return Constants.CYBER_GOBLIN_STATIC;
    }

    @Override
    public String getCombatASCII() {
        return Constants.CYBER_GOBLIN_ATTACK;
    }
}
