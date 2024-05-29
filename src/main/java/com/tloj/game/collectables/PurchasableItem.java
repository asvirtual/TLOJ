package com.tloj.game.collectables;

import com.tloj.game.entities.Character;


/**
 * An abstract class representing an item that can be purchased in the game via {@link Merchant}<br>
 * This class is meant to be extended by specific purchasable items, guaranteeing modularity<br>
 */
public abstract class PurchasableItem extends Item {
    protected int price;

    protected PurchasableItem(int price, double weight, int id) {
        super(weight, id);
        this.price = price;
    }

    protected PurchasableItem(int price, double weight, int id, double dropChance) {
        super(weight, id, dropChance);
        this.price = price;
    }

    /**
     * Purchases the item and adds it to the buyer's inventory
     * @param buyer The character that is purchasing the item
     */
    public void purchase(Character buyer) {
        buyer.addInventoryItem(this);
        buyer.pay(this.price);
    };

    public int getPrice() {
        return this.price;
    }

    /**
     * @return A short description of the item to be displayed when printing the player's inventory
     */
    public abstract String shortInfo();

    @Override
    public String toString() {
        return super.toString();
    }
}
