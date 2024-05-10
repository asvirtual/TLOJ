package com.tloj.game.rooms.roomeffects;

import com.tloj.game.entities.Character;
import com.tloj.game.utilities.Dice;

/**
 * Class that implements a room effect that steals money from the character<br>
 * Steals {@value #COST} money from the player if it doesn't roll 6 on D6<br>
 * @see RoomEffect
 * @see InflictDamage
 * @see TpEffect
 */

public class StealMoney extends RoomEffect {
    public static final int COST = 10;

    /*
     * Default constructor for Jackson JSON deserialization
     */
    public StealMoney() {}

    @Override
    public boolean applyEffect(Character character) {
        Dice dice = new Dice(6);
        int roll = dice.roll();
        if (roll < 3) return false;

        System.out.println("You have been scammed by a Crypto Guru and you lost " + COST + " BTC!");
        character.pay(COST);

        return true;
    }
}
