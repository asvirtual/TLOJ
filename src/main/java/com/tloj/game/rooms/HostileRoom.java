package com.tloj.game.rooms;

import com.tloj.game.entities.Mob;
import com.tloj.game.utilities.Coordinates;
import com.tloj.game.game.PlayerRoomVisitor;


public class HostileRoom extends Room {
    protected Mob mob;

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

    @Override
    public String toString() {
        if(this.isVisited())
            return "\u25A0" + " ";
        else
            return "\u00A0" + " ";
    }

    public void clear() {
        super.clear();
        this.mob = null;
    }
}
