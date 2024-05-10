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
import com.tloj.game.collectables.items.Lockpick;
import com.tloj.game.collectables.items.HealthPotion;
import com.tloj.game.entities.Character;
import com.tloj.game.entities.FriendlyEntity;
import com.tloj.game.entities.npcs.Smith;
import com.tloj.game.game.Controller;
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

    private static Map<Integer, PurchasableItem> items = new LinkedHashMap<>();
    static {
        items.put(1, new HealthPotion());
        items.put(2, new GreatHealthPotion());
        items.put(3, new ManaPotion());
        items.put(4, new GreatManaPotion());
        items.put(5, new AttackElixir());
        items.put(6, new DefenseElixir());
        items.put(7, new Lockpick());
    }

    @JsonCreator
    public Merchant(@JsonProperty("position") Coordinates position) {
        super(position, NAME);
    }

    @Override
    public void interact(Character player) {
        super.interact(player);
        System.out.println("Merchant: Hello there! What do you want to buy today?");
        for (Map.Entry<Integer, PurchasableItem> entry : items.entrySet()) 
            System.out.println(entry.getKey() + ". " + entry.getValue());

        Controller.getInstance().setState(GameState.MERCHANT_SHOPPING);
    }

    @Override
    public void endInteraction() {
        super.endInteraction();
        Controller.getInstance().setState(GameState.MERCHANT_SHOPPING);
    }

    public void buy(int index) {
        if (index < 0 || index >= Merchant.items.size()) {
            System.out.println("Merchant: Please choose an item!");
            return;
        }

        PurchasableItem item = Merchant.items.get(index);

        if (!this.player.canAfford(item)) {
            System.out.println("Merchant: You don't have enough gold!");
            return;
        }

        item.purchase(this.player);
        items.remove(index);
    }
}
