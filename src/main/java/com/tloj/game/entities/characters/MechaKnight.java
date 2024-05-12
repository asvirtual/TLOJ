package com.tloj.game.entities.characters;

import java.util.ArrayList;

import com.fasterxml.jackson.annotation.JsonCreator;
import com.fasterxml.jackson.annotation.JsonProperty;
import com.tloj.game.collectables.Item;
import com.tloj.game.collectables.Weapon;
import com.tloj.game.collectables.weapons.PlasmaGreatsword;
import com.tloj.game.entities.Character;
import com.tloj.game.skills.Guard;
import com.tloj.game.utilities.Constants;
import com.tloj.game.utilities.Coordinates;

public class MechaKnight extends Character {
    private static final int HP = 30;
    private static final int ATTACK = 2; 
    // private static final int ATTACK = 999; 
    private static final int DEFENSE = 6;
    private static final int MANA = 5;
    private static final int MAX_WEIGHT = 5;
    private static final int MONEY = 15;

    /** 
     * Constructor to create a MechaKnight from loaded data  
     * See {@link Character#Character(int, int, int, int, int, int, int, int, Weapon,  ArrayList, Coordinates)}
    */
    @JsonCreator
    public MechaKnight(
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

        this.skill = new Guard(this);
    }    

    /** 
     * Constructor to create an entirely new MechaKnight 
     * @param position The initial position of the MechaKnight
     * @param lvl The level of the MechaKnight
    */
    public MechaKnight(
        Coordinates position
    ) {
        super(
            HP,
            ATTACK,
            DEFENSE,
            MANA,
            MAX_WEIGHT,
            MONEY,
            new PlasmaGreatsword(),
            position
        );

        this.skill = new Guard(this);
    }

    public static String getDetailedInfo() {
        return "HP: " + HP + "\n" +
               "Attack: " + ATTACK + "\n" +
               "Defense: " + DEFENSE + "\n" +
               "Mana: " + MANA + "\n" +
               "Weapon: " + PlasmaGreatsword.describe() + "\n" +
               "Ability: " + Guard.describe() + "\n" +
               "BTC: " + MONEY;
    }

    @Override
    public String getASCII(){
        return Constants.MECHA_KNIGHT;
    }
}
