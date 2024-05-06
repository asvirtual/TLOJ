package com.tloj.game.collectables;


public abstract class Item {
    protected double weight;
    protected Object effect;

    protected Item(double weight, Object effect) {
        this.weight = weight;
        this.effect = effect;
    }

    public double getWeight() {
        return weight;
    }
}
