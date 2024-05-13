package com.tloj.game.collectables;

import com.tloj.game.collectables.items.AttackElixir;
import com.tloj.game.collectables.items.DefenseElixir;
import com.tloj.game.collectables.items.GreatHealthPotion;
import com.tloj.game.collectables.items.GreatManaPotion;
import com.tloj.game.collectables.items.HealthPotion;
import com.tloj.game.collectables.items.Lockpick;
import com.tloj.game.collectables.items.ManaPotion;
import com.tloj.game.collectables.items.NorthStar;
import com.tloj.game.collectables.items.Ragu;
import com.tloj.game.collectables.items.WeaponShard;
import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonTypeInfo;


// Needed to serialize/deserialize subclasses of Character, by including the class name in the JSON
@JsonTypeInfo(
  use = JsonTypeInfo.Id.CLASS, 
  include = JsonTypeInfo.As.PROPERTY, 
  property = "@class")
  
/**
 * An abstract class representing an item that can be found in the game<br>
 * All items have a weight, and can be picked up by the player<br>
 * @see Weapon
 * @see PurchasableItem
 */
public abstract class Item {
    protected double weight;
    protected int dropMoney;
    protected int id;
    protected double dropChance;
    
    enum ConsumableItems {
        HEALTH_POTION(new HealthPotion()),
        GREAT_HEALTH_POTION(new GreatHealthPotion()),
        MANA_POTION(new ManaPotion()),
        GREAT_MANA_POTION(new GreatManaPotion()),
        ATTACK_ELIXIR(new AttackElixir()),
        DEFENSE_ELIXIR(new DefenseElixir()),
        WEAPON_SHARD(new WeaponShard()),
        LOCKPICK(new Lockpick()),
        RAGU(new Ragu()),
        NORTH_STAR(new NorthStar());

        private Item item;

        ConsumableItems(Item item) {
            this.item = item;
        }
    }

    protected Item(double weight, int dropMoney, int id) {
        this.weight = weight;
        this.dropMoney = dropMoney;
        this.id = id;
    }

    protected Item(double weight, int dropMoney, int id, double dropChance) {
        this.weight = weight;
        this.dropMoney = dropMoney;
        this.id = id;
        this.dropChance = dropChance;
    }

    public double getWeight() {
        return weight;
    }

    public double getDropChance() {
        return this.dropChance;
    }

    public int getDropMoney() {
        return dropMoney;
    }

    public int getId() {
        return id;
    }

    /*
     * used to get a random item based on their drop chances. 
     * It first calculates the total drop chance of all items, then generates a random value within this range. 
     * It then goes through the items again, adding each item's drop chance to a cumulative total 
     * until this total is greater than or equal to the random value, at which point it returns the current item
     */
    public static Item getRandomItem() {
        double totalProbability = 0.0;
        for (ConsumableItems i : ConsumableItems.values()) {
            Item item = i.item;
            totalProbability += item.getDropChance();
        }

        double randomValue = Math.random() * totalProbability;
        double cumulativeProbability = 0.0;
        for (ConsumableItems i : ConsumableItems.values()) {
            Item item = i.item;
            cumulativeProbability += item.getDropChance();
            if (randomValue <= cumulativeProbability) return item;
        }

        return null; // Should never reach here if probabilities sum to 1
    }

    @Override
    public String toString() {
        return String.join(" ", this.getClass().getSimpleName().split("(?=[A-Z])"));
    };

    @JsonIgnore
    public abstract String getASCII();

    @JsonIgnore
    public abstract String describe();
}
