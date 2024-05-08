package com.tloj.game.skills;

import com.tloj.game.game.PlayerAttack;
import com.tloj.game.entities.Character;

/**
 * Abstract class for character skills <br>
 * 
 */

public abstract class CharacterSkill {
    protected Character character;

    protected CharacterSkill(Character character) {
        this.character = character;
    }

    public abstract void use(PlayerAttack attack);
}
