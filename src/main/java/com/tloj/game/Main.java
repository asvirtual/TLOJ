package com.tloj.game;

import com.tloj.game.game.Controller;


/**
 * Main class of the game<br>
 * Contains the main method to run the game
 */
public class Main {
    public static void main(String[] args) {
        Controller controller = Controller.getInstance();
        controller.run();
    }
}