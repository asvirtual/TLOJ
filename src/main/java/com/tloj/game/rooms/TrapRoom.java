package com.tloj.game.rooms;

import com.tloj.game.utilities.Coordinates;
import com.tloj.game.game.PlayerRoomVisitor;
import com.tloj.game.rooms.roomeffects.RoomEffect;


public class TrapRoom extends Room {
    private RoomEffect effect;

    public TrapRoom(Coordinates coordinates, RoomEffect effect) {
        super(coordinates);
        this.effect = effect;
    }

    @Override
    public RoomType getType() {
        return RoomType.TRAP_ROOM;
    }

    @Override
    public void accept(PlayerRoomVisitor visitor) {
        visitor.visit(this);
    }

    @Override
    public void exit() {
        // TODO Auto-generated method stub
        throw new UnsupportedOperationException("Unimplemented method 'exit'");
    }
}
