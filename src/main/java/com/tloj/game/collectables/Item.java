package com.tloj.game.collectables;

import com.tloj.game.collectables.items.AttackElixir;
import com.tloj.game.collectables.items.DefenseElixir;
import com.tloj.game.collectables.items.GreatHealthPotion;
import com.tloj.game.collectables.items.GreatManaPotion;
import com.tloj.game.collectables.items.HealthPotion;
import com.tloj.game.collectables.items.Emp;
import com.tloj.game.collectables.items.ManaPotion;
import com.tloj.game.collectables.items.NorthStar;
import com.tloj.game.collectables.items.Ragu;
import com.tloj.game.collectables.items.WeaponShard;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonTypeInfo;


/**
 * An abstract class representing an item in the game<br>
 * All items have a weight, and can be picked up by the player<br>
 * Some items can be purchased, and some can be consumed to provide benefits to the player<br>
 * @see Weapon
 * @see PurchasableItem
 * @see ConsumableItem
 */

 // Needed to serialize/deserialize subclasses of Character, by including the class name in the JSON
@JsonTypeInfo(
    use = JsonTypeInfo.Id.CLASS, 
    include = JsonTypeInfo.As.PROPERTY, 
    property = "@class")
    
public abstract class Item {
    /** 
     * The weight of the item. The player has a limited carrying capacity, reached which they can no longer pick up items.<br>
     */
    protected double weight;
    protected int id;
    /**
     * The chance that the item will be dropped by a mob upon defeat.<br>
     */
    protected double dropChance;
    
    /**
     * An enum that contains all the items that can be dropped by defeating mobs or in the loot rooms.<br>
     */
    enum DroppableItems {
        HEALTH_POTION(new HealthPotion()),
        GREAT_HEALTH_POTION(new GreatHealthPotion()),
        MANA_POTION(new ManaPotion()),
        GREAT_MANA_POTION(new GreatManaPotion()),
        ATTACK_ELIXIR(new AttackElixir()),
        DEFENSE_ELIXIR(new DefenseElixir()),
        WEAPON_SHARD(new WeaponShard()),
        EMP(new Emp()),
        RAGU(new Ragu()),
        NORTH_STAR(new NorthStar());

        private Item item;

        DroppableItems(Item item) {
            this.item = item;
        }
    }

    protected Item(double weight, int id) {
        this.weight = weight;
        this.id = id;
    }

    protected Item(double weight, int id, double dropChance) {
        this.weight = weight;
        this.id = id;
        this.dropChance = dropChance;
    }

    public double getWeight() {
        return weight;
    }

    public double getDropChance() {
        return this.dropChance;
    }

    public int getId() {
        return id;
    }

    /**
     * Used to get a random item based on their drop chances. 
     */
    public static Item getRandomItem() {
        // Calculate the total drop chance of all items
        double totalProbability = 0.0;
        for (DroppableItems i : DroppableItems.values()) {
            Item item = i.item;
            totalProbability += item.getDropChance();
        }

        // Generate a random value within the total drop chance range
        double randomValue = Math.random() * totalProbability;
        // Go through the items again, adding each item's drop chance to a cumulative total
        double cumulativeProbability = 0.0;
        for (DroppableItems i : DroppableItems.values()) {
            Item item = i.item;
            cumulativeProbability += item.getDropChance();
            // Return the current item if the cumulative total is greater than or equal to the random value
            if (randomValue <= cumulativeProbability) return item;
        }

        // Should never reach here if probabilities sum to 1
        return null; 
    }

    @Override
    public String toString() {
        // Split the class name by capital letters and join the resulting array with spaces
        return String.join(" ", this.getClass().getSimpleName().split("(?=[A-Z])"));
    };

    /**
     * Used to get the ASCII representation of the item
     * @return The ASCII representation of the item
     */
    @JsonIgnore
    public abstract String getASCII();

    /**
     * Used to get the description of the item
     * @return The description of the item
     */
    @JsonIgnore
    public abstract String describe();

    @Override
    public boolean equals(Object obj) {
        if (obj == null) return false;
        if (obj == this) return true;
        if (obj.getClass() != this.getClass()) return false;
        Item item = (Item) obj;
        return item.getId() == this.id;
    }
}
