package com.tloj.game.rooms;

import com.tloj.game.utilities.Coordinates;
import com.tloj.game.collectables.Item;
import com.tloj.game.game.PlayerRoomVisitor;


public class LootRoom extends Room {
    private Item item;

    public LootRoom(Coordinates coordinates) {
        super(coordinates);
        this.item = Item.getRandomItem();
        this.locked = false;
    }

    public LootRoom(Coordinates coordinates, boolean locked) {
        super(coordinates);
        this.item = Item.getRandomItem();
        this.locked = locked;
    }

    public LootRoom(Coordinates coordinates, boolean locked, Item item) {
        super(coordinates);
        this.item = item;
        this.locked = locked;
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
