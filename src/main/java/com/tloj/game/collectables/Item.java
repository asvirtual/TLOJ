package com.tloj.game.collectables;


public abstract class Item {
    protected double weight;

    protected Item(double weight) {
        this.weight = weight;
    }

    public double getWeight() {
        return weight;
    }
}
