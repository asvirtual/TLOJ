package com.tloj.game.skills;

import com.tloj.game.entities.Character;
import com.tloj.game.entities.characters.DataThief;
import com.tloj.game.game.PlayerAttack;
import com.tloj.game.collectables.Item;
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
    public Steal(Character character) {
        super(character);
    }

    @Override
    public void use(PlayerAttack attack) {
        Character attacker = attack.getAttacker();
        
        if (attacker.getMana() < 10) {
            System.out.println("Not enough mana to use Steal");
            return;
        }

        Dice dice = new Dice(10);
        if (dice.roll() < 4) return;
        
        attacker.addInventoryItem(Item.getRandomItem());
        attacker.useMana(10);
    }

    public static String describe() {
        return "Steal: Chance to steal a random item";
    }
}
