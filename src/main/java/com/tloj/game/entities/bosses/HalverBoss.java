package com.tloj.game.entities.bosses;

import com.tloj.game.utilities.Constants;
import com.tloj.game.utilities.Coordinates;
import com.fasterxml.jackson.annotation.JsonCreator;
import com.fasterxml.jackson.annotation.JsonProperty;
import com.tloj.game.abilities.BossAbility;
import com.tloj.game.abilities.DodgeEvenRollAttack;
import com.tloj.game.abilities.DodgeSlowAttackFlying;
import com.tloj.game.abilities.TakeHalfDamage;
import com.tloj.game.collectables.items.WeaponShard;
import com.tloj.game.entities.Boss;

/**
 * Represents the FlyingBoss Boss entity in the game.<br>
 * The EvenBoss is the second boss of the game. It has a special ability that nullifies received damage is weapon roll is {@literal <} 4<br>
 * It has {@value #HP} health points, {@value #ATTACK} attack points, {@value #DEFENSE} defense points, {@value #DICE_FACES} dice faces, {@value #XP_DROP} experience points drop, {@value #MONEY_DROP} money drop.
 * This boss is paired with the {@link TakeHalfDamage} ability and the {@link WeaponShard} drop.
 * @see Boss
 * @see EvenBoss
 * @see FlyingBoss
 * @see BossAbility
 * @see DodgeSlowAttackFlying
 * @see DodgeEvenRollAttack
 * @see WeaponShard
 */
public class HalverBoss extends Boss {
    public static final int HP = 70;
    public static final int ATTACK = 9;
    public static final int DEFENSE = 8;
    public static final int DICE_FACES = 10;
    public static final int XP_DROP = 50;
    public static final int MONEY_DROP = 40;
    
    @JsonCreator
    public HalverBoss(
        @JsonProperty("position") Coordinates position
    ) {
        super(HP, ATTACK, DEFENSE, DICE_FACES, XP_DROP, MONEY_DROP, position);
        this.ability = new TakeHalfDamage(this);
        this.drop = new WeaponShard();
    }

    @Override
    public String toString() {
        return "Leth, Halver Lord of the Dice";
    }

    @Override
    public String getASCII(){
        return Constants.HALVERBOSS;
    }
}
