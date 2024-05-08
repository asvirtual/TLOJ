package com.tloj.game.entities.bosses;

import com.tloj.game.utilities.Coordinates;
import com.tloj.game.abilities.TakeHalfDamage;

/**
 * Represents the FlyingBoss Boss entity in the game.<br>
 * The EvenBoss is the second boss of the game. It has a special ability that nullifies received damage is weapon roll is < 4<br>
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



public class HalverBoss extends Boss{
    public static final int HP = 150;
    public static final int ATTACK = 15;
    public static final int DEFENSE = 10;
    public static final int DICE_FACES = 15;
    public static final int XP_DROP = 75;
    public static final int MONEY_DROP = 100;
    
    public HalverBoss(Coordinates position, int lvl) {
        super(HP, ATTACK, DEFENSE, DICE_FACES, lvl, XP_DROP, MONEY_DROP,position);
        this.ability = new TakeHalfDamage(this);
    }

    @Override
    public String toString() {
        return "Leth, Halver Lord of the Dice";
    }

}
