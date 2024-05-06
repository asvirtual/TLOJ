package com.tloj.game.abilities;

import com.tloj.game.collectables.Weapon;
import com.tloj.game.entities.Character;
import com.tloj.game.entities.Mob;


public class HealthAbsorber extends WeaponEffect {
    public HealthAbsorber(Weapon weapon, Character character, Mob mob) {
        super(weapon, character, mob);
    }

    @Override
    public void apply(Object ...params) {
        try {
            int damage = (int) params[0];
            this.character.heal(damage / 2);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}
