package com.tloj.game.entities;

import java.util.ArrayList;

import com.tloj.game.collectables.Item;
import com.tloj.game.collectables.Weapon;
import com.tloj.game.utilities.Coordinates;

public class NeoSamurai extends Character {
    
    private static final int HP = 20;
    private static final int ATTACK = 7;
    private static final int DEFENCE = 1;
    private static final int MANA = 10;
    private static final int MAX_WEIGHT = 5;
    private static final int MONEY = 15;

    /** 
     * Constructor to create a NeoSamurai from loaded data  
     * @see Character#Character(int, int, int, int, int, int, int, int, Weapon, Object, Object, ArrayList, Coordinates)
    */
    public NeoSamurai(
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
     * Constructor to create an entirely new NeoSamurai 
     * @param position The initial position of the NeoSamurai
     * @param lvl The level of the NeoSamurai
    */

    public NeoSamurai(Coordinates position, int lvl) {
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
            position
        );
    }


}

