package com.tloj.game.entities.bosses;

import com.tloj.game.utilities.Coordinates;
import com.tloj.game.abilities.DodgeEvenRollAttack;
import com.tloj.game.abilities.DodgeSlowAttackFlying;
import com.tloj.game.abilities.TakeHalfDamage;
import com.tloj.game.collectables.items.WeaponShard;
import com.tloj.game.entities.Boss;
import com.tloj.game.abilities.BossAbility;


/**
 * Represents the EvenBoss Boss entity in the game.<br>
 * The EvenBoss is the first boss of the game. It has a special ability that nullifies received damage is weapon roll is even<br>
 * It has {@value #HP} health points, {@value #ATTACK} attack points, {@value #DEFENSE} defense points, {@value #DICE_FACES} dice faces, {@value #XP_DROP} experience points drop, {@value #MONEY_DROP} money drop.
 * This boss is paired with the {@link DodgeEvenRollAttack} ability and the {@link WeaponShard} drop.    
 * @see Boss
 * @see FlyingBoss
 * @see HalverBoss
 * @see BossAbility
 * @see DodgeSlowAttackFlying
 * @see TakeHalfDamage
 * @see WeaponShard
 */


public class EvenBoss extends Boss{
    public static final int HP = 50;
    public static final int ATTACK = 8;
    public static final int DEFENSE = 5;
    public static final int DICE_FACES = 10;
    public static final int XP_DROP = 30;
    public static final int MONEY_DROP = 50;

    /**
     * Default constructor to allow Jackson to deserialize JSON.
     */
    public EvenBoss() {}
    
    public EvenBoss(Coordinates position, int lvl) {
        super(HP, ATTACK, DEFENSE, DICE_FACES, lvl, XP_DROP, MONEY_DROP,position);
        this.ability = new DodgeEvenRollAttack(this);
        this.drop = new WeaponShard();
    }

    @Override
    public String toString() {
        return "Cyfartal, Even Lord of the Dice";
    }
}
