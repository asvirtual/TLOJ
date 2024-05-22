package com.tloj.game.entities.characters;

import com.fasterxml.jackson.annotation.JsonCreator;
import com.fasterxml.jackson.annotation.JsonProperty;
import com.tloj.game.collectables.Weapon;
import com.tloj.game.collectables.weapons.PlasmaGreatsword;
import com.tloj.game.entities.Character;
import com.tloj.game.entities.Inventory;
import com.tloj.game.game.Coordinates;
import com.tloj.game.game.Floor;
import com.tloj.game.rooms.Room;
import com.tloj.game.skills.Guard;
import com.tloj.game.utilities.ConsoleHandler;
import com.tloj.game.utilities.Constants;


public class MechaKnight extends Character {
    private static final int HP = 30;
    private static final int ATTACK = 1; 
    private static final int DEFENSE = 5;
    private static final int MANA = 5;
    private static final int MAX_WEIGHT = 5;
    private static final int MONEY = 15;

    /** 
     * Constructor to create a MechaKnight from loaded data  
     * See {@link Character#Character(int, int, int, int, int, int, int, int, Weapon, Inventory, Coordinates)}
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
        return "⸭ HP: " + ConsoleHandler.RED + HP + ConsoleHandler.RESET + "\n" +
               "⸭ Mana: " + ConsoleHandler.BLUE + MANA + ConsoleHandler.RESET + "\n" +
               "⸭ Attack: " + ConsoleHandler.PURPLE + ATTACK + ConsoleHandler.RESET  +"\n" +
               "⸭ Defense: " + ConsoleHandler.PURPLE + DEFENSE + ConsoleHandler.RESET + "\n" +
               "⸭ Weapon: " + ConsoleHandler.CYAN + PlasmaGreatsword.weaponInfo() + ConsoleHandler.RESET + "\n" +
               "⸭ Ability: " +ConsoleHandler.SILVER + Guard.describe() + ConsoleHandler.RESET + "\n" +
               "⸭ BTC: " + ConsoleHandler.YELLOW + MONEY + ConsoleHandler.RESET;
    }

    @Override
    public String getASCII() {
        return Constants.MECHA_KNIGHT;
    }
}
