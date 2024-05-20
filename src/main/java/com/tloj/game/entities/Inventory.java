package com.tloj.game.entities;

import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.List;

import com.fasterxml.jackson.annotation.JsonBackReference;
import com.fasterxml.jackson.annotation.JsonCreator;
import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonManagedReference;
import com.fasterxml.jackson.annotation.JsonProperty;
import com.tloj.game.collectables.Item;
import com.tloj.game.utilities.ConsoleHandler;


public class Inventory {
    @JsonProperty("items")
    private ArrayList<Item> items;
    @JsonBackReference
    @JsonProperty("holder")
    private ItemsHolderEntity holder;
    @JsonProperty
    private double totalWeight;

    public Inventory(ItemsHolderEntity holder) {
        this.holder = holder;
        this.items = new ArrayList<Item>();
    }

    public Inventory(
        ItemsHolderEntity holder,
        List<Item> items
    ) {
        this.holder = holder;
        this.items = new ArrayList<Item>(items);
    }

    @JsonCreator
    public Inventory(
        @JsonProperty("holder") ItemsHolderEntity holder,
        @JsonProperty("items") List<Item> items,
        @JsonProperty("totalWeight") double totalWeight
    ) {
        this.holder = holder;
        this.totalWeight = totalWeight;
        this.items = new ArrayList<Item>(items);
    }

    public double getTotalWeight() {
        return this.totalWeight;
    }

    @JsonIgnore
    public int getSize() {
        return this.items.size();
    }

    @JsonIgnore
    public int getCount(Item item) {
        int count = 0;
        for (Item i : this.items)
            if (i.getId() == item.getId()) count++;

        return count;
    }

    @JsonIgnore
    public Item getByIndex(int index) {
        return this.items.get(index);
    }

    @JsonIgnore
    public Item get(Item item){
        for (Item i : this.items) 
            if (i.equals(item)) return i;
        
        return null;
    }

    public void remove(Item item) {
        this.items.remove(item);
        this.totalWeight -= item.getWeight();
        this.sort();
    }

    public Item removeByIndex(int index) {
        Item item = this.items.remove(index);
        this.totalWeight -= item.getWeight();
        this.sort();

        return item;
    }

    public boolean add(Item item) {
        if (item == null) return false;

        if (!this.holder.canCarry(item)) {
            ConsoleHandler.println(ConsoleHandler.RED + "You can't carry more weight, drop something first." + ConsoleHandler.RESET);
            return false;
        }
        
        this.items.add(item);
        this.totalWeight += item.getWeight();
        this.sort();
        return true;
    }

    @JsonIgnore
    public Item getByName(String itemName) {
        for (Item item : this.items) 
            if (itemName.equalsIgnoreCase(item.toString())) return item;

        return null;
    }

    /**
     * Sorts the inventory by item id, lower id first
    */
    public void sort() {
        Collections.sort(this.items, new Comparator<Item>() {
            @Override
            public int compare(Item first, Item second) {
                if (first.getId() < second.getId()) return -1;
                else return 1;
            }
        });
    }

    @Override
    public String toString() {
        String inventory = ConsoleHandler.YELLOW + "Jordan's Inventory:" + ConsoleHandler.RESET + "\n";
        for (int i = 0; i < this.items.size(); i++)
            inventory += (i + 1) + ". " + this.items.get(i) + "\n";

        return inventory;

    }
}
