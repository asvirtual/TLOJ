package com.tloj.game.game;

import com.tloj.game.rooms.Room;


public class CharacterObserver implements Observer {
    private Game game;
    
    public CharacterObserver(Game game) {
        this.game = game;
    }

    public void levelUp() {
        
    }

    public void roomCleared() {
        this.game.getCurrentRoom().roomCleared();
        this.game.updateScore(Room.SCORE_DROP);
    }
}
