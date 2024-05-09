package com.tloj.game.skills;

import com.tloj.game.entities.Character;
import com.tloj.game.entities.characters.NeoSamurai;
import com.tloj.game.game.PlayerAttack;


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
    public Daburu(Character character) {
        super(character);
    }

    @Override
    public void use(PlayerAttack attack) {
        Character attacker = attack.getAttacker();
        
        if (attacker.getMana() < 10) {
            System.out.println("Not enough mana to use Daburu");
            return;
        }

        attack.setTotalDamage(attack.getTotalDamage() * 2);
        attacker.useMana(10);
    }
}
