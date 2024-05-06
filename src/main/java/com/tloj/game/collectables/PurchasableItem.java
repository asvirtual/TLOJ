package com.tloj.game.collectables;


public abstract class PurchasableItem extends Item {
    protected int price;

    protected PurchasableItem(int price, int weight, Object effect) {
        super(weight, effect);
        this.price = price;
    }
}
