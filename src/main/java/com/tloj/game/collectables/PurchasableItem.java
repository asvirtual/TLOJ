package com.tloj.game.collectables;


import com.tloj.game.entities.Character;


/**
 * An abstract class representing an item that can be purchased in the game<br>
 * This class is meant to be extended by specific purchasable items, guaranteeing modularity<br>
 */
public abstract class PurchasableItem extends Item {
    protected int price;

    protected PurchasableItem(int price, double weight, int dropMoney, int ID) {
        super(weight, dropMoney, ID);
        this.price = price;
    }

    public void purchase(Character buyer) {
        buyer.addInventoryItem(this);
        buyer.pay(this.price);
    };

    public int getPrice() {
        return this.price;
    }
}
