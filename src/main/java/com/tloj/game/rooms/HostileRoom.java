package com.tloj.game.rooms;

import com.tloj.game.entities.Character;
import com.tloj.game.entities.Mob;
import com.tloj.game.utilities.Coordinates;
import com.tloj.game.game.PlayerRoomVisitor;


public class HostileRoom extends Room {
    private Mob mob;

    public HostileRoom(Coordinates coordinates, Mob mob) {
        super(coordinates);
        this.mob = mob;
    }

    @Override
    public RoomType getType() {
        return RoomType.HOSTILE_ROOM;
    }

    public Mob getMob() {
        return this.mob;
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
