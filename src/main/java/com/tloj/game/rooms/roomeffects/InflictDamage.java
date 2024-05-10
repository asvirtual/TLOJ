package com.tloj.game.rooms.roomeffects;

import com.tloj.game.entities.Character;
/**
 * Class that implements a room effect that inflicts damage to the character<br>
 * Inflicts {@value #DAMAGE} damage to the player if it rolls {@literal <} 5 on D6<br>
 * @see RoomEffect
 * @see StealMoney
 * @see TpEffect
 */

public class InflictDamage implements RoomEffect{
    public static final int DAMAGE = 10;

    /*
     * Default constructor for Jackson JSON deserialization
     */
    public InflictDamage() {}

    @Override
    public void applyEffect(Character character) {
        System.out.println("You've been hit by a virus and lost " + DAMAGE + " HP!");
        character.takeDamage(DAMAGE);
    }
}
