package com.tloj.game.collectables;


/**
 * An abstract class representing an item that can be purchased in the game<br>
 * This class is meant to be extended by specific purchasable items, guaranteeing modularity<br>
 */
public abstract class PurchasableItem extends Item {
    protected int price;

    protected PurchasableItem(int price, int weight) {
        super(weight);
        this.price = price;
    }
}
