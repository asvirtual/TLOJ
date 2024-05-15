package com.tloj.game.entities.characters;

import java.util.ArrayList;

import com.fasterxml.jackson.annotation.JsonCreator;
import com.fasterxml.jackson.annotation.JsonProperty;
import com.tloj.game.collectables.Item;
import com.tloj.game.collectables.Weapon;
import com.tloj.game.collectables.weapons.LaserBlade;
import com.tloj.game.entities.Character;
import com.tloj.game.skills.Focus;
import com.tloj.game.utilities.Constants;
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
    private static final int HP = 25;
    private static final int ATTACK = 3;
    private static final int DEFENSE = 3;
    private static final int MANA = 15;
    private static final int MAX_WEIGHT = 5;
    private static final int MONEY = 10;

    /** 
     * Constructor to create a BasePlayer from loaded data 
     * See {@link Character#Character(int, int, int, int, int, int, int, int, Weapon,  ArrayList, Coordinates)}
    */
    @JsonCreator
    public BasePlayer(
        @JsonProperty("hp") int hp,
        @JsonProperty("atk") int atk,
        @JsonProperty("def") int def,
        @JsonProperty("mana") int mana,
        @JsonProperty("xp") int xp,
        @JsonProperty("lvl") int lvl,
        @JsonProperty("maxWeight") int maxWeight,
        @JsonProperty("money") int money,
        @JsonProperty("weapon") Weapon weapon,
        @JsonProperty("inventory") ArrayList<Item> inventory,
        @JsonProperty("position") Coordinates position
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

        this.skill = new Focus(this);
    }

    /** 
     * Constructor to create an entirely new BasePlayer 
     * @param coordinates The initial position of the BasePlayer
    */
    public BasePlayer(
        Coordinates position
    ) {
        super(
            HP,
            ATTACK,
            DEFENSE,
            MANA,
            MAX_WEIGHT,
            MONEY,
            new LaserBlade(),
            position
        );

        this.skill = new Focus(this);
    }

    public static String getDetailedInfo() {
        return "HP: " + HP + "\n" +
               "Attack: " + ATTACK + "\n" +
               "Defense: " + DEFENSE + "\n" +
               "Mana: " + MANA + "\n" +
               "Weapon: " + LaserBlade.weaponInfo() + "\n" +
               "Ability: " + Focus.describe() + "\n" +
               "BTC: " + MONEY;
    }

    @Override
    public String getASCII() {
        return Constants.BASE_PLAYER;
    }
}
