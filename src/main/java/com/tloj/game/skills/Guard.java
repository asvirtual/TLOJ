package com.tloj.game.skills;

import com.tloj.game.entities.characters.MechaKnight;
import com.fasterxml.jackson.annotation.JsonCreator;
import com.fasterxml.jackson.annotation.JsonProperty;
import com.tloj.game.entities.Character;
import com.tloj.game.game.Attack;
import com.tloj.game.game.MobAttack;
import com.tloj.game.utilities.ConsoleHandler;


/**
 * Class that represents the Guard skill, which halfs the opponent next attack. <br>
 * It is paired with the {@link MechaKnight} class. <br>
 * @see Character
 * @see CharacterSkill
 * @see CheatEngine
 * @see Daburu
 * @see Steal
 */

public class Guard extends CharacterSkill {
    private static final int MANA_COST = 5;

    /**
     * Constructs a Guard object with the given character.
     *
     * @param character The character that uses the skill.
     */
    @JsonCreator
    public Guard(@JsonProperty("character") Character character) {
        super(character, MANA_COST);
        this.activationMessage = ConsoleHandler.PURPLE + "Guard activated! Jordan will take half damage from the next opponent's attack!" + ConsoleHandler.RESET;
    }
    
    /**
     * Method for using ability.
     *
     * @param attack The attack being performed.
     */
    @Override 
    public void execute(Attack attack) {
        // This skill only works on mob attacks
        if (!this.activated || !(attack instanceof MobAttack)) return;

        MobAttack mobAttack = (MobAttack) attack;
        mobAttack.setTotalAttack(mobAttack.getTotalAttack() / 2);
        
        super.execute(attack);
    }

    public static String describe() {
        return "Guard: Takes half damage from the next opponent's attack. Costs 5 mana.";
    }
}

