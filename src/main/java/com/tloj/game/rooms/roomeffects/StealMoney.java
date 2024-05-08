package com.tloj.game.rooms.roomeffects;

import com.tloj.game.entities.Character;

public class StealMoney implements RoomEffect{
    public static final int COST = 10;

    @Override
    public void applyEffect(Character character) {
        character.pay(COST);
    }
}
