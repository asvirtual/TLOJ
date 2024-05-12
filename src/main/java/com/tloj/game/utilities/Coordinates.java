package com.tloj.game.utilities;

import com.fasterxml.jackson.annotation.JsonCreator;
import com.fasterxml.jackson.annotation.JsonProperty;


public class Coordinates {

    /**
     * Represents the direction of movement.
     */
    public enum Direction {
        NORTH,
        SOUTH,
        WEST,
        EAST
    }

    private int x;
    private int y;

    /**
     * Constructs a new instance of the Coordinates class with the specified x and y values.
     *
     * @param x The x-coordinate.
     * @param y The y-coordinate.
     */
    @JsonCreator
    public Coordinates(@JsonProperty("x") int x, @JsonProperty("y") int y) {
        this.x = x;
        this.y = y;
    }

    /**
     * Gets the x-coordinate.
     *
     * @return The x-coordinate.
     */
    public int getX() {
        return x;
    }

    /**
     * Gets the y-coordinate.
     *
     * @return The y-coordinate.
     */
    public int getY() {
        return y;
    }

    /**
     * Gets the adjacent coordinates in the specified direction.
     *
     * @param direction The direction to move.
     * @return The adjacent coordinates.
     */
    public Coordinates getAdjacent(Direction direction) {
        switch (direction) {
            case NORTH:
                return new Coordinates(x, y - 1);
            case SOUTH:
                return new Coordinates(x, y + 1);
            case WEST:
                return new Coordinates(x - 1, y);
            case EAST:
                return new Coordinates(x + 1, y);
            default:
                return this;
        }
    }

    /**
     * Returns a string representation of the coordinates.
     *
     * @return The string representation of the coordinates.
     */
    @Override
    public String toString() {
        return "(" + this.x + ", " + this.y + ")";
    }

    /**
     * Checks if the specified object is equal to this coordinates object.
     *
     * @param obj The object to compare.
     * @return True if the objects are equal, false otherwise.
     */
    @Override
    public boolean equals(Object obj) {
        if (obj == this) return true;
        if (!(obj instanceof Coordinates)) return false;
        Coordinates other = (Coordinates) obj;
        return this.x == other.x && this.y == other.y;
    }
}
