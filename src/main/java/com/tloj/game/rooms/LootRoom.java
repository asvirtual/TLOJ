package com.tloj.game.rooms;

import com.tloj.game.entities.Character;
import com.tloj.game.utilities.Coordinates;
import com.tloj.game.collectables.Item;


public class LootRoom extends Room {
    private Item item;

    public LootRoom(Coordinates coordinates, Item item, Character character, boolean isLocked) {
        super(coordinates, character, isLocked);
        this.item = item;
    }
}
