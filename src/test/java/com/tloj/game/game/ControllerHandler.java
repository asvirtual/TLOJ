package com.tloj.game.game;

import java.io.ByteArrayInputStream;
import java.io.InputStream;


public class ControllerHandler {
    public static void deleteController() {
        Controller.instance = null;
    }

    public static void setInput(String input){
        System.setIn(new ByteArrayInputStream(input.getBytes()));
    }

    public static void resetInput(InputStream s){
        System.setIn(s);
    }
}
