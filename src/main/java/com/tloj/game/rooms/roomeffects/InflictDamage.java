package com.tloj.game.rooms.roomeffects;

import com.tloj.game.entities.Character;
import com.tloj.game.game.Controller;
import com.tloj.game.utilities.ConsoleHandler;
import com.tloj.game.utilities.Constants;
import com.tloj.game.utilities.Dice;

/**
 * Class that implements a room effect that inflicts damage to the character<br>
 * Inflicts {@value #DAMAGE} damage to the player if it rolls {@literal <} 5 on D6<br>
 * @see RoomEffect
 * @see StealMoney
 * @see TpEffect
 */

public class InflictDamage extends RoomEffect {
    public static final int DAMAGE = 10;

    /*
     * Default constructor for Jackson JSON deserialization
     */
    public InflictDamage() {}

    /**
     * Applies the effect of inflicting damage to a character.
     *
     * @param character the character to apply the effect to
     * @return true if the effect was successfully applied, false otherwise
     */
    @Override
    public boolean applyEffect(Character character) {
        Dice dice = new Dice(6);
        int roll = dice.roll();
        if (roll < 3) return false;

        ConsoleHandler.println(ConsoleHandler.PURPLE + "You've entered a Trap Room!" + ConsoleHandler.RESET + "\n");
        Controller.getInstance().printMapAndArt(this.getASCII());
        ConsoleHandler.println("\n" + ConsoleHandler.RED + "You've been hit by a virus and lost " + DAMAGE + " HP!" + ConsoleHandler.RESET);
        
        // Inflict damage to the character
        character.takeDamage(DAMAGE);

        return true;
    }

    @Override
    public String getASCII() {
        return Constants.INFILCT_DAMAGE_EFFECT;
    }
}
