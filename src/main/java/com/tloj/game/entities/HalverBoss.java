package com.tloj.game.entities;

import com.tloj.game.utilities.Coordinates;
import com.tloj.game.abilities.TakeHalfDamage;


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

}
