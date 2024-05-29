package com.tloj.game.collectables;

import com.tloj.game.entities.Character;


/**
 * An interface representing an item that can be consumed in the game (e.g. potions, food, etc.)<br>
 */
public interface ConsumableItem {
    /**
     * Consumes the item, providing benefits to the consumer<br>
     * @param consumer The character that consumes the item
     */
    void consume(Character consumer);
}
