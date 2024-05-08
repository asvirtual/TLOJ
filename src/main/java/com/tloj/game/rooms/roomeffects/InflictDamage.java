package com.tloj.game.rooms.roomeffects;

import com.tloj.game.entities.Character;

public class InflictDamage implements RoomEffect{
    public static final int DAMAGE = 10;

    @Override
    public void applyEffect(Character character) {
        character.takeDamage(DAMAGE);
    }
}
