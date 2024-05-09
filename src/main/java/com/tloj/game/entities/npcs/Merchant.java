package com.tloj.game.entities.npcs;

import java.util.ArrayList;

import com.tloj.game.collectables.Item;
import com.tloj.game.collectables.items.WeaponShard;
import com.tloj.game.entities.Character;
import com.tloj.game.entities.FriendlyEntity;
import com.tloj.game.game.Controller;
import com.tloj.game.utilities.Coordinates;
import com.tloj.game.utilities.GameState;


public class Merchant extends FriendlyEntity {
    private ArrayList<Item> items;

    public Merchant(Coordinates position) {
        super(position);
    }

    @Override
    public void interact(Character player) {
        super.interact(player);
        System.out.println("Merchant: Hello there! What do you want to buy today?");
        for (int i = 0 ; i < this.items.size(); i++) 
            System.out.print((i + 1) + ". " + items.get(i));
            
        Controller.getInstance().setState(GameState.MERCHANT_SHOPPING);
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
