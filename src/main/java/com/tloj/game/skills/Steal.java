package com.tloj.game.skills;

import com.tloj.game.entities.Character;
import com.tloj.game.entities.characters.DataThief;
import com.tloj.game.game.PlayerAttack;
import com.fasterxml.jackson.annotation.JsonCreator;
import com.fasterxml.jackson.annotation.JsonProperty;
import com.tloj.game.collectables.Item;
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
    /**
     * Constructs a Steal object with the given character.
     *
     * @param character The character that uses the skill.
     */
    @JsonCreator
    public Steal(@JsonProperty("character") Character character) {
        super(character);
    }

    /**
     * Method for using ability.
     *
     * @param attack The attack being performed.
     */
    @Override
    public void use(PlayerAttack attack) {
        // Get the attacker
        Character attacker = attack.getAttacker();
        
        if (attacker.getMana() < 10) {
            System.out.println("Not enough mana to use Steal");
            return;
        }

        Dice dice = new Dice(10);
        attacker.useMana(10);
        if (dice.roll() < 4) {
            System.out.println("Couldn't insert the USB drive! Steal failed");
            return;
        }
        
        Item item = Item.getRandomItem();
        if (attacker.addInventoryItem(item)) System.out.println(ConsoleColors.CYAN + "Data acquired! You stole a " + item + ConsoleColors.RESET);
        else System.out.println("Steal failed! " + item + " fell out of your pocket because your inventory is full");
    }

    public static String describe() {
        return "Steal: Chance to steal a random item";
    }
}
