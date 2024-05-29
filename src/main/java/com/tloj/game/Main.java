package com.tloj.game;

import com.tloj.game.game.Controller;


/**
 * Entry point of the program.
 */
public class Main {
    public static void main(String[] args) {
        Controller controller = Controller.getInstance();
        controller.run();
    }
}