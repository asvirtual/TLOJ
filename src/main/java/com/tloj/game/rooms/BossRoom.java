package com.tloj.game.rooms;

import java.util.ArrayList;

import com.tloj.game.utilities.Coordinates;
import com.tloj.game.entities.Boss;
import com.tloj.game.entities.Mob;
import com.tloj.game.game.PlayerRoomVisitor;


public class BossRoom extends HostileRoom {
    public BossRoom(Coordinates coordinates, ArrayList<Mob> bosses) {
        super(coordinates, bosses);
    }

    public BossRoom(Coordinates coordinates, Boss boss) {
        super(coordinates, boss);
    }

    public Boss getBoss() {
        return (Boss) this.mobs.get(0);
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
    public String toString() {
        if(this.isVisited())
            return "\u00DF" + " ";
        else
            return "\u00A0" + " ";
    }

    @Override 
    public void clear() {
        super.clear();
        this.mobs.set(0, null);
    }
}
