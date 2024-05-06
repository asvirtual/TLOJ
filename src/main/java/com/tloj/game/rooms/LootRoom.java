package com.tloj.game.rooms;

import com.tloj.game.utilities.Coordinates;
import com.tloj.game.collectables.Item;
import com.tloj.game.game.PlayerRoomVisitor;


public class LootRoom extends Room {
    private Item item;
    private boolean isLocked;

    public LootRoom(Coordinates coordinates, Item item, boolean isLocked) {
        super(coordinates);
        this.item = item;
        this.isLocked = isLocked;
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
