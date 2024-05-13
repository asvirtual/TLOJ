package com.tloj.game.skills;

import com.tloj.game.game.Attack;
import com.tloj.game.game.MobAttack;
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
    // The character that uses the skill
    protected Character character;
    protected Runnable onUse;

    /**
     * Constructs a CharacterSkill object with the given character.
     *
     * @param character The character that uses the skill.
     */
    protected CharacterSkill(Character character) {
        this.character = character;
    }

    public void executeOnUse() {
        if (onUse != null) onUse.run();
    }

    /**
     * Applies the skill's effect depending on the type of attack.
     */
    public void use(Attack attack) {
        if (attack instanceof MobAttack) this.useOnDefend((MobAttack) attack);
        if (attack instanceof PlayerAttack) this.useOnAttack((PlayerAttack) attack);
    }

    public abstract void useOnDefend(MobAttack attack);
    public abstract void useOnAttack(PlayerAttack attack);
}
