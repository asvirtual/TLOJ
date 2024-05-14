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
    protected boolean activated = false;
    protected int manaCost;
    protected String activationMessage;

    /**
     * Constructs a CharacterSkill object with the given character.
     *
     * @param character The character that uses the skill.
     */
    protected CharacterSkill(Character character) {
        this.character = character;
        this.manaCost = 0;
    }

    protected CharacterSkill(Character character, int manaCost) {
        this.character = character;
        this.manaCost = manaCost;
    }

    public void activate() {
        if (this.character.getMana() < this.manaCost) {
            System.out.println(ConsoleColors.RED + "Not enough mana to use " + this.getClass().getSimpleName().split("(?=[A-Z])") + ConsoleColors.RESET);
            return;
        }

        this.character.useMana(this.manaCost);
        this.activated = true;

        Controller.clearConsole();
        HostileRoom room = (HostileRoom) this.character.getCurrentRoom();

        Controller.printSideBySideText(
            room.getMob().getASCII(), 
            room.getMob().getPrettifiedStatus() + "\n\n\n" + this.character.getPrettifiedStatus() + "\n" + 
            this.activationMessage + "\n\n"
        );

        System.out.println();
    }

    public void execute(Attack attack) {
        this.activated = false;
    };
}
