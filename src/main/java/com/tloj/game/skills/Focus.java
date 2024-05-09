package com.tloj.game.skills;

import com.tloj.game.entities.characters.BasePlayer;
import com.tloj.game.entities.Character;
import com.tloj.game.entities.characters.BasePlayer;
import com.tloj.game.game.PlayerAttack;


/**
 * Class that represents the Focus skill, which adds +3 damage on next attack. <br>
 * It is paired with the {@link BasePlayer} class. <br>
 * @see Character
 * @see CharacterSkill
 * @see CheatEngine
 * @see Daburu
 * @see Steal
 */

public class Focus extends CharacterSkill{
    public Focus(Character character) {
        super(character);
    }

    @Override
    public void use(PlayerAttack attack) {
        Character attacker = attack.getAttacker();
        
        if (attacker.getMana() < 5) {
            System.out.println("Not enough mana to use Focus");
            return;
        }

        attack.setBaseDamage(this.character.getCurrentFightAtk() + 3);
        attacker.useMana(5);
    }

    public static String describe() {
        return "Focus: Adds 3 dmg points on next attack";
    }
}

