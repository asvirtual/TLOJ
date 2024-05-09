package com.tloj.game.skills;

import com.tloj.game.entities.Character;
import com.tloj.game.entities.characters.Hacker;
import com.tloj.game.game.PlayerAttack;
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
        System.out.println("Hacking going on! MAX ROLL INCOMING!");
    }

    public static String describe() {
        return "Cheat Engine: Forces the max weapon roll";
    }
}
