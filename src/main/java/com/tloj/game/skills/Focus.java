package com.tloj.game.skills;

import com.tloj.game.entities.Character;
import com.tloj.game.game.PlayerAttack;


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

        attack.setTotalDamage(attack.getTotalDamage() + 2);
        attacker.useMana(5);
    }
}

