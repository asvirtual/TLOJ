package com.tloj.game.collectables;


public abstract class Item {
    protected int weight;
    protected Object effect;

    protected Item(int weight, Object effect) {
        this.weight = weight;
        this.effect = effect;
    }

    public int getWeight() {
        return weight;
    }
}
