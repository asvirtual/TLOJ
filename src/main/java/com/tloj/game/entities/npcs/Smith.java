package com.tloj.game.entities.npcs;

import com.tloj.game.collectables.Item;
import com.tloj.game.collectables.items.WeaponShard;
import com.tloj.game.entities.Character;
import com.tloj.game.entities.FriendlyEntity;
import com.tloj.game.game.Controller;
import com.tloj.game.utilities.Coordinates;
import com.tloj.game.utilities.GameState;


public class Smith extends FriendlyEntity {
    public Smith(Coordinates position) {
        super(position);
    }

    @Override
    public void interact(Character player) {
        super.interact(player);
        System.out.println("Smith: Hello there! I can upgrade your weapon with a Weapon Shard.");
        Controller.getInstance().setState(GameState.SMITH_FORGING);
    }

    @Override
    public void giveItem(Item item) {
        super.giveItem(item);
        if (!(item instanceof WeaponShard)) {
            System.out.println("Smith: I need a Weapon Shard to forge!");
            return;
        }

        this.player.getWeapon().upgrade(1);
    }
}
