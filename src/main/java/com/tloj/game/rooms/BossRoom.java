package com.tloj.game.rooms;

import com.tloj.game.entities.Boss;
import com.tloj.game.utilities.Coordinates;
import com.tloj.game.game.PlayerRoomVisitor;


public class BossRoom extends Room {
    Boss boss;
    
    public BossRoom(Coordinates coordinates, Boss boss) {
        super(coordinates);
        this.boss = boss;
    }

    @Override
    public RoomType getType() {
        return RoomType.BOSS_ROOM;
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
