package com.tloj.game.skills;

import com.tloj.game.entities.characters.MechaKnight;
import com.fasterxml.jackson.annotation.JsonCreator;
import com.fasterxml.jackson.annotation.JsonProperty;
import com.tloj.game.entities.Character;
import com.tloj.game.game.Attack;
import com.tloj.game.game.MobAttack;
import com.tloj.game.game.PlayerAttack;
import com.tloj.game.utilities.ConsoleColors;


/**
 * Class that represents the Guard skill, which adds +3 defense during fight. <br>
 * It is paired with the {@link MechaKnight} class. <br>
 * @see Character
 * @see CharacterSkill
 * @see CheatEngine
 * @see Daburu
 * @see Steal
 */

public class Guard extends CharacterSkill {
    /**
     * Constructs a Guard object with the given character.
     *
     * @param character The character that uses the skill.
     */
    @JsonCreator
    public Guard(@JsonProperty("character") Character character) {
        super(character);
    }

    /**
     * Method for using ability.
     *
     * @param attack The attack being performed.
     */
    @Override
    public void use(Attack attack) {
        if (this.character.getMana() < 8) {
            System.out.println("Not enough mana to use Focus");
            return;
        }

        this.character.useMana(8);
        System.out.println(ConsoleColors.CYAN + "Guard activated! Defense increased by 3 points!" + ConsoleColors.RESET);

        super.use(attack);
    }

    public static String describe() {
        return "Guard: Adds 3 defense points during fight";
    }

    @Override
    public void useOnDefend(MobAttack attack) {
        attack.setDiceRoll(0);
        attack.setTotalDamage(0);
    }

    /**
     * This ability is only used on defending attack
     */
    @Override
    public void useOnAttack(PlayerAttack attack) {}
}

