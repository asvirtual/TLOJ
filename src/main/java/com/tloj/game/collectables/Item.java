package com.tloj.game.collectables;


/**
 * An abstract class representing an item that can be found in the game<br>
 * All items have a weight, and can be picked up by the player<br>
 * @see Weapon
 * @see PurchasableItem
 */
public abstract class Item {
    protected double weight;

    protected Item(double weight) {
        this.weight = weight;
    }

    public double getWeight() {
        return weight;
    }
}
