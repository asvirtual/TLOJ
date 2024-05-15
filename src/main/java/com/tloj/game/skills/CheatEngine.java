package com.tloj.game.skills;

import com.tloj.game.entities.Character;
import com.tloj.game.entities.characters.Hacker;
import com.tloj.game.game.Attack;
import com.tloj.game.game.PlayerAttack;
import com.tloj.game.utilities.ConsoleHandler;
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

public class CheatEngine extends CharacterSkill {
    private static final int MANA_COST = 5;
    
    /**
     * Constructs a CheatEngine object with the given character.
     *
     * @param character The character that uses the skill.
     */
    @JsonCreator
    public CheatEngine(@JsonProperty("character") Character character) {
        super(character, MANA_COST);
        this.activationMessage = ConsoleHandler.PURPLE + "Hacking going on! MAX ROLL INCOMING!" + ConsoleHandler.RESET;
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
        Weapon weapon = this.character.getWeapon();
        playerAttack.setWeaponRoll(weapon.getDiceMax());

        super.execute(attack);
    }

    public static String describe() {
        return "Cheat Engine: Forces the max weapon roll";
    }
}
