package com.tloj.game.rooms.roomeffects;

import com.tloj.game.entities.Character;
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

    @Override
    public boolean applyEffect(Character character) {
        Dice dice = new Dice(6);
        int roll = dice.roll();
        if (roll < 3) return false;

        System.out.println("You've been hit by a virus and lost " + DAMAGE + " HP!");
        character.takeDamage(DAMAGE);

        return true;
    }
}
