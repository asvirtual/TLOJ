package com.tloj.game.entities.npcs;

import java.util.List;

import com.fasterxml.jackson.annotation.JsonCreator;
import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonProperty;
import com.tloj.game.collectables.Item;
import com.tloj.game.collectables.PurchasableItem;
import com.tloj.game.collectables.items.DefenseElixir;
import com.tloj.game.collectables.items.AttackElixir;
import com.tloj.game.collectables.items.GreatHealthPotion;
import com.tloj.game.collectables.items.GreatManaPotion;
import com.tloj.game.collectables.items.ManaPotion;
import com.tloj.game.collectables.items.WeaponShard;
import com.tloj.game.collectables.items.Emp;
import com.tloj.game.collectables.items.HealthPotion;
import com.tloj.game.entities.Character;
import com.tloj.game.entities.FriendlyEntity;
import com.tloj.game.entities.Inventory;
import com.tloj.game.entities.ItemsHolderEntity;
import com.tloj.game.game.Controller;
import com.tloj.game.game.Coordinates;
import com.tloj.game.game.GameState;
import com.tloj.game.utilities.ConsoleHandler;
import com.tloj.game.utilities.Constants;


/**
 * Represents a merchant NPC in the game. <br>
 * Merchants can sell {@link PurchasableItem} to the player and have a fixed inventory that replenish after every floor <br>
 * Merchants can be interacted with by the player to access their shop <br>
 * @see Smith
 */
public class Merchant extends FriendlyEntity implements ItemsHolderEntity {
    public static final String NAME = "MERCHANT";
    @JsonIgnore
    private Inventory inventory;

    @JsonCreator
    public Merchant(@JsonProperty("position") Coordinates position) {
        super(position, NAME);
        this.inventory = new Inventory(
            this, 
            List.of(
                new HealthPotion(),
                new ManaPotion(),
                new GreatHealthPotion(),
                new GreatManaPotion(),
                new AttackElixir(),
                new DefenseElixir(),
                new Emp(),
                new WeaponShard()
            )
        );
    }

    /**
     * @return a string representation of the merchant's items
     */
    @JsonIgnore
    public String getItems() {
        String items = "";
        for (int i = 0; i < this.inventory.getSize(); i++) {
            PurchasableItem item = (PurchasableItem) this.inventory.getByIndex(i);
            items += (i + 1) + ". " + item + " - " + item.getPrice() + " BTC" + " - " + item.getWeight() + " MB\n";
        }
        
        return items;
    }

    @Override
    public void interact(Character player) {
        super.interact(player);

        Controller.printSideBySideText(
            this.getASCII(),
            ConsoleHandler.YELLOW + "Merchant: Hello there! What do you want to buy?" + ConsoleHandler.RESET + "\n" +
            this.getCurrentStatus(),
            4
        );

        Controller.getInstance().setState(GameState.MERCHANT_SHOPPING);
    }

    @Override
    public void endInteraction() {
        super.endInteraction();
        Controller.getInstance().setState(GameState.MERCHANT_SHOPPING);
    }

    public void buy(int index) {
        ConsoleHandler.clearConsole();
        PurchasableItem item = (PurchasableItem) this.inventory.getByIndex(index);
        
        if (item == null) {
            Controller.printSideBySideText(
                this.getASCII(),
                ConsoleHandler.RED + "Merchant: I don't have that!" + ConsoleHandler.RESET + "\n" +
                this.getCurrentStatus(),
                4
            );

            return;
        }

        if (!this.player.canAfford(item)) {
            Controller.printSideBySideText(
                this.getASCII(),
                ConsoleHandler.RED + "Merchant: You don't have enough BTC!" + ConsoleHandler.RESET + "\n" +
                this.getCurrentStatus(),
                4
            );

            return;
        }

        if (!this.player.canCarry(item)) {
            Controller.printSideBySideText(
                this.getASCII(),
                ConsoleHandler.RED + "Merchant: You don't have enough space in your inventory!" + ConsoleHandler.RESET + "\n" +
                this.getCurrentStatus(),
                4
            );
            
            return;
        }

        if (!Controller.awaitConfirmation()) return;

        item.purchase(this.player);
        this.inventory.removeByIndex(index);

        Controller.printSideBySideText(
            this.getASCII(),
            ConsoleHandler.YELLOW + "Merchant: It's always a pleasure doing business with you!" + ConsoleHandler.RESET + "\n " + 
            ConsoleHandler.YELLOW + "Do you need anything else?" + ConsoleHandler.RESET + "\n" +
            this.getCurrentStatus(),
            4
        );
    }

    @JsonIgnore
    private String getCurrentStatus() {
        return 
            "You currently have " + ConsoleHandler.YELLOW + this.player.getMoney() + " BTC" +
            ConsoleHandler.RESET + " and " + ConsoleHandler.YELLOW + this.player.getFreeWeight() + " MB" + 
            ConsoleHandler.RESET + " of free space.\n" +
            ConsoleHandler.RESET + this.getItems() + "\n\n\n" +
            ConsoleHandler.RESET + this.player.getInventoryString();
    }

    @Override
    @JsonIgnore
    public String getASCII() {
        return Constants.MERCHANT;
    }

    @Override
    public boolean canCarry(Item item) {
        return false;
    }

    @Override
    @JsonIgnore
    public double getMaxWeight() {
        return 0.0;
    }
    
    @Override
    @JsonIgnore
    public double getFreeWeight() {
        return 0.0;
    }

    @Override
    @JsonIgnore
    public double getCarriedWeight() {
        return Math.floor(this.inventory.getTotalWeight() * 10) / 10;
    }

    @Override
    @JsonIgnore
    public int getItemCount(Item item) {
        return this.inventory.getCount(item);
    }   

    @Override
    @JsonIgnore
    public int getInventorySize() {
        return this.inventory.getSize();
    }

    @Override
    @JsonIgnore
    public Item getInventoryItem(Item item) {
        return this.inventory.get(item);
    }

    @Override
    @JsonIgnore
    public Item getInventoryItem(int index) {
        return this.inventory.getByIndex(index);
    }

    @Override
    @JsonIgnore
    public String getInventoryString() {
        return this.inventory.toString();
    }

    @Override
    @JsonIgnore
    public Item getItemByName(String itemName) {
        return this.inventory.getByName(itemName);
    }

    @Override
    @JsonIgnore
    public Item removeInventoryItem(Item item) {
        return this.inventory.remove(item);
    }

    @Override
    @JsonIgnore
    public Item removeInventoryItem(int index) {
        Item item = this.inventory.removeByIndex(index);
        return item;
    }

    public Item removeRandomInventoryItem() {
        if (this.inventory.getSize() == 0) return null;

        int index = (int) (Math.random() * this.inventory.getSize());
        Item removed = this.inventory.removeByIndex(index);
        return removed;
    }

    @Override
    public boolean addInventoryItem(Item item) {
        return false;
    }

    @Override
    public boolean hasItem(Item item) {
        return this.inventory.get(item) != null;
    }
}
