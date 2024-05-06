package com.tloj.game.collectables;


public abstract class PurchasableItem extends Item implements ConsumableItem {
    protected int price;

    protected PurchasableItem(int price, int weight) {
        super(weight);
        this.price = price;
    }
}
