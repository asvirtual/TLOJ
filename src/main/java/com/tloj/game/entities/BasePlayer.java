package com.tloj.game.entities;

import java.util.ArrayList;

import com.tloj.game.collectables.Item;
import com.tloj.game.collectables.Weapon;
import com.tloj.game.utilities.Coordinates;


/**
 * Class to represent the BasePlayer class in the game<br>
 * It has the following fixed initial attributes:<br>
 * HP 20 health points<br>
 * ATTACK 4 attack points<br>
 * DEFENCE 4 defence points<br>
 * MANA 10 mana points<br>
 * MAX_WEIGHT 5 maximum weight capacity<br>
 * MONEY 10 money<br>
 * @see Character
 */
public class BasePlayer extends Character {
    private static final int HP = 20;
    private static final int ATTACK = 4;
    private static final int DEFENCE = 4;
    private static final int MANA = 10;
    private static final int MAX_WEIGHT = -1; // Missing from doc
    private static final int MONEY = 10;

    /** 
     * Constructor to create a BasePlayer from loaded data 
     * @see Character#Character(int, int, int, int, int, int, int, int, Weapon, Object, Object, ArrayList, Coordinates)
    */
    public BasePlayer(
        int hp,
        int atk,
        int def,
        int mana,
        int xp,
        int lvl,
        int maxWeight,
        int money,
        Weapon weapon,
        Object ability,
        Object passiveAbility,
        ArrayList<Item> inventory,
        Coordinates position
    ) {
        super(
            hp,
            atk,
            def,
            mana,
            xp,
            lvl,
            maxWeight,
            money,
            weapon,
            ability,
            passiveAbility,
            inventory,
            position
        );
    }

    /** 
     * Constructor to create an entirely new BasePlayer 
     * @param position The initial position of the BasePlayer
     * @param lvl The level of the BasePlayer
    */
    public BasePlayer(Coordinates coordinates) {
        super(
            HP,
            ATTACK,
            DEFENCE,
            MANA,
            MAX_WEIGHT,
            MONEY,
            null,
            null,
            null,
            coordinates
        );
    }
}
