package com.tloj.game.entities.characters;

import java.util.ArrayList;

import com.tloj.game.collectables.Item;
import com.tloj.game.collectables.Weapon;
import com.tloj.game.entities.Character;
import com.tloj.game.skills.Steal;
import com.tloj.game.utilities.Coordinates;


public class DataThief extends Character {
    
    private static final int HP = 16;
    private static final int ATTACK = 5;
    private static final int DEFENSE = 3;
    private static final int MANA = 10;
    private static final int MAX_WEIGHT = 5;
    private static final int MONEY = 0;

    /** 
     * Constructor to create a DataThief from loaded data  
     * @see Character#Character(int, int, int, int, int, int, int, int, Weapon, Object, Object, ArrayList, Coordinates)
    */
    public DataThief(
        int hp,
        int atk,
        int def,
        int mana,
        int xp,
        int lvl,
        int maxWeight,
        int money,
        Weapon weapon,
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
            inventory,
            position
        );

        this.skill = new Steal(this);
    }    

    /** 
     * Constructor to create an entirely new DataThief 
     * @param position The initial position of the DataThief
     * @param lvl The level of the DataThief
    */
    public DataThief(Coordinates position, int lvl) {    
        super(
            HP,
            ATTACK,
            DEFENSE,
            MANA,
            MAX_WEIGHT,
            MONEY,
            null,
            position
        );

        this.skill = new Steal(this);
    }
}