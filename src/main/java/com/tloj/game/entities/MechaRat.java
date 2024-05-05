package com.tloj.game.entities;

import com.tloj.game.utilities.Coordinates;


public class MechaRat extends Mob  {
    private static final int HP = 8;
    private static final int ATTACK = 2;
    private static final int DEFENSE = 2;
    private static final int DICE_FACES = 6;
    private static final int XP_DROP = 2;
    private static final int MONEY_DROP = 1;

    public MechaRat(Coordinates position) {
        super(HP, ATTACK, DEFENSE, DICE_FACES, XP_DROP, MONEY_DROP, position);
    }
}
