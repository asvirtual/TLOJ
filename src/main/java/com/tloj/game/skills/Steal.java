package com.tloj.game.skills;

import com.tloj.game.entities.Character;
import com.tloj.game.entities.characters.DataThief;
import com.tloj.game.game.Attack;
import com.tloj.game.game.Controller;
import com.tloj.game.game.MobAttack;
import com.tloj.game.game.PlayerAttack;
import com.tloj.game.rooms.HostileRoom;
import com.fasterxml.jackson.annotation.JsonCreator;
import com.fasterxml.jackson.annotation.JsonProperty;
import com.tloj.game.collectables.Item;
import com.tloj.game.collectables.Weapon;
import com.tloj.game.utilities.ConsoleColors;
import com.tloj.game.utilities.Dice;


/**
 * Class that represents the Steal skill, which allows the character to steal a random item and add it to the inventory with a 40% probability. <br>
 * It is paired with the {@link DataThief} class. <br>
 * @see Character
 * @see CharacterSkill
 * @see CheatEngine
 * @see Daburu
 * @see Focus
 */

public class Steal extends CharacterSkill {
    private static final int MANA_COST = 10;

    /**
     * Constructs a Steal object with the given character.
     *
     * @param character The character that uses the skill.
     */
    @JsonCreator
    public Steal(@JsonProperty("character") Character character) {
        super(character, MANA_COST);
    }

    @Override 
    public void activate() {
        if (this.character.getMana() < this.manaCost) {
            System.out.println(ConsoleColors.RED + "Not enough mana to use Steal!" + ConsoleColors.RESET);
            return;
        }

        this.character.useMana(this.manaCost);
        this.activated = true;

        HostileRoom room = (HostileRoom) this.character.getCurrentRoom();

        Dice dice = new Dice(10);
        this.character.useMana(10);
        if (dice.roll() < 4) {
            this.activationMessage = ConsoleColors.RED + "Couldn't insert the USB drive! Steal failed" + ConsoleColors.RESET;
            return;
        }
        
        Item item = Item.getRandomItem();
        if (this.character.addInventoryItem(item)) this.activationMessage = ConsoleColors.CYAN + "Data acquired! You stole a " + item + ConsoleColors.RESET;
        else this.activationMessage = ConsoleColors.RED + "Steal failed! " + item + " fell out of your pocket because your inventory is full" + ConsoleColors.RESET;

        Controller.clearConsole();
        Controller.printSideBySideText(
            room.getMob().getASCII(), 
            room.getMob().getPrettifiedStatus() + "\n\n\n" + this.character.getPrettifiedStatus() + "\n" + 
            ConsoleColors.PURPLE + this.activationMessage + ConsoleColors.RESET + "\n\n"
        );

        System.out.println();
    }

    /**
     * Method for using ability.
     *
     * @param attack The attack being performed.
     */
    @Override
    public void execute(Attack attack) {
        super.execute(attack);
    }

    public static String describe() {
        return "Steal: Chance to steal a random item";
    }
}
