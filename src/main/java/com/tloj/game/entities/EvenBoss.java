package com.tloj.game.entities;

import com.tloj.game.utilities.Coordinates;
import com.tloj.game.abilities.DodgeEvenRollAttack;
import com.tloj.game.collectables.WeaponShard;


public class EvenBoss extends Boss{
    public static final int HP = 50;
    public static final int ATTACK = 8;
    public static final int DEFENSE = 5;
    public static final int DICE_FACES = 10;
    public static final int XP_DROP = 30;
    public static final int MONEY_DROP = 50;
    
    public EvenBoss(Coordinates position, int lvl) {
        super(HP, ATTACK, DEFENSE, DICE_FACES, lvl, XP_DROP, MONEY_DROP,position);
        this.ability = new DodgeEvenRollAttack(this);
        this.drop = new WeaponShard();
    }
}
