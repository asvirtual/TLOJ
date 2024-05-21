package com.tloj.game.entities.characters;

import com.fasterxml.jackson.annotation.JsonCreator;
import com.fasterxml.jackson.annotation.JsonProperty;
import com.tloj.game.collectables.Weapon;
import com.tloj.game.collectables.weapons.PulseStaff;
import com.tloj.game.entities.Character;
import com.tloj.game.entities.Inventory;
import com.tloj.game.game.Floor;
import com.tloj.game.rooms.Room;
import com.tloj.game.skills.CheatEngine;
import com.tloj.game.utilities.ConsoleHandler;
import com.tloj.game.utilities.Constants;
import com.tloj.game.utilities.Coordinates;


public class Hacker extends Character {
    private static final int HP = 18;
    private static final int ATTACK = 3;
    private static final int DEFENSE = 3;
    private static final int MANA = 20;
    private static final int MAX_WEIGHT = 5;
    private static final int MONEY = 10;

    /** 
     * Constructor to create a Cheater from loaded data 
     * See {@link Character#Character(int, int, int, int, int, int, int, int, Weapon, Inventory, Coordinates)}
    */
    @JsonCreator
    public Hacker(
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

        this.skill = new CheatEngine(this);
    }    

    /** 
     * Constructor to create an entirely new Cheater 
     * @param position The initial position of the Cheater
     * @param lvl The level of the Cheater
    */
    public Hacker(
       Coordinates position
    ) {
        super(
            HP,
            ATTACK,
            DEFENSE,
            MANA,
            MAX_WEIGHT,
            MONEY,
            new PulseStaff(),
            position
        );

        this.skill = new CheatEngine(this);
    }

    public static String getDetailedInfo() {
       return  "⸭ HP: " + ConsoleHandler.RED + HP + ConsoleHandler.RESET + "\n" +
               "⸭ Mana: " + ConsoleHandler.BLUE + MANA + ConsoleHandler.RESET + "\n" +
               "⸭ Attack: " + ConsoleHandler.PURPLE + ATTACK + ConsoleHandler.RESET  +"\n" +
               "⸭ Defense: " + ConsoleHandler.PURPLE + DEFENSE + ConsoleHandler.RESET + "\n" +
               "⸭ Weapon: " + ConsoleHandler.CYAN + PulseStaff.weaponInfo() + ConsoleHandler.RESET + "\n" +
               "⸭ Ability: " +ConsoleHandler.SILVER + CheatEngine.describe() + ConsoleHandler.RESET + "\n" +
               "⸭ BTC: " + ConsoleHandler.YELLOW + MONEY + ConsoleHandler.RESET;
    }

    @Override
    public String getASCII() {
        return Constants.HACKER;
    }
}
 
