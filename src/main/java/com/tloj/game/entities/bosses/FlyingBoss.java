package com.tloj.game.entities.bosses;

import com.tloj.game.utilities.Constants;
import com.fasterxml.jackson.annotation.JsonCreator;
import com.fasterxml.jackson.annotation.JsonProperty;
import com.tloj.game.abilities.BossAbility;
import com.tloj.game.abilities.DodgeEvenRoll;
import com.tloj.game.abilities.VampireDodge;
import com.tloj.game.collectables.items.WeaponShard;
import com.tloj.game.entities.Boss;
import com.tloj.game.game.Coordinates;

/**
 * Represents the FlyingBoss, called Flygande, boss entity in the game.<br>
 * The EvenBoss is the second boss of the game. It has a special ability that nullifies received damage is weapon roll is {@literal <} 4<br>
 * It has {@value #HP} health points, {@value #ATTACK} attack points, {@value #DEFENSE} defense points, {@value #DICE_FACES} dice faces, {@value #XP_DROP} experience points drop, {@value #MONEY_DROP} money drop.
 * This boss is paired with the {@link VampireDodge} ability and the {@link WeaponShard} drop.
 * @see Boss
 * @see EvenBoss
 * @see HalverBoss
 * @see BossAbility
 * @see VampireDodge
 * @see DodgeEvenRoll
 * @see WeaponShard
 */
public class FlyingBoss extends Boss {
    public static final int HP = 100;
    public static final int ATTACK = 17;
    public static final int DEFENSE = 12;
    public static final int DICE_FACES = 15;
    public static final int XP_DROP = 70;
    public static final int MONEY_DROP = 50;
    
    @JsonCreator
    public FlyingBoss(
        @JsonProperty("position") Coordinates position
    ) {
        super(HP, ATTACK, DEFENSE, DICE_FACES, XP_DROP, MONEY_DROP, position);
        this.ability = new VampireDodge(this);
        this.drop = null;
    }

    @Override
    public String toString() {
        return "Flygande, Flying Lord of the Dice";
    }

    @Override
    public String getASCII() {
        return Constants.FLYBOSS;
    }
}