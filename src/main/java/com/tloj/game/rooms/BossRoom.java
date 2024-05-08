package com.tloj.game.rooms;

import com.tloj.game.utilities.Coordinates;
import com.tloj.game.entities.bosses.Boss;
import com.tloj.game.game.PlayerRoomVisitor;


public class BossRoom extends HostileRoom {
    public BossRoom(Coordinates coordinates, Boss boss) {
        super(coordinates, boss);
    }

    public Boss getBoss() {
        return (Boss) this.mob;
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

    @Override 
    public void clear() {
        super.clear();
        this.mob = null;
    }
}
