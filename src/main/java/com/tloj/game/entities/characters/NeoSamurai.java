package com.tloj.game.entities.characters;

import com.fasterxml.jackson.annotation.JsonCreator;
import com.fasterxml.jackson.annotation.JsonProperty;
import com.tloj.game.collectables.Weapon;
import com.tloj.game.collectables.weapons.CyberKatana;
import com.tloj.game.entities.Character;
import com.tloj.game.entities.Inventory;
import com.tloj.game.game.Coordinates;
import com.tloj.game.game.Floor;
import com.tloj.game.rooms.Room;
import com.tloj.game.utilities.ConsoleHandler;
import com.tloj.game.utilities.Constants;
import com.tloj.game.skills.Daburu;


/**
 * Class to represent the NeoSamurai class in the game<br>
 * It has the following fixed initial attributes:<br>
 * {@value #HP} health points<br>
 * {@value #MANA} mana points<br>
 * {@value #ATTACK} attack points<br>
 * {@value #DEFENSE} defense points<br>
 * {@value #MAX_WEIGHT} maximum weight capacity<br>
 * {@value #MONEY} money<br>
 * It has the {@link Daburu} ability<br>
 * @see Character
 */

public class NeoSamurai extends Character {
    private static final int HP = 20;
    private static final int ATTACK = 5;
    private static final int DEFENSE = 1;
    private static final int MANA = 10;
    private static final int MAX_WEIGHT = 5;
    private static final int MONEY = 15;

    /** 
     * Constructor to create a NeoSamurai from loaded data  
     * See {@link Character#Character(int, int, int, int, int, int, int, int, Floor, Room, Weapon, Inventory, Coordinates)}
    */
    @JsonCreator
    public NeoSamurai(
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

        this.skill = new Daburu(this);
    }    

    /** 
     * Constructor to create an entirely new NeoSamurai 
     * @param position The initial position of the NeoSamurai
     * @param lvl The level of the NeoSamurai
    */
    public NeoSamurai(
        Coordinates position
    ) {
        super(
            HP,
            ATTACK,
            DEFENSE,
            MANA,
            MAX_WEIGHT,
            MONEY,
            new CyberKatana(),
            position
        );

        this.skill = new Daburu(this);
    }

    public static String getDetailedInfo() {
        return "⸭ HP: " + ConsoleHandler.RED + HP + ConsoleHandler.RESET + "\n" +
               "⸭ Mana: " + ConsoleHandler.BLUE + MANA + ConsoleHandler.RESET + "\n" +
               "⸭ Attack: " + ConsoleHandler.PURPLE + ATTACK + ConsoleHandler.RESET  +"\n" +
               "⸭ Defense: " + ConsoleHandler.PURPLE + DEFENSE + ConsoleHandler.RESET + "\n" +
               "⸭ Weapon: " + ConsoleHandler.CYAN + CyberKatana.weaponInfo() + ConsoleHandler.RESET + "\n" +
               "⸭ Ability: " +ConsoleHandler.SILVER + Daburu.describe() + ConsoleHandler.RESET + "\n" +
               "⸭ BTC: " + ConsoleHandler.YELLOW + MONEY + ConsoleHandler.RESET;
    }

    @Override
    public String getASCII(){
        return Constants.NEO_SAMURAI;
    }
}

