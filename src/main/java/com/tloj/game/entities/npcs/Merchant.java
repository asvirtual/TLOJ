package com.tloj.game.entities.npcs;

import java.util.Map;

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
 * Merchants can sell items to the player and have a fixed inventory that replenish after every floor <br>
 * Merchants can be interacted with by the player to access their shop <br>
 * @see Smith
 */

public class Merchant extends FriendlyEntity {
    public static final String NAME = "MERCHANT";
    
    /**
     * Default constructor to allow Jackson to deserialize JSON.
     */
    public Merchant() {}

    private static Map<Integer, PurchasableItem> items = Map.ofEntries(
        Map.entry(1, new HealthPotion()),
        Map.entry(2, new GreatHealthPotion()),
        Map.entry(3, new ManaPotion()),
        Map.entry(4, new GreatManaPotion()),
        Map.entry(5, new AttackElixir()),
        Map.entry(6, new DefenseElixir()),
        Map.entry(7, new Lockpick())
    );

    public Merchant(Coordinates position) {
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
    }
}
