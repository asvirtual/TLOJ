package com.tloj.game.entities;

import com.tloj.game.collectables.Item;
import com.tloj.game.utilities.Coordinates;


public abstract class FriendlyEntity extends Entity {
    protected Character player;

    protected FriendlyEntity(Coordinates position) {
        super(position);
    }

    public void interact(Character player) {
        this.player = player;
    }

    public void giveItem(Item item) {
        this.player.removeInventoryItem(item);
    }
}
