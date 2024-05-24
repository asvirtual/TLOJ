package com.tloj.game.entities;

import com.tloj.game.game.Coordinates;


/**
 * Represents an entity that can move in the game. <br>
 * @see Character
 * @see Glitched
 */

public interface MovingEntity {
    /**
     * Moves the entity to the specified coordinates. <br>
     * @param to The coordinates to move the entity to
     */
    void move(Coordinates to);
}
