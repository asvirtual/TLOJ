package com.tloj.game.skills;

import com.fasterxml.jackson.annotation.JsonCreator;
import com.fasterxml.jackson.annotation.JsonProperty;
import com.tloj.game.entities.Character;
import com.tloj.game.entities.characters.NeoSamurai;
import com.tloj.game.game.PlayerAttack;
import com.tloj.game.utilities.ConsoleColors;


/**
 * Class that represents the Daburu (double from japanese) skill, which forces next attack to inflict double damage. <br>
 * It is paired with the {@link NeoSamurai} class. <br>
 * @see Character
 * @see CharacterSkill
 * @see CheatEngine
 * @see Focus
 * @see Steal
 */

public class Daburu extends CharacterSkill{
    /**
     * Constructs a Daburu object with the given character.
     *
     * @param character The character that uses the skill.
     */
    @JsonCreator
    public Daburu(@JsonProperty("character") Character character) {
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
            System.out.println("Not enough mana to use Daburu");
            return;
        }

        this.onUse = new Runnable() {
            @Override
            public void run() {
                attack.setBaseDamage(attack.getBaseDamage() * 2);
                attack.setWeaponRoll(attack.getWeaponRoll() * 2);
            }
        };

        attacker.useMana(10);
        System.out.println(ConsoleColors.CYAN + "Daburu modo! Next attack will deal double damage" + ConsoleColors.RESET);
    }

    public static String describe() {
        return "Daburu: Doubles next attack damage";
    }
}
