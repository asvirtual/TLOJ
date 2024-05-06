package com.tloj.game.game;

import java.util.ArrayList;
import java.util.Date;

import com.tloj.game.entities.Character;
import com.tloj.game.entities.Mob;
import com.tloj.game.rooms.HostileRoom;
import com.tloj.game.rooms.Room;
import com.tloj.game.utilities.Coordinates;
import com.tloj.game.utilities.GameState;


public class Game {    
    public static final int DEFAULT_LEVELS_COUNT = 3;
    public static final int DEFAULT_ROOMS_ROWS = 6;
    public static final int DEFAULT_ROOMS_COLS = 6;

    private long seed;
    private Level currentLevel;
    private Character player;
    private ArrayList<Level> levels;
    private Controller controller;

    public Game(ArrayList<ArrayList<ArrayList<Room>>> map) {
        this.levels = new ArrayList<Level>(map.size());
        for (int i = 0; i < map.size(); i++)
            this.levels.set(i, new Level(i, map.get(i)));

        this.currentLevel = this.levels.get(0);
        this.controller = Controller.getInstance();
        this.seed = new Date().getTime();
    }

    public Game(GameData gameData) {
        this.currentLevel = gameData.currentLevel;
        this.player = gameData.player;
        this.levels = gameData.levels;
        this.controller = Controller.getInstance();
        this.seed = new Date().getTime();
    }

    public long getSeed() {
        return this.seed;
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

    public void movePlayer(Coordinates.Direction direction) throws IllegalArgumentException, IllegalStateException {
        if (this.controller.getState() == GameState.FIGHTING_BOSS || this.controller.getState() == GameState.FIGHTING_MOB)
            throw new IllegalStateException("Cannot move while fighting");

        Coordinates newCoordinates = this.player.getPosition().getAdjacent(direction);
        if (!this.areCoordinatesValid(newCoordinates)) throw new IllegalArgumentException("Invalid coordinates");
        
        this.player.move(newCoordinates);
        PlayerRoomVisitor PlayerRoomVisitor = new PlayerRoomVisitor(this.player);
        this.currentLevel.getRoom(newCoordinates).accept(PlayerRoomVisitor);
    }

    public void playerAttack() throws IllegalStateException {
        if (this.controller.getState() != GameState.FIGHTING_BOSS && this.controller.getState() != GameState.FIGHTING_MOB)
            throw new IllegalStateException("Cannot attack while not fighting");

        HostileRoom room = (HostileRoom) this.getCurrentRoom();
        Mob mob = room.getMob();
        this.player.attack(mob);
        
        if (!mob.isAlive()) {
            System.out.println("You've defeated the enemy!");
            this.player.lootMob(mob);
            room.removeMob();
            this.controller.setState(GameState.MOVING);
        }
    }

    public boolean areCoordinatesValid(Coordinates coordinates) {
        int roomsRowCount = this.currentLevel.getRoomsRowCount();
        int roomsColCount = this.currentLevel.getRoomsColCount();
        
        return coordinates.getY() >= 0 && coordinates.getY() < roomsRowCount &&
               coordinates.getX() >= 0 && coordinates.getX() < roomsColCount;
    }

    public void save() {
        GameData gameData = new GameData(this);
        gameData.serializeJSON();
        // TODO: Save in JSON file
    }

    public Room getCurrentRoom() {
        return this.currentLevel.getRoom(this.player.getPosition());
    }
}
