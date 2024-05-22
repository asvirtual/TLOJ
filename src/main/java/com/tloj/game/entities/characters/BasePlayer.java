package com.tloj.game.entities.characters;

import com.fasterxml.jackson.annotation.JsonCreator;
import com.fasterxml.jackson.annotation.JsonProperty;
import com.tloj.game.collectables.Weapon;
import com.tloj.game.collectables.weapons.LaserBlade;
import com.tloj.game.entities.Character;
import com.tloj.game.entities.Inventory;
import com.tloj.game.game.Coordinates;
import com.tloj.game.game.Floor;
import com.tloj.game.rooms.Room;
import com.tloj.game.skills.Focus;
import com.tloj.game.utilities.ConsoleHandler;
import com.tloj.game.utilities.Constants;


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
     * See {@link Character#Character(int, int, int, int, int, int, int, int, Weapon, Inventory, Coordinates)}
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
        return "⸭ HP: " + ConsoleHandler.RED + HP + ConsoleHandler.RESET + "\n" +
               "⸭ Mana: " + ConsoleHandler.BLUE + MANA + ConsoleHandler.RESET + "\n" +
               "⸭ Attack: " + ConsoleHandler.PURPLE + ATTACK + ConsoleHandler.RESET  +"\n" +
               "⸭ Defense: " + ConsoleHandler.PURPLE + DEFENSE + ConsoleHandler.RESET + "\n" +
               "⸭ Weapon: " + ConsoleHandler.CYAN + LaserBlade.weaponInfo() + ConsoleHandler.RESET + "\n" +
               "⸭ Ability: " +ConsoleHandler.SILVER + Focus.describe() + ConsoleHandler.RESET + "\n" +
               "⸭ BTC: " + ConsoleHandler.YELLOW + MONEY + ConsoleHandler.RESET;
    }

    @Override
    public String getASCII() {
        return Constants.BASE_PLAYER;
    }
}
