package com.tloj.game.rooms;

import com.tloj.game.utilities.Coordinates;
import com.tloj.game.collectables.Item;
import com.tloj.game.game.PlayerRoomVisitor;


public class LootRoom extends Room {
    private Item item;
    private boolean locked;

    public LootRoom(Coordinates coordinates, Item item, boolean locked) {
        super(coordinates);
        this.item = item;
        this.locked = locked;
    }

    public boolean isLocked() {
        return this.locked;
    }

    @Override
    public RoomType getType() {
        return RoomType.LOOT_ROOM;
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
