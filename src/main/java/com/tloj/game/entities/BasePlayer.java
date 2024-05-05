package com.tloj.game.entities;

import com.tloj.game.utilities.Coordinates;


public class BasePlayer extends Character {
    private static final int HP = 20;
    private static final int ATTACK = 4;
    private static final int DEFENSE = 4;
    private static final int MANA = 10;
    private static final int MAX_WEIGHT = -1; // Missing from doc
    private static final int MONEY = 10;

    private static final int DICE_FACES = 6;

    public BasePlayer(Coordinates coordinates) {
        super(
            HP,
            ATTACK,
            DEFENSE,
            MANA,
            MAX_WEIGHT,
            MONEY,
            DICE_FACES,
            null,
            null,
            null,
            coordinates
        );
    }
}
