package com.tloj.game.skills;

import com.tloj.game.entities.Character;
import com.tloj.game.game.PlayerAttack;
import com.tloj.game.collectables.Weapon;


public class CheatEngine extends CharacterSkill{
    public CheatEngine(Character character) {
        super(character);
    }

    @Override
    public void useSkill(PlayerAttack attack) {
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
