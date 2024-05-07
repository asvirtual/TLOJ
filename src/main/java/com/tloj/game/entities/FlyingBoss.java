package com.tloj.game.entities;

import com.tloj.game.utilities.Coordinates;
import com.tloj.game.abilities.DodgeSlowAttackFlying;
import com.tloj.game.collectables.WeaponShard;


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
}