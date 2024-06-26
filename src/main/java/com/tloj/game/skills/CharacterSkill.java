package com.tloj.game.skills;

import com.tloj.game.game.Attack;
import com.tloj.game.game.Controller;
import com.tloj.game.rooms.HostileRoom;
import com.tloj.game.utilities.ConsoleHandler;
import com.tloj.game.entities.Character;
import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonManagedReference;
import com.fasterxml.jackson.annotation.JsonProperty;
import com.fasterxml.jackson.annotation.JsonTypeInfo;
import com.tloj.game.abilities.BossAbility;


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

 // Needed to serialize/deserialize subclasses of Character, by including the class name in the JSON
@JsonTypeInfo(
    use = JsonTypeInfo.Id.CLASS, 
    include = JsonTypeInfo.As.PROPERTY, 
    property = "@class")

public abstract class CharacterSkill {
    // The character that uses the skill
    @JsonProperty("character")
    @JsonManagedReference
    protected Character character;
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
            ConsoleHandler.println(ConsoleHandler.RED + "Not enough mana to use " + this.getName() + ConsoleHandler.RESET);
            return;
        }

        this.character.useMana(this.manaCost);
        this.activated = true;

        ConsoleHandler.clearConsole();
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
    }

    public void setActivated(boolean activated) {
        this.activated = activated;
    }

    public boolean isActivated() {
        return this.activated;
    }

    @JsonIgnore
    public String getName()  {
        return String.join(" ", this.getClass().getSimpleName().split("(?=[A-Z])"));
    }
}
