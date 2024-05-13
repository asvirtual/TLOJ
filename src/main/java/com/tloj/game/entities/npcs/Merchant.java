package com.tloj.game.entities.npcs;

import java.util.LinkedHashMap;
import java.util.Map;

import com.fasterxml.jackson.annotation.JsonCreator;
import com.fasterxml.jackson.annotation.JsonProperty;
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
import com.tloj.game.entities.npcs.Smith;
import com.tloj.game.game.Controller;
import com.tloj.game.utilities.ConsoleColors;
import com.tloj.game.utilities.Constants;
import com.tloj.game.utilities.Coordinates;
import com.tloj.game.utilities.GameState;


/**
 * Represents a merchant NPC in the game. <br>
 * Merchants can sell {@link PurchasableItem} to the player and have a fixed inventory that replenish after every floor <br>
 * Merchants can be interacted with by the player to access their shop <br>
 * @see Smith
 */
public class Merchant extends FriendlyEntity {
    public static final String NAME = "MERCHANT";
    private Map<Integer, PurchasableItem> items = new LinkedHashMap<>();

    @JsonCreator
    public Merchant(@JsonProperty("position") Coordinates position) {
        super(position, NAME);
        this.items.put(1, new HealthPotion());
        this.items.put(2, new ManaPotion());
        this.items.put(3, new GreatHealthPotion());
        this.items.put(4, new GreatManaPotion());
        this.items.put(5, new AttackElixir());
        this.items.put(6, new DefenseElixir());
        this.items.put(7, new Emp());
        this.items.put(8, new WeaponShard());
    }
    /**
     * @return a string representation of the merchant's items
     */
    public String getItems() {
        String items = "";
        for (Map.Entry<Integer, PurchasableItem> entry : this.items.entrySet()) 
            items += entry.getKey() + ". " + entry.getValue() + " - " + entry.getValue().getPrice() + " BTC" + " - " + entry.getValue().getWeight() + " MB\n";

        return items;
    }

    @Override
    public void interact(Character player) {
        super.interact(player);

        Controller.printSideBySideText(
            this.getASCII(),
            ConsoleColors.YELLOW + "Merchant: Hello there! What do you want to buy today?" + ConsoleColors.RESET + "\n" +
            "You currently have " + ConsoleColors.YELLOW + this.player.getMoney() + " BTC" +
            ConsoleColors.RESET + " and " + ConsoleColors.YELLOW + this.player.getFreeWeight() + " MB" + 
            ConsoleColors.RESET + " of free space\n" +
            this.getItems() 
        );

        Controller.getInstance().setState(GameState.MERCHANT_SHOPPING);
    }

    @Override
    public void endInteraction() {
        super.endInteraction();
        Controller.getInstance().setState(GameState.MERCHANT_SHOPPING);
    }

    public void buy(int index) {
        PurchasableItem item = this.items.get(index);
        
        if (item == null) {
            System.out.println("Merchant: I don't have that!");
            return;
        }

        if (!this.player.canAfford(item)) {
            System.out.println("Merchant: You don't have enough BTC!");
            return;
        }

        item.purchase(this.player);
        items.remove(index);

        System.out.println("Merchant: It's always a pleasure doing business with you!");

        Controller.clearConsole(2000);

        Controller.printSideBySideText(
            this.getASCII(),
            ConsoleColors.RESET + "You currently have " + ConsoleColors.YELLOW + this.player.getMoney() + " BTC" +
            ConsoleColors.RESET + " and " + ConsoleColors.YELLOW + this.player.getFreeWeight() + " MB" + 
            ConsoleColors.RESET + " of free space\n" +
            this.getItems() 
        );
    }

    @Override
    public String getASCII() {
        return Constants.MERCHANT;
    }
}
