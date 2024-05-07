package com.tloj.game.entities;

import java.util.ArrayList;

import com.tloj.game.collectables.Item;
import com.tloj.game.collectables.Weapon;
import com.tloj.game.utilities.Coordinates;


public class Cheater extends Character {
    private static final int HP = 15;
    private static final int ATTACK = 3;
    private static final int DEFENSE = 3;
    private static final int MANA = 30;
    private static final int MAX_WEIGHT = 5;
    private static final int MONEY = 10;

    /** 
     * Constructor to create a Cheater from loaded data 
     * @see Character#Character(int, int, int, int, int, int, int, int, Weapon, Object, Object, ArrayList, Coordinates)
    */
    public Cheater(
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
     * Constructor to create an entirely new Cheater 
     * @param position The initial position of the Cheater
     * @param lvl The level of the Cheater
    */
    public Cheater(Coordinates position, int lvl) { 
        super(
            HP,
            ATTACK,
            DEFENSE,
            MANA,
            MAX_WEIGHT,
            MONEY,
            null,
            null,
            null,
            position
        );
    }


}
 
