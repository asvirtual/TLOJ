package com.tloj.game.game;

import java.util.ArrayList;
import java.util.Date;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonProperty;
import com.tloj.game.collectables.ConsumableItem;
import com.tloj.game.collectables.Item;
import com.tloj.game.collectables.PurchasableItem;
import com.tloj.game.collectables.items.SpecialKey;
import com.tloj.game.collectables.items.WeaponShard;
import com.tloj.game.entities.Boss;
import com.tloj.game.entities.Character;
import com.tloj.game.entities.FriendlyEntity;
import com.tloj.game.entities.ItemReceiverEntity;
import com.tloj.game.entities.Mob;
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
    @JsonProperty
    private long elapsedTime;
    private long sessionStartTime;

    // public Game(ArrayList<ArrayList<ArrayList<Room>>> map) {
    //     this.levels = new ArrayList<Level>();
    //     for (int i = 0; i < map.size(); i++)
    //         this.levels.add(new Level(i, map.get(i)));

    //     this.currentLevel = this.levels.get(0);
    //     this.controller = Controller.getInstance();
    //     this.seed = new Date().getTime();
    //     this.elapsedTime = 0;
    //     this.sessionStartTime = new Date().getTime();
    // }

    public Game(ArrayList<Level> levels) {
        this.levels = levels;

        this.currentLevel = this.levels.get(0);
        this.controller = Controller.getInstance();
        this.seed = new Date().getTime();
        this.elapsedTime = 0;
        this.sessionStartTime = new Date().getTime();
    }
    
    public Game(long seed, Level currentLevel, Character player, ArrayList<Level> levels) {
        this.player = player;
        this.levels = levels;
        this.currentLevel = currentLevel;
        this.controller = Controller.getInstance();
        this.seed = seed;
        this.sessionStartTime = new Date().getTime();
    }

    @JsonIgnore
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
            
        if (this.controller.getState() == GameState.BOSS_DEFEATED || this.getCurrentRoom().getType() == RoomType.HEALING_ROOM) {
            this.currentLevel = this.levels.get(this.currentLevel.getLevelNumber() + 1);
            this.player.setCurrentLevel(this.currentLevel);
            return;
        }

        Coordinates newCoordinates = this.player.getPosition().getAdjacent(direction);
        if (!this.getLevel().areCoordinatesValid(newCoordinates)) throw new IllegalArgumentException("Invalid coordinates");
        if (this.currentLevel.getRoom(newCoordinates).isLocked() && !this.player.hasItem(new SpecialKey())) throw new IllegalArgumentException("Room is locked");
        
        /**
         * updats player score if the room is cleared
         * TODO: This could probably be moved to the CharacterObserver, after the player has ended the interactions/events in the room
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
        
        if (mob.isAlive()) mob.attack(this.player);
    }

    public void usePlayerSkill() throws IllegalStateException {
        if (
            (this.controller.getState() != GameState.FIGHTING_BOSS && this.controller.getState() != GameState.FIGHTING_MOB) ||
            (this.getCurrentRoom().getType() != RoomType.BOSS_ROOM && this.getCurrentRoom().getType() != RoomType.HOSTILE_ROOM)
        )
            throw new IllegalStateException("Cannot use skill outside of a fight");

        this.player.useSkill();
    }

    public void save() {
        this.elapsedTime += new Date().getTime() - this.sessionStartTime;
        GameData gameData = this.getGameData();
        gameData.serializeJSON();
        // TODO: Save in JSON file (and/or in cloud)
    }

    public Room getCurrentRoom() {
        return this.currentLevel.getRoom(this.player.getPosition());
    }

    public void dropItem(int index) {
        Item item = this.player.getInventoryItem(index);

        if (item instanceof PurchasableItem)
            this.player.setMoney(
                this.player.getMoney() + 
                ((PurchasableItem) this.player.getInventoryItem(index)).getPrice()
            );

        this.player.removeInventoryItem(index);
    }

    @Override
    public void onMobDefeated(Mob mob) {
        HostileRoom room = (HostileRoom) this.getCurrentRoom();

        room.clear();

        /** Reset stats to how they were before the fight, so that elixirs' effects are canceled */
        this.player.resetFightStats();
        this.player.lootMob(mob);

        System.out.println("You've defeated the " + mob + "!");
        
        this.updateScore(Mob.SCORE_DROP);
        this.controller.setState(GameState.MOVING);
    }

    @Override
    public void onBossDefeated() {
        BossRoom room = (BossRoom) this.getCurrentRoom();
        Boss boss = room.getBoss();

        room.clear();

        /** Reset stats to how they were before the fight, so that elixirs' effects are canceled */
        this.player.resetFightStats();
        this.player.lootMob(boss);

        System.out.println("You've defeated " + boss +"!");
        this.updateScore(Boss.SCORE_DROP);
        this.controller.setState(GameState.BOSS_DEFEATED);
    }

    @Override
    public void onPlayerDefeated() {
        System.out.println("You've been defeated!");
        this.controller.setState(GameState.GAME_OVER);
    }

    public void printMap(){
        for (int i = 0; i < this.currentLevel.getRoomsRowCount(); i++) {
            for (int j = 0; j < this.currentLevel.getRoomsColCount(); j++) {
                Room room = this.currentLevel.getRoom(new Coordinates(i, j));
                if (room == null){
                    System.out.print("\u00A0" + " ");                    
                    continue;
                }

                if (this.getCurrentRoom().equals(room)) System.out.print("\u0398" + " ");
                else System.out.print(room + " ");
            }
            
            System.out.println();
        }
    }

    public void printInventory() {
        this.player.sortInventory();
        System.out.println("Inventory:");
        for (int i = 0; i < this.player.getInventorySize(); i++) {
            Item item = this.player.getInventoryItem(i);
            int count = this.player.getItemCount(item);
            if (count > 1) System.out.println(i + ". " + item + " (x" + count + ")");
            else System.out.println(i + ". " + item);
        }
    }

    public void useItem(int index) {
        ConsumableItem item = (ConsumableItem) this.player.getInventoryItem(index);
        item.consume(this.player);
    }
    
    public void returnToStart() {
        this.player.move(this.currentLevel.getStartRoom().getCoordinates());
    }

    public void printPlayerStatus() {
        System.out.println(this.player);
    }

    public void giveItem(String receiverName, String itemName) {
        Item item = this.player.getItem(itemName);
        if (item == null) return;

        FriendlyEntity entity = this.getCurrentRoom().getFriendlyEntity(receiverName);
        
        if (entity == null) {
            System.out.println("There is no such entity in this room");
            return;
        }

        if (!(entity instanceof ItemReceiverEntity)) {
            System.out.println("This entity cannot receive items");
            return;
        }
        
        if (!Controller.awaitConfirmation()) return;
        
        ItemReceiverEntity receiver = (ItemReceiverEntity) entity;
        receiver.giveItem(item);
    }

    public String getAvailableDirections() {
        Coordinates coordinates = this.player.getPosition();
        String directions = "You can: \n";
        String N = "[gn] ";
        String S = "[gs] ";
        String E = "[ge] ";
        String W = "[gn] ";
        String Nb = "[gn] - Something's off... ";
        String Sb = "[gs] - Something's off... ";
        String Eb = "[ge] - Something's off... ";
        String Wb = "[gn] - Something's off... ";

        if (this.currentLevel.areCoordinatesValid(coordinates.getAdjacent(Coordinates.Direction.NORTH))) {
            if (currentLevel.getRoom(coordinates.getAdjacent(Coordinates.Direction.NORTH)).getType() == RoomType.BOSS_ROOM)
                directions += Nb;
            else
                directions += N;    
        }
        
        if (this.currentLevel.areCoordinatesValid(coordinates.getAdjacent(Coordinates.Direction.SOUTH))) {
            if (currentLevel.getRoom(coordinates.getAdjacent(Coordinates.Direction.SOUTH)).getType() == RoomType.BOSS_ROOM)
                directions += Sb;
            else
                directions += S;  
        }

        if (this.currentLevel.areCoordinatesValid(coordinates.getAdjacent(Coordinates.Direction.EAST))) {
            if (currentLevel.getRoom(coordinates.getAdjacent(Coordinates.Direction.EAST)).getType() == RoomType.BOSS_ROOM)
                directions += Eb;
            else
                directions += E;
        }

        if (this.currentLevel.areCoordinatesValid(coordinates.getAdjacent(Coordinates.Direction.WEST))) {
            if (currentLevel.getRoom(coordinates.getAdjacent(Coordinates.Direction.WEST)).getType() == RoomType.BOSS_ROOM)
                directions += Wb;
            else
                directions += W;    
        }
        
        return directions;
    }
}
