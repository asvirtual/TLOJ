package com.tloj.game.skills;

import com.tloj.game.entities.Character;
import com.tloj.game.game.PlayerAttack;
import com.tloj.game.collectables.Weapon;

/**
 * Class that represents the Cheat Engine skill, which allows the character to force the max weapon roll. <br>
 * It is paired with the {@link Hacker} class. <br>
 */

public class CheatEngine extends CharacterSkill{
    public CheatEngine(Character character) {
        super(character);
    }

    @Override
    public void use(PlayerAttack attack) {
        Character attacker = attack.getAttacker();
        Weapon weapon = attacker.getWeapon();

        if (attacker.getMana() < 5) {
            System.out.println("Not enough mana to use Cheat Engine");
            return;
        }
        
        attack.setWeaponRoll(weapon.getDice().getFaces());
        attacker.useMana(5);
    }
}
