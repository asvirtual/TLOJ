package com.tloj.game.rooms.roomeffects;

import com.tloj.game.entities.Character;   
import com.tloj.game.game.Controller;

public class TpEffect implements RoomEffect {
    @Override
    public void applyEffect(Character character) {
        Controller controller = Controller.getInstance();
    }   
    
}