package com.tloj.game.entities.characters;

import com.fasterxml.jackson.annotation.JsonCreator;
import com.fasterxml.jackson.annotation.JsonProperty;
import com.tloj.game.collectables.Weapon;
import com.tloj.game.collectables.weapons.NanoDirk;
import com.tloj.game.entities.Character;
import com.tloj.game.entities.Inventory;
import com.tloj.game.game.Coordinates;
import com.tloj.game.game.Floor;
import com.tloj.game.rooms.Room;
import com.tloj.game.skills.Steal;
import com.tloj.game.utilities.ConsoleHandler;
import com.tloj.game.utilities.Constants;


/**
 * Class to represent the DataThief class in the game<br>
 * It has the following fixed initial attributes:<br>
 * {@value #HP} health points<br>
 * {@value #MANA} mana points<br>
 * {@value #ATTACK} attack points<br>
 * {@value #DEFENSE} defense points<br>
 * {@value #MAX_WEIGHT} maximum weight capacity<br>
 * {@value #MONEY} money<br>
 * It has the {@link Steal} ability<br>
 * @see Character
 */

public class DataThief extends Character {
    private static final int HP = 20;
    private static final int ATTACK = 4;
    private static final int DEFENSE = 2;
    private static final int MANA = 10;
    private static final int MAX_WEIGHT = 5;
    private static final int MONEY = 0;

    /** 
     * Constructor to create a DataThief from loaded data  
     * See {@link Character#Character(int, int, int, int, int, int, int, int, Floor, Room, Weapon, Inventory, Coordinates)}
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
        @JsonProperty("currentFloor") Floor currentFloor,
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
            currentFloor,
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
        return "⸭ HP: " + ConsoleHandler.RED + HP + ConsoleHandler.RESET + "\n" +
               "⸭ Mana: " + ConsoleHandler.BLUE + MANA + ConsoleHandler.RESET + "\n" +
               "⸭ Attack: " + ConsoleHandler.PURPLE + ATTACK + ConsoleHandler.RESET  +"\n" +
               "⸭ Defense: " + ConsoleHandler.PURPLE + DEFENSE + ConsoleHandler.RESET + "\n" +
               "⸭ Weapon: " + ConsoleHandler.CYAN + NanoDirk.weaponInfo() + ConsoleHandler.RESET + "\n" +
               "⸭ Ability: " +ConsoleHandler.SILVER + Steal.describe() + ConsoleHandler.RESET + "\n" +
               "⸭ BTC: " + ConsoleHandler.YELLOW + MONEY + ConsoleHandler.RESET;
    }

    @Override
    public String getASCII() {
        return Constants.DATA_THIEF;
    }
}
