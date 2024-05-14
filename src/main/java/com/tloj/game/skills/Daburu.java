package com.tloj.game.skills;

import com.fasterxml.jackson.annotation.JsonCreator;
import com.fasterxml.jackson.annotation.JsonProperty;
import com.tloj.game.collectables.Weapon;
import com.tloj.game.entities.Character;
import com.tloj.game.entities.characters.NeoSamurai;
import com.tloj.game.game.Attack;
import com.tloj.game.game.MobAttack;
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

public class Daburu extends CharacterSkill {
    private static final int MANA_COST = 10;

    /**
     * Constructs a Daburu object with the given character.
     *
     * @param character The character that uses the skill.
     */
    @JsonCreator
    public Daburu(@JsonProperty("character") Character character) {
        super(character, MANA_COST);
        this.activationMessage = ConsoleColors.PURPLE + "Daburu modo! Next attack will deal double damage!" + ConsoleColors.RESET;
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
        
        playerAttack.setBaseDamage(playerAttack.getBaseDamage() * 2);
        playerAttack.setWeaponRoll(playerAttack.getWeaponRoll() * 2);

        super.execute(attack);
    }

    public static String describe() {
        return "Daburu: Doubles next attack damage";
    }
}
