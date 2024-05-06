package com.tloj.game.abilities;

import com.tloj.game.entities.Character;
import com.tloj.game.entities.Mob;
import com.tloj.game.collectables.Weapon;


public class DiceThrower extends WeaponEffect {
    public DiceThrower(Weapon weapon, Character character, Mob mob) {
        super(weapon, character, mob);
    }

    @Override
    public void apply(Object ...params) {
        try {
            this.mob.takeDamage(this.weapon.diceRoll());
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}
