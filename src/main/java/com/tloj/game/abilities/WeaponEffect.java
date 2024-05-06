package com.tloj.game.abilities;

import com.tloj.game.entities.Character;
import com.tloj.game.entities.Mob;
import com.tloj.game.collectables.Weapon;


public abstract class WeaponEffect {
    protected Weapon weapon;
    protected Character character;
    protected Mob mob;

    protected WeaponEffect(Weapon weapon, Character character, Mob mob) {
        this.weapon = weapon;
        this.character = character;
        this.mob = mob;
    }

    public abstract void apply(Object ...params);
}
