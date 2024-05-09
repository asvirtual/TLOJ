package com.tloj.game.skills;

import com.tloj.game.game.PlayerAttack;
import com.tloj.game.entities.Character;

import com.fasterxml.jackson.annotation.JsonTypeInfo;


// Needed to serialize/deserialize subclasses of Character, by including the class name in the JSON
@JsonTypeInfo(
  use = JsonTypeInfo.Id.CLASS, 
  include = JsonTypeInfo.As.PROPERTY, 
  property = "@class")

/**
 * Abstract class for character skill used during combat<br>
 * As with the {@link BossAbility} class, this class applies an adaptation of the Strategy pattern to the character skills, allowing for easy addition of new skills.<br>
 * This class is meant to be extended by specific character skills, guaranteeing modularity.<br>
 * @see Character
 * @see CheatEngine
 * @see Daburu
 * @see Focus
 * @see Steal
 */

public abstract class CharacterSkill {
    protected Character character;

    protected CharacterSkill(Character character) {
        this.character = character;
    }

    public abstract void use(PlayerAttack attack);
}
