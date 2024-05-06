package com.tloj.game.collectables;


public abstract class Item {
    protected int price;
    protected int weight;
    protected Object effect;

    protected Item(int price, int weight, Object effect) {
        this.price = price;
        this.weight = weight;
        this.effect = effect;
    }

    public int getWeight() {
        return weight;
    }
}
