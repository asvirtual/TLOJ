package com.tloj.game.utilities;


public class Coordinates {
    public enum Direction {
        NORTH,
        SOUTH,
        WEST,
        EAST
    }

    private int x;
    private int y;

    public Coordinates(int x, int y) {
        this.x = x;
        this.y = y;
    }

    /**
     * Default constructor to allow Jackson to deserialize JSON.
     */
    public Coordinates() {}

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
