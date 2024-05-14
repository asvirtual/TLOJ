package com.tloj.game.skills;

import com.tloj.game.entities.characters.BasePlayer;
import com.fasterxml.jackson.annotation.JsonCreator;
import com.fasterxml.jackson.annotation.JsonProperty;
import com.tloj.game.entities.Character;
import com.tloj.game.game.Attack;
import com.tloj.game.game.MobAttack;
import com.tloj.game.game.PlayerAttack;
import com.tloj.game.utilities.ConsoleHandler;


/**
 * Class that represents the Focus skill, which adds +3 damage on next attack. <br>
 * It is paired with the {@link BasePlayer} class. <br>
 * @see Character
 * @see CharacterSkill
 * @see CheatEngine
 * @see Daburu
 * @see Steal
 */

public class Focus extends CharacterSkill {
    private static final int MANA_COST = 5;

    /**
     * Constructs a Focus object with the given character.
     *
     * @param character The character that uses the skill.
     */
    @JsonCreator
    public Focus(@JsonProperty("character") Character character) {
        super(character, MANA_COST);
        this.activationMessage = ConsoleHandler.PURPLE + "Focus mode on! Next attack will deal 3 more damage" + ConsoleHandler.RESET;
    }
    

    /**
     * Method for using ability.
     *
     * @param attack The attack being performed.
     */
    @Override 
    public void execute(Attack attack) {
        // This skill only works on player attacks
        if (!this.activated || !(attack instanceof PlayerAttack)) return;

        PlayerAttack playerAttack = (PlayerAttack) attack;
        playerAttack.setBaseDamage(playerAttack.getBaseDamage() + 3);
        
        super.execute(attack);
    }

    public static String describe() {
        return "Focus: Adds 3 dmg points on next attack";
    }
}

