package com.tloj.game.utilities;

import com.fasterxml.jackson.annotation.JsonCreator;
import com.fasterxml.jackson.annotation.JsonProperty;

public class Coordinates {
    public enum Direction {
        NORTH,
        SOUTH,
        WEST,
        EAST
    }

    private int x;
    private int y;

    @JsonCreator
    public Coordinates(@JsonProperty("x") int x, @JsonProperty("y") int y) {
        this.x = x;
        this.y = y;
    }

    public int getX() {
        return x;
    }

    public int getY() {
        return y;
    }

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

    @Override
    public String toString() {
        return "(" + this.x + ", " + this.y + ")";
    }
}
