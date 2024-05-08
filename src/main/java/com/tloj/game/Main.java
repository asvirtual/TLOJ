package com.tloj.game;

import com.tloj.game.game.Controller;
public class Main {
    public static void main(String[] args) {
        
        Controller controller = Controller.getInstance();
        controller.run();
    }
}