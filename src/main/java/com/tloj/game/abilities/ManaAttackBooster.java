package com.tloj.game.abilities;

import com.tloj.game.collectables.Weapon;
import com.tloj.game.entities.Character;
import com.tloj.game.utilities.Dice;
import com.tloj.game.entities.Mob;


public class ManaAttackBooster extends WeaponEffect {
    public ManaAttackBooster(Weapon weapon, Character character, Mob mob) {
        super(weapon, character, mob);
    }

    @Override
    public void apply(Object... params) {
        if (this.character.getMana() < 3) return;

        try {
            Dice dice = this.weapon.getDice();
            this.character.useMana(3);
            this.mob.takeDamage(dice.roll());
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}
