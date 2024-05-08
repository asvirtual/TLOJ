package com.tloj.game.skills;

import com.tloj.game.game.PlayerAttack;
import com.tloj.game.entities.Character;

/**
 * Abstract class for character skill used during combat<br>
 * As with the {@link BossAbility} class, this class applies an adaptation of the Strategy pattern to the character skills, allowing for easy addition of new skills.<br>
 * This class is meant to be extended by specific character skills, guaranteeing modularity.<br>
 * @see Character
 */

public abstract class CharacterSkill {
    protected Character character;

    protected CharacterSkill(Character character) {
        this.character = character;
    }

    public abstract void use(PlayerAttack attack);
}
