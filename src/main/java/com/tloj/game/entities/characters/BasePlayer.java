package com.tloj.game.entities.characters;

import java.util.ArrayList;

import com.tloj.game.collectables.Item;
import com.tloj.game.collectables.Weapon;
import com.tloj.game.entities.Character;
import com.tloj.game.utilities.Coordinates;


/**
 * Class to represent the BasePlayer class in the game<br>
 * It has the following fixed initial attributes:<br>
 * HP 20 health points<br>
 * ATTACK 4 attack points<br>
 * DEFENSE 4 defense points<br>
 * MANA 10 mana points<br>
 * MAX_WEIGHT 5 maximum weight capacity<br>
 * MONEY 10 money<br>
 * @see Character
 */
public class BasePlayer extends Character {
    private static final int HP = 20;
    private static final int ATTACK = 4;
    private static final int DEFENSE = 4;
    private static final int MANA = 10;
    private static final int MAX_WEIGHT = -1; // Missing from doc
    private static final int MONEY = 10;

    /**
     * Default constructor to allow Jackson to deserialize JSON.
     */
    public BasePlayer() {}

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
            DEFENSE,
            MANA,
            MAX_WEIGHT,
            MONEY,
            null,
            coordinates
        );
    }

    public static String getDetailedInfo() {
        return "HP: " + HP + "\n" +
               "Attack: " + ATTACK + "\n" +
               "Defense: " + DEFENSE + "\n" +
               "Mana: " + MANA + "\n" +
               "Weapon: LaserBlade (D8)" + "\n" +
               "Ability: Focus - Adds 3 damage on next attack" + "\n" +
               "Money: " + MONEY;
    }
}
