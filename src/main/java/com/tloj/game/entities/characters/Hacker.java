package com.tloj.game.entities.characters;

import java.util.ArrayList;

import com.tloj.game.collectables.Item;
import com.tloj.game.collectables.Weapon;
import com.tloj.game.entities.Character;
import com.tloj.game.skills.CheatEngine;
import com.tloj.game.utilities.Coordinates;


public class Hacker extends Character {
    private static final int HP = 15;
    private static final int ATTACK = 3;
    private static final int DEFENSE = 3;
    private static final int MANA = 30;
    private static final int MAX_WEIGHT = 5;
    private static final int MONEY = 10;

    /**
     * Default constructor to allow Jackson to deserialize JSON.
     */
    public Hacker() {}

    /** 
     * Constructor to create a Cheater from loaded data 
     * @see Character#Character(int, int, int, int, int, int, int, int, Weapon, Object, Object, ArrayList, Coordinates)
    */
    public Hacker(
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

        this.skill = new CheatEngine(this);
    }    

    /** 
     * Constructor to create an entirely new Cheater 
     * @param position The initial position of the Cheater
     * @param lvl The level of the Cheater
    */
    public Hacker(Coordinates position, int lvl) { 
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

        this.skill = new CheatEngine(this);
    }

    public static String getDetailedInfo() {
        return "HP: " + HP + "\n" +
               "Attack: " + ATTACK + "\n" +
               "Defense: " + DEFENSE + "\n" +
               "Mana: " + MANA + "\n" +
               "Weapon: Pulse-Staff (D5-10) - costs 3 mana to use" + "\n" +
               "Ability: CheatEngine - Forces max roll on next attack" + "\n" +
               "Money: " + MONEY;
    }

}
 
