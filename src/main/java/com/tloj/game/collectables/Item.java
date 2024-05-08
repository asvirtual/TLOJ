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

    protected Item(double weight, int dropMoney) {
        this.weight = weight;
        this.dropMoney = dropMoney;
    }

    public double getWeight() {
        return weight;
    }

    public double getDropChance() {
        return 0;
    }

    public int getDropMoney() {
        return dropMoney;
    }

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
}
