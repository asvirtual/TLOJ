package com.tloj.game.game;

import java.util.ArrayList;
import java.util.Date;

import com.tloj.game.entities.Character;
import com.tloj.game.entities.Mob;
import com.tloj.game.entities.Boss;
import com.tloj.game.rooms.BossRoom;
import com.tloj.game.rooms.HostileRoom;
import com.tloj.game.rooms.Room;
import com.tloj.game.rooms.RoomType;
import com.tloj.game.utilities.Coordinates;
import com.tloj.game.utilities.GameState;


public class Game implements CharacterObserver {    
    public static final int DEFAULT_LEVELS_COUNT = 3;
    public static final int DEFAULT_ROOMS_ROWS = 6;
    public static final int DEFAULT_ROOMS_COLS = 6;

    private long seed;
    private int score;
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
    
    public Game(long seed, Level currentLevel, Character player, ArrayList<Level> levels) {
        this.levels = levels;
        this.currentLevel = currentLevel;
        this.controller = Controller.getInstance();
        this.seed = seed;
    }

    public GameData getGameData() {
        return new GameData(
            this.seed,
            this.currentLevel,
            this.player,
            this.levels
        );
    }

    public int getScore() {
        return this.score;
    }

    public void updateScore(int score) {
        this.score += score;
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

    public void movePlayer(Coordinates.Direction direction) throws IllegalArgumentException, IllegalStateException {
        if (this.controller.getState() == GameState.FIGHTING_BOSS || this.controller.getState() == GameState.FIGHTING_MOB)
            throw new IllegalStateException("Cannot move while fighting");
            
        Coordinates newCoordinates = this.player.getPosition().getAdjacent(direction);
        if (!this.areCoordinatesValid(newCoordinates)) throw new IllegalArgumentException("Invalid coordinates");
        
        /**
         * updats player score if the room is cleared
         */
        if (!this.getCurrentRoom().isCleared()) {
            this.getCurrentRoom().clear();
            this.updateScore(Room.SCORE_DROP);
        }

        this.player.move(newCoordinates);
        PlayerRoomVisitor PlayerRoomVisitor = new PlayerRoomVisitor(this.player);
        this.currentLevel.getRoom(newCoordinates).accept(PlayerRoomVisitor);
    }

    public void playerAttack() throws IllegalStateException {
        if (
            (this.controller.getState() != GameState.FIGHTING_BOSS && this.controller.getState() != GameState.FIGHTING_MOB) ||
            (this.getCurrentRoom().getType() != RoomType.BOSS_ROOM && this.getCurrentRoom().getType() != RoomType.HOSTILE_ROOM)
        )
            throw new IllegalStateException("Cannot attack outside of a fight");

        /** 
         * Player in a HostileRoom/BossRoom, attack its Mob/Boss
        */
        HostileRoom room = (HostileRoom) this.getCurrentRoom();
        Mob mob = room.getMob();
        this.player.attack(mob);
        
        if (mob.isAlive()) {
            mob.attack(this.player);
            return;
        }

        /** Reset stats to how they were before the fight, so that elixirs' effects are canceled */
        this.player.resetFightStats();

        /**
         * If the defeated Mob was a Boss, ...
         * Otherwise, ...
         * TODO: Maybe this should be moved to the "die" methods in the Mob and Boss classes
        */
        if (mob instanceof Boss) {
            System.out.println("You've defeated the Boss!");
            this.updateScore(Boss.SCORE_DROP);
            this.controller.setState(GameState.MOVING);
        } else {
            System.out.println("You've defeated the enemy!");
            this.updateScore(Mob.SCORE_DROP);
            this.controller.setState(GameState.MOVING);
        }

        this.player.lootMob(mob);
    }

    public void usePlayerSkill() throws IllegalStateException {
        if (
            (this.controller.getState() != GameState.FIGHTING_BOSS && this.controller.getState() != GameState.FIGHTING_MOB) ||
            (this.getCurrentRoom().getType() != RoomType.BOSS_ROOM && this.getCurrentRoom().getType() != RoomType.HOSTILE_ROOM)
        )
            throw new IllegalStateException("Cannot use skill outside of a fight");

        // this.player.useSkill();
    }

    public boolean areCoordinatesValid(Coordinates coordinates) {
        int roomsRowCount = this.currentLevel.getRoomsRowCount();
        int roomsColCount = this.currentLevel.getRoomsColCount();
        
        return coordinates.getY() >= 0 && coordinates.getY() < roomsRowCount &&
               coordinates.getX() >= 0 && coordinates.getX() < roomsColCount;
    }

    public void save() {
        GameData gameData = this.getGameData();
        gameData.serializeJSON();
        // TODO: Save in JSON file
    }

    public Room getCurrentRoom() {
        return this.currentLevel.getRoom(this.player.getPosition());
    }

    public void dropItem(int index) {
        this.player.removeInventoryItem(index);
    }

    @Override
    public void onMobDefeated() {
        HostileRoom room = (HostileRoom) this.getCurrentRoom();
        room.clear();
    }

    @Override
    public void onBossDefeated() {
        BossRoom room = (BossRoom) this.getCurrentRoom();
        room.clear();
    }

    @Override
    public void onPlayerDefeated() {

    }

    @Override
    public void onPlayerLevelUp() {

    }

    @Override
    public void onPlayerMove() {
        this.getCurrentRoom().visit();
    }
}
