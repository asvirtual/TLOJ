package com.tloj.game.game;

import java.util.ArrayList;
import com.tloj.game.entities.Character;
import com.tloj.game.rooms.RoomType;
import com.tloj.game.utilities.Coordinates;


public class Game {    
    public static final int DEFAULT_LEVELS_COUNT = 3;
    public static final int DEFAULT_ROOMS_ROWS = 6;
    public static final int DEFAULT_ROOMS_COLS = 6;

    private Level currentLevel;
    private Character player;
    private ArrayList<Level> levels;

    public Game(RoomType[][][] map) {
        this.levels = new ArrayList<Level>(map.length);
        for (int i = 0; i < map.length; i++)
            this.levels.set(i, new Level(i, map[i]));

        this.currentLevel = this.levels.get(0);
    }

    public Game(GameData gameData) {
        this.currentLevel = gameData.currentLevel;
        this.player = gameData.player;
        this.levels = gameData.levels;
    }

    public void setPlayer(Character player) {
        this.player = player;
    }

    public Character getPlayer() {
        return this.player;
    }

    public Level getLevel() {
        return this.currentLevel;
    }

    public ArrayList<Level> getLevels() {
        return this.levels;
    }

    public void movePlayer(Coordinates.Direction direction) throws IllegalArgumentException {
        Coordinates newCoordinates = this.player.getPosition().getAdjacent(direction);
        if (!this.areCoordinatesValid(newCoordinates)) throw new IllegalArgumentException("Invalid coordinates");

        this.player.move(newCoordinates);
        this.currentLevel.getRoom(newCoordinates).enter();
    }

    public boolean areCoordinatesValid(Coordinates coordinates) {
        int roomsRowCount = this.currentLevel.getRoomsRowCount();
        int roomsColCount = this.currentLevel.getRoomsColCount();
        
        return coordinates.getX() >= 0 && coordinates.getY() < roomsRowCount &&
               coordinates.getX() >= 0 && coordinates.getY() < roomsColCount;
    }
}
