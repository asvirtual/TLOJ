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
    private int roll;

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
        this.roll = Dice.random.nextInt(max - min + 1) + min;
        return this.roll;
    }

    public static void setSeed(long seed) {
        Dice.random = new Random(seed);
    }

    public static String getASCII(int roll) {
        return switch (roll) {
            case 0 -> Constants.DICE_NEGATED;
            case 1 -> Constants.DICE_ONE;
            case 2 -> Constants.DICE_TWO;
            case 3 -> Constants.DICE_THREE;
            case 4 -> Constants.DICE_FOUR;
            case 5 -> Constants.DICE_FIVE;
            case 6 -> Constants.DICE_SIX;
            case 7 -> Constants.DICE_SEVEN;
            case 8 -> Constants.DICE_EIGHT;
            case 9 -> Constants.DICE_NINE;
            case 10 -> Constants.DICE_TEN;
            case 11 -> Constants.DICE_ELEVEN;
            case 12 -> Constants.DICE_TWELVE;
            case 13 -> Constants.DICE_THIRTHEEN;
            case 14 -> Constants.DICE_FOURTEEN;
            case 15 -> Constants.DICE_FIFTEEN;
            case 16 -> Constants.DICE_SIXTEEN;
            case 17 -> Constants.DICE_SEVENTEEN;
            case 18 -> Constants.DICE_EIGHTEEN;
            case 19 -> Constants.DICE_NINETEEN;
            case 20 -> Constants.DICE_TWENTY;
            default -> Constants.DICE_NEGATED;
        };
    }

    @Override
    public String toString() {
        return "D" + (this.max - this.min + 1);
    }
}
