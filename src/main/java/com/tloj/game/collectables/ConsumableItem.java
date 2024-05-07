package com.tloj.game.collectables;

import com.tloj.game.entities.Character;


/**
 * An interface representing an item that can be consumed in the game (e.g. potions, food, etc.)<br>
 */
public interface ConsumableItem {
    void consume(Character consumer);
}
