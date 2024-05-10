package com.tloj.game.entities;

import com.tloj.game.collectables.Item;
import com.tloj.game.entities.npcs.Smith;


/**
 * An interface representing an entity that can receive items in the game<br>
 * This interface is meant to be implemented by entities that can receive items as {@link Smith}<br>
 */
public interface ItemReceiverEntity {
    void giveItem(Item item);
}
