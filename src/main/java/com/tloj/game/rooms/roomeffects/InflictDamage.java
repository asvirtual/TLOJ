package com.tloj.game.rooms.roomeffects;

import com.tloj.game.entities.Character;
/**
 * Class that implements a room effect that inflicts damage to the character<br>
 * Inflicts 5 damage to the player if it rolls < 5 on D6<br>
 * @see RoomEffect
 * @see StealMoney
 * @see TpEffect
 */

public class InflictDamage implements RoomEffect{
    public static final int DAMAGE = 10;

    @Override
    public void applyEffect(Character character) {
        character.takeDamage(DAMAGE);
    }
}
