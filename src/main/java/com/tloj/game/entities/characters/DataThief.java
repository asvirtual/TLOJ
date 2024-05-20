package com.tloj.game.entities.characters;

import java.util.ArrayList;

import com.fasterxml.jackson.annotation.JsonCreator;
import com.fasterxml.jackson.annotation.JsonProperty;
import com.tloj.game.collectables.Item;
import com.tloj.game.collectables.Weapon;
import com.tloj.game.collectables.weapons.NanoDirk;
import com.tloj.game.effects.DiceReroller;
import com.tloj.game.entities.Character;
import com.tloj.game.entities.Inventory;
import com.tloj.game.game.Level;
import com.tloj.game.rooms.Room;
import com.tloj.game.skills.Steal;
import com.tloj.game.utilities.Constants;
import com.tloj.game.utilities.Coordinates;


public class DataThief extends Character {
    private static final int HP = 20;
    private static final int ATTACK = 4;
    private static final int DEFENSE = 2;
    private static final int MANA = 10;
    private static final int MAX_WEIGHT = 5;
    private static final int MONEY = 0;

    /** 
     * Constructor to create a DataThief from loaded data  
     * See {@link Character#Character(int, int, int, int, int, int, int, int, Weapon,  ArrayList, Coordinates)}
    */
    @JsonCreator
    public DataThief(
        @JsonProperty("hp") int hp,
        @JsonProperty("atk") int atk,
        @JsonProperty("def") int def,
        @JsonProperty("mana") int mana,
        @JsonProperty("xp") int xp,
        @JsonProperty("lvl") int lvl,
        @JsonProperty("maxWeight") int maxWeight,
        @JsonProperty("money") int money,
        @JsonProperty("currentLevel") Level currentLevel,
        @JsonProperty("currentRoom") Room currentRoom,
        @JsonProperty("weapon") Weapon weapon,
        @JsonProperty("inventory") Inventory inventory,
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
            currentLevel,
            currentRoom,
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
    public DataThief(
        Coordinates position
    ) {    
        super(
            HP,
            ATTACK,
            DEFENSE,
            MANA,
            MAX_WEIGHT,
            MONEY,
            new NanoDirk(),
            position
        );

        this.skill = new Steal(this);
    }

    public static String getDetailedInfo() {
        return "HP: " + HP + "\n" +
               "Attack: " + ATTACK + "\n" +
               "Defense: " + DEFENSE + "\n" +
               "Mana: " + MANA + "\n" +
               "Weapon: " + NanoDirk.weaponInfo() + " - " + DiceReroller.describe() + "\n" +
               "Ability: " + Steal.describe() + "\n" +
               "BTC: " + MONEY;
    }

    @Override
    public String getASCII() {
        return Constants.DATA_THIEF;
    }
}
