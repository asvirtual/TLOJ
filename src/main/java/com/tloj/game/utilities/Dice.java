package com.tloj.game.utilities;

import com.fasterxml.jackson.annotation.JsonIgnore;

public class Dice {
    private int min;
    private int max;

    /**
     * Default constructor to allow Jackson to deserialize JSON.
     */
    public Dice() {}

    public Dice(int faces) {
        this.min = 1;
        this.max = faces;
    }

    public Dice (int min, int max) {
        this.min = min;
        this.max = max;
    }

    public int getMin() {
        return this.min;
    }

    public int getMax() {
        return this.max;
    }

    @JsonIgnore
    public int getFaces() {
        return this.max - this.min + 1;
    }
    
    public int roll() {
        return (int) (Math.random() * (this.max - this.min + 1)) + 1;
    }

    @Override 
    public String toString() {
        return "D" + (this.max - this.min + 1);
    }
}
