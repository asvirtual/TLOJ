package com.tloj.game.rooms;

import com.tloj.game.utilities.Coordinates;
import com.tloj.game.collectables.Item;
import com.tloj.game.game.PlayerRoomVisitor;


public class LootRoom extends Room {
    private Item item;
    private boolean locked;

    public LootRoom(Coordinates coordinates, boolean isLocked) {
        super(coordinates);
        this.item = Item.getRandomItem();
        this.isLocked = isLocked;

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

    @Override
    public void clear() {
        super.clear();
        this.item = null;
    }

    public Item getItem() {
        return this.item;
    }
}
