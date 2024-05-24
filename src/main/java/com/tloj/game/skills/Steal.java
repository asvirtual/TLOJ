package com.tloj.game.skills;

import com.tloj.game.entities.Character;
import com.tloj.game.entities.characters.DataThief;
import com.tloj.game.game.Controller;
import com.tloj.game.game.Dice;
import com.tloj.game.rooms.HostileRoom;
import com.fasterxml.jackson.annotation.JsonCreator;
import com.fasterxml.jackson.annotation.JsonProperty;
import com.tloj.game.collectables.Item;
import com.tloj.game.utilities.ConsoleHandler;


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
            ConsoleHandler.println(ConsoleHandler.RED + "Not enough mana to use Steal!" + ConsoleHandler.RESET);
            return;
        }

        this.character.useMana(this.manaCost);
        this.activated = true;

        HostileRoom room = (HostileRoom) this.character.getCurrentRoom();

        Dice dice = new Dice(10);
        if (dice.roll() < 4) {
            this.activationMessage = ConsoleHandler.RED + "Couldn't insert the USB drive! Steal failed" + ConsoleHandler.RESET;
            
            ConsoleHandler.clearConsole();
            Controller.printSideBySideText(
                room.getMob().getASCII(), 
                room.getMob().getPrettifiedStatus() + "\n\n\n" + this.character.getPrettifiedStatus() + "\n" + 
                ConsoleHandler.PURPLE + this.activationMessage + ConsoleHandler.RESET + "\n\n"
            );

            return;
        }
        
        Item item = Item.getRandomItem();
        if (this.character.addInventoryItem(item)) this.activationMessage = ConsoleHandler.CYAN + "Data acquired! You stole a " + item + ConsoleHandler.RESET;
        else this.activationMessage = ConsoleHandler.RED + "Steal failed! " + item + " fell out of your pocket because your inventory is full" + ConsoleHandler.RESET;

        ConsoleHandler.clearConsole();
        Controller.printSideBySideText(
            room.getMob().getASCII(), 
            room.getMob().getPrettifiedStatus() + "\n\n\n" + this.character.getPrettifiedStatus() + "\n" + 
            ConsoleHandler.PURPLE + this.activationMessage + ConsoleHandler.RESET + "\n\n"
        );

        System.out.println();
    }

    public static String describe() {
        return "Steal: Chance to steal a random item";
    }
}
