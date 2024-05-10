package com.tloj.game.entities.npcs;

import com.fasterxml.jackson.annotation.JsonCreator;
import com.fasterxml.jackson.annotation.JsonProperty;
import com.tloj.game.collectables.Item;
import com.tloj.game.collectables.items.WeaponShard;
import com.tloj.game.entities.Character;
import com.tloj.game.entities.FriendlyEntity;
import com.tloj.game.entities.ItemReceiverEntity;
import com.tloj.game.entities.npcs.Merchant;
import com.tloj.game.game.Controller;
import com.tloj.game.utilities.Coordinates;
import com.tloj.game.utilities.GameState;


/**
 * Represents a Smith NPC in the game. <br>
 * The Smith can upgrade the player's weapon with a {@link WeaponShard}. <br>
 * @see Merchant
 */
public class Smith extends FriendlyEntity implements ItemReceiverEntity {
    public static final String NAME = "SMITH";

    @JsonCreator
    public Smith(@JsonProperty("position") Coordinates position) {
        super(position, NAME);
    }

    @Override
    public void interact(Character player) {
        super.interact(player);
        System.out.println("Smith: Hello there! I can upgrade your weapon with a Weapon Shard.");
        System.out.println("You currently have " + this.player.getItemCount(new WeaponShard()) + " Weapon Shards.");
        Controller.getInstance().setState(GameState.SMITH_FORGING);
    }

    @Override
    public void endInteraction() {
        System.out.println("Smith: Goodbye!");
        Controller.getInstance().setState(GameState.HEALING_ROOM);
        super.endInteraction();
    }
    
    @Override
    public void giveItem(Item item) {
        if (!(item instanceof WeaponShard)) {
            System.out.println("Smith: I need a Weapon Shard to forge!");
            return;
        }

        this.player.removeInventoryItem(item);
        this.player.getWeapon().upgrade(1);
        
        int shardsCount = this.player.getItemCount(new WeaponShard());
        if (shardsCount == 0) {
            System.out.println("You've ran out of Weapon Shards!");
            this.endInteraction();
        }
    }
}
