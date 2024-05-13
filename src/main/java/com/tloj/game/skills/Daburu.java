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
    public void use(Attack attack) {
        if (this.character.getMana() < 10) {
            System.out.println("Not enough mana to use Daburu");
            return;
        }

        this.character.useMana(10);
        System.out.println(ConsoleColors.CYAN + "Daburu modo! Next attack will deal double damage" + ConsoleColors.RESET);
        
        super.use(attack);
    }

    @Override
    public void useOnAttack(PlayerAttack attack) {
        this.onUse = new Runnable() {
            @Override
            public void run() {
                attack.setBaseDamage(attack.getBaseDamage() * 2);
                attack.setWeaponRoll(attack.getWeaponRoll() * 2);
            }
        };
    }

    @Override
    public void useOnDefend(MobAttack attack) {}

    public static String describe() {
        return "Daburu: Doubles next attack damage";
    }
}
