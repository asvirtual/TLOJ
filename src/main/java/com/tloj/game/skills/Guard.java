package com.tloj.game.skills;

import com.tloj.game.entities.characters.MechaKnight;
import com.tloj.game.entities.Character;
import com.tloj.game.game.PlayerAttack;


/**
 * Class that represents the Guard skill, which adds +3 defense during fight. <br>
 * It is paired with the {@link MechaKnight} class. <br>
 * @see Character
 * @see CharacterSkill
 * @see CheatEngine
 * @see Daburu
 * @see Steal
 */

public class Guard extends CharacterSkill{
    public Guard(Character character) {
        super(character);
    }

    @Override
    public void use(PlayerAttack attack) {
        Character attacker = attack.getAttacker();
        
        if (attacker.getMana() < 5) {
            System.out.println("Not enough mana to use Focus");
            return;
        }

        attack.setTargetDef(this.character.getCurrentFightDef() + 3);
        attacker.useMana(5);
    }
}

