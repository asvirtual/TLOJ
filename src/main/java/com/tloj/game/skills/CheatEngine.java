package com.tloj.game.skills;

import com.tloj.game.entities.Character;
import com.tloj.game.entities.characters.Hacker;
import com.tloj.game.game.PlayerAttack;
import com.tloj.game.utilities.ConsoleColors;
import com.fasterxml.jackson.annotation.JsonCreator;
import com.fasterxml.jackson.annotation.JsonProperty;
import com.tloj.game.collectables.Weapon;

/**
 * Class that represents the Cheat Engine skill, which allows the character to force the max weapon roll. <br>
 * It is paired with the {@link Hacker} class. <br>
 * @see Character
 * @see CharacterSkill
 * @see Daburu
 * @see Focus
 * @see Steal
 */

public class CheatEngine extends CharacterSkill{
    /**
     * Constructs a CheatEngine object with the given character.
     *
     * @param character The character that uses the skill.
     */
    @JsonCreator
    public CheatEngine(@JsonProperty("character") Character character) {
        super(character);
    }

    /**
     * Method for using ability.
     *
     * @param attack The attack being performed.
     */
    @Override
    public void use(PlayerAttack attack) {
        // Get the attacker and the weapon
        Character attacker = attack.getAttacker();
        Weapon weapon = attacker.getWeapon();

        if (attacker.getMana() < 5) {
            System.out.println("Not enough mana to use Cheat Engine");
            return;
        }
        
        attack.setWeaponRoll(weapon.getDiceFaces());
        attacker.useMana(5);
        System.out.println(ConsoleColors.CYAN + "Hacking going on! MAX ROLL INCOMING!" + ConsoleColors.RESET);
    }

    public static String describe() {
        return "Cheat Engine: Forces the max weapon roll";
    }
}
