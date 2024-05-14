package com.tloj.game.entities.npcs;

import com.fasterxml.jackson.annotation.JsonCreator;
import com.fasterxml.jackson.annotation.JsonProperty;
import com.tloj.game.collectables.Item;
import com.tloj.game.collectables.Weapon;
import com.tloj.game.collectables.items.WeaponShard;
import com.tloj.game.entities.Character;
import com.tloj.game.entities.FriendlyEntity;
import com.tloj.game.entities.ItemReceiverEntity;
import com.tloj.game.entities.npcs.Merchant;
import com.tloj.game.game.Controller;
import com.tloj.game.utilities.ConsoleHandler;
import com.tloj.game.utilities.Constants;
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

        Controller.printSideBySideText(
            this.getASCII(),
            ConsoleHandler.YELLOW + "Smith: Hello there! I can upgrade your weapon with a Weapon Shard." + ConsoleHandler.RESET + "\n" +
            "You currently have " + ConsoleHandler.YELLOW + this.player.getItemCount(new WeaponShard()) + " Weapon Shards." + ConsoleHandler.RESET,
            7
        );
        
        Controller.getInstance().setState(GameState.SMITH_FORGING);
    }

    @Override
    public void endInteraction() {
        Controller.getInstance().setState(GameState.HEALING_ROOM);
        super.endInteraction();
    }
    
    @Override
    public void giveItem(Item item) {
        ConsoleHandler.clearConsole();
        
        if (!(item instanceof WeaponShard)) {
            Controller.printSideBySideText(
                this.getASCII(),
                ConsoleHandler.RED + "Smith: I need a Weapon Shard to forge!" + ConsoleHandler.RESET + "\n",
                7
            );
            return;
        }

        if (this.player.getWeapon().getLevel() == Weapon.MAX_LEVEL) {
            Controller.printSideBySideText(
                this.getASCII(),
                ConsoleHandler.RED + "Smith: Your weapon is already at its maximum level!" + ConsoleHandler.RESET + "\n",
                7
            );
            return;
        }

        this.player.removeInventoryItem(item);
        this.player.getWeapon().upgrade(1);

        Controller.printSideBySideText(
            this.getASCII(),
            ConsoleHandler.YELLOW + "Smith: Here you go! Your weapon has been upgraded!" + ConsoleHandler.RESET + "\n" +
            this.player.getWeapon().getASCII() + "\n" +
            this.player.getWeapon() + "\n" +
            this.getCurrentStatus(),
            7
        );

        
        int shardsCount = this.player.getItemCount(new WeaponShard());
        if (shardsCount == 0) {
            Controller.awaitEnter();
            ConsoleHandler.clearConsole();
            Controller.printSideBySideText(
                this.getASCII(),
                ConsoleHandler.RED + "Smith: You've ran out of Weapon Shards, Goodbye!" + ConsoleHandler.RESET + "\n" +
                this.getCurrentStatus(),
                7
            );

            this.endInteraction();
            return;
        }
    }

    private String getCurrentStatus() {
        return "You currently have " + ConsoleHandler.YELLOW + this.player.getItemCount(new WeaponShard()) + " Weapon Shards." + ConsoleHandler.RESET;
    }

    @Override
    public String getASCII() {
        return Constants.SMITH;
    }
}
