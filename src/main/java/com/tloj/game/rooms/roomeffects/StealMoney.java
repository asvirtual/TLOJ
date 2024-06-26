package com.tloj.game.rooms.roomeffects;

import com.tloj.game.entities.Character;
import com.tloj.game.game.Controller;
import com.tloj.game.game.Dice;
import com.tloj.game.utilities.ConsoleHandler;
import com.tloj.game.utilities.Constants;

/**
 * Class that implements a room effect that steals money from the character<br>
 * Steals {@value #COST} money from the player if it doesn't roll 6 on D6<br>
 * @see RoomEffect
 * @see InflictDamage
 * @see Teleport
 */

public class StealMoney extends RoomEffect {
    public static final int COST = 10;
    
    /**
     * Applies the effect of stealing money from the character.
     *
     * @param character the character from whom the money will be stolen
     * @return true if the effect is successfully applied, false otherwise
     */
    @Override
    public boolean applyEffect(Character character) {
        Dice dice = new Dice(6);
        int roll = dice.roll();
        if (roll < 3) return false;

        ConsoleHandler.println(ConsoleHandler.PURPLE + "You've entered a Trap Room!" + ConsoleHandler.RESET + "\n");
        Controller.getInstance().printMapAndArt(this.getASCII());
        ConsoleHandler.println("\n" + ConsoleHandler.RED +"You have been scammed by a Crypto Guru and you lost " + COST + " BTC!" + ConsoleHandler.RESET);
        character.pay(COST);

        return true;
    }

    @Override
    public String getASCII() {
        return Constants.STEAL_MONEY_EFFECT;
    }
}
