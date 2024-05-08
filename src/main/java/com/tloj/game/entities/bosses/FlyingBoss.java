package com.tloj.game.entities.bosses;

import com.tloj.game.utilities.Coordinates;
import com.tloj.game.abilities.BossAbility;
import com.tloj.game.abilities.DodgeSlowAttackFlying;
import com.tloj.game.collectables.items.WeaponShard;

/**
 * Represents the FlyingBoss Boss entity in the game.<br>
 * The EvenBoss is the second boss of the game. It has a special ability that nullifies received damage is weapon roll is < 4<br>
 * It has {@value #HP} health points, {@value #ATTACK} attack points, {@value #DEFENSE} defense points, {@value #DICE_FACES} dice faces, {@value #XP_DROP} experience points drop, {@value #MONEY_DROP} money drop.
 * This boss is paired with the {@link DodgeSlowAttackFlying} ability and the {@link WeaponShard} drop.
 * @see Boss
 * @see EvenBoss
 * @see HalverBoss
 * @see BossAbility
 * @see DodgeSlowAttackFlying
 * @see DodgeEvenRollAttack
 * @see WeaponShard
 */


public class FlyingBoss extends Boss{
    public static final int HP = 80;
    public static final int ATTACK = 10;
    public static final int DEFENSE = 6;
    public static final int DICE_FACES = 12;
    public static final int XP_DROP = 50;
    public static final int MONEY_DROP = 80;
    
    public FlyingBoss(Coordinates position, int lvl) {
        super(HP, ATTACK, DEFENSE, DICE_FACES, lvl, XP_DROP, MONEY_DROP,position);
        this.ability = new DodgeSlowAttackFlying(this);
        this.drop = new WeaponShard();
    }

    @Override
    public String toString() {
        return "Flygande, Flying Lord of the Dice";
    }
}