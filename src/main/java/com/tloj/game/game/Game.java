package com.tloj.game.game;

import java.util.ArrayList;
import java.util.Date;

import com.tloj.game.entities.Character;
import com.tloj.game.entities.Mob;
import com.tloj.game.entities.Boss;
import com.tloj.game.rooms.BossRoom;
import com.tloj.game.rooms.HealingRoom;
import com.tloj.game.rooms.HostileRoom;
import com.tloj.game.rooms.LootRoom;
import com.tloj.game.rooms.Room;
import com.tloj.game.rooms.RoomType;
import com.tloj.game.rooms.StartRoom;
import com.tloj.game.rooms.TrapRoom;
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
        this.levels = new ArrayList<Level>();
        for (int i = 0; i < map.size(); i++)
            this.levels.add(new Level(i, map.get(i)));

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
        if (!this.getCurrentRoom().isCleared())
        {
            this.getCurrentRoom().roomCleared();
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
        room.removeMob();
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
        // TODO Auto-generated method stub
        throw new UnsupportedOperationException("Unimplemented method 'onMobDefeated'");
    }

    @Override
    public void onBossDefeated() {
        // TODO Auto-generated method stub
        throw new UnsupportedOperationException("Unimplemented method 'onBossDefeated'");

    public static void main(String[] args) {

        ArrayList<ArrayList<ArrayList<Room>>> map = new ArrayList<ArrayList<ArrayList<Room>>>();

        ArrayList<ArrayList<Room>> level = new ArrayList<ArrayList<Room>>();

        ArrayList<Room> row = new ArrayList<Room>();
        HostileRoom h1 = new HostileRoom(new Coordinates(3, 0), null);
        h1.visit();
        HostileRoom h2 = new HostileRoom(new Coordinates(4, 0), null);
        h2.visit();
        row.add(null);
        row.add(null);
        row.add(null);
        row.add(h1);
        row.add(h2);
        row.add(null);
        level.add(row);

        ArrayList<Room> row1 = new ArrayList<Room>();
        LootRoom l1 = new LootRoom(new Coordinates(3, 1), null, false);
        l1.visit();
        row1.add(null);
        row1.add(null);
        row1.add(null);
        row1.add(l1);
        row1.add(null);
        row1.add(null);
        level.add(row1);

        ArrayList<Room> row2 = new ArrayList<Room>();
        TrapRoom t1 = new TrapRoom(new Coordinates(3, 2));
        t1.visit();
        row2.add(null);
        row2.add(null);
        row2.add(null);
        row2.add(t1);
        row2.add(null);
        row2.add(null);
        level.add(row2);

        ArrayList<Room> row3 = new ArrayList<Room>();
    
        row3.add(null);
        row3.add(null);
        row3.add(null);
        row3.add(null);
        row3.add(null);
        row3.add(null);
        level.add(row3);

        ArrayList<Room> row4 = new ArrayList<Room>();
        StartRoom s1 = new StartRoom(new Coordinates(3, 4));
        s1.visit();
        row4.add(null);
        row4.add(null);
        row4.add(null);
        row4.add(s1);
        row4.add(null);
        row4.add(null);
        level.add(row4);

    
        ArrayList<Room> row5 = new ArrayList<Room>();
        BossRoom b1 = new BossRoom(new Coordinates(2, 5), null);
        HealingRoom hl = new HealingRoom(new Coordinates(3, 5));
        row5.add(null);
        row5.add(null);
        row5.add(b1);
        row5.add(hl);
        row5.add(null);
        row5.add(null);
        level.add(row5);

        map.add(level);

        /*
        for (int l = 0; l < Game.DEFAULT_LEVELS_COUNT; l++) {
            ArrayList<ArrayList<Room>> level = new ArrayList<ArrayList<Room>>();

            for (int i = 0; i < Game.DEFAULT_ROOMS_ROWS; i++) {
                ArrayList<Room> row = new ArrayList<Room>();

                for (int j = 0; j < Game.DEFAULT_ROOMS_COLS; j++) {
                    row.add(new HostileRoom(new Coordinates(j, i), null));
                }

                level.add(row);    
            }

            map.add(level);
        }
        */

        Game game = new Game(map);
        game.printMap();
    }

    public void printMap(){
        for (int i = 0; i < this.currentLevel.getRoomsRowCount(); i++) {
            for (int j = 0; j < this.currentLevel.getRoomsColCount(); j++) {
                Room room = this.currentLevel.getRoom(new Coordinates(i, j));
                if(room == null){
                    System.out.print("\u00A0" + " ");                    
                    continue;
                }
                //if (this.getCurrentRoom().equals(room)) {
                if (i==3 && j==3) {
                    System.out.print("\u0398" + " ");
                } else {
                    
                    System.out.print(room.toString());
                }
            }
            System.out.println();
        }
    }

}
