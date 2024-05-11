package com.tloj.game.utilities;

import java.util.Random;

import com.fasterxml.jackson.annotation.JsonCreator;
import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonProperty;


/**
 * Represents a dice in the game<br>
 * A dice is a random number generator with a minimum and maximum value<br>
 * The dice can be rolled to get a random number between the minimum and maximum values<br>
 */
public class Dice {
    private int min;
    private int max;
    private static Random random;

    public Dice(int faces) {
        this.min = 1;
        this.max = faces;
    }

    @JsonCreator
    public Dice(@JsonProperty("min") int min, @JsonProperty("max") int max) {
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
        return Dice.random.nextInt(max - min + 1) + min;
    }

    public static void setSeed(long seed) {
        Dice.random = new Random(seed);
    }

    @Override
    public String toString() {
        return "D" + (this.max - this.min + 1);
    }
}
