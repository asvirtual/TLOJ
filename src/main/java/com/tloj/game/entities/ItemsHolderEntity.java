package com.tloj.game.entities;

import com.tloj.game.collectables.Item;


public interface ItemsHolderEntity {
    double getMaxWeight();
    double getFreeWeight();
    double getCarriedWeight();
    int getItemCount(Item item);
    int getInventorySize();
    Item getInventoryItem(int index);
    Item getInventoryItem(Item item);
    String getInventoryString();
    Item getItemByName(String itemName);
    Item removeInventoryItem(Item item);
    Item removeInventoryItem(int index);
    boolean addInventoryItem(Item item);
    boolean hasItem(Item item);
    boolean canCarry(Item item);
}
