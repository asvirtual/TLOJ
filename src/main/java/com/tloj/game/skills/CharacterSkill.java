package com.tloj.game.skills;

import com.tloj.game.game.Attack;
import com.tloj.game.game.Controller;
import com.tloj.game.game.MobAttack;
import com.tloj.game.game.PlayerAttack;
import com.tloj.game.rooms.HostileRoom;
import com.tloj.game.utilities.ConsoleColors;
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
        this.onUse = null;
    }

    /**
     * Applies the skill's effect depending on the type of attack.
     */
    public void use(Attack attack) {
        if (attack instanceof MobAttack) this.useOnDefend((MobAttack) attack);
        if (attack instanceof PlayerAttack) this.useOnAttack((PlayerAttack) attack);

        Controller.clearConsole();
        HostileRoom room = (HostileRoom) this.character.getCurrentRoom();

        System.out.println(ConsoleColors.PURPLE + "You've encountered " + room.getMob() + ConsoleColors.RESET + "\n");
        Controller.printSideBySideText(
            room.getMob().getASCII(), 
            room.getMob().getPrettifiedStatus() + "\n\n\n" + this.character.getPrettifiedStatus() + "\n" + 
            ConsoleColors.PURPLE + String.join(" ", this.getClass().getSimpleName().split("(?=[A-Z])")) + " activated! " + ConsoleColors.RESET + "\n\n"
        );

        System.out.println();
    }

    public abstract void useOnDefend(MobAttack attack);
    public abstract void useOnAttack(PlayerAttack attack);
}
