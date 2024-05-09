package com.tloj.game.game;

import java.util.ArrayList;
import java.util.Date;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.tloj.game.collectables.ConsumableItem;
import com.tloj.game.collectables.Item;
import com.tloj.game.collectables.PurchasableItem;
import com.tloj.game.collectables.items.SpecialKey;
import com.tloj.game.collectables.items.WeaponShard;
import com.tloj.game.entities.Boss;
import com.tloj.game.entities.Character;
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

    public Game(ArrayList<ArrayList<ArrayList<Room>>> map) {
        this.levels = new ArrayList<Level>();
        for (int i = 0; i < map.size(); i++)
            this.levels.add(new Level(i, map.get(i)));

        this.currentLevel = this.levels.get(0);
        this.controller = Controller.getInstance();
        this.seed = new Date().getTime();
    }
    
    public Game(long seed, Level currentLevel, Character player, ArrayList<Level> levels) {
        this.player = player;
        this.levels = levels;
        this.currentLevel = currentLevel;
        this.controller = Controller.getInstance();
        this.seed = seed;
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
        GameData gameData = this.getGameData();
        gameData.serializeJSON();
        // TODO: Save in JSON file
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

        System.out.println("You've defeated the enemy!");
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

        System.out.println("You've defeated the Boss!");
        this.updateScore(Boss.SCORE_DROP);
        this.controller.setState(GameState.BOSS_DEFEATED);
    }

    @Override
    public void onPlayerDefeated() {

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
                else System.out.print(room);
            }
            
            System.out.println();
        }
    }

    public void printInventory() {
        this.player.sortInventory();
        System.out.println("Inventory:");
        for (int i = 0; i < this.player.getInventorySize(); i++) {
            System.out.println(i + ". " + this.player.getInventoryItem(i).toString());
        }
    }

    @Override
    public void onPlayerLevelUp() {

    }

    public void useItem(int index) {
        ConsumableItem item = (ConsumableItem) this.player.getInventoryItem(index);
        item.consume(this.player);
    }
    
    public void returnToStart() {
        if (this.controller.getState() == GameState.MOVING) {
            this.player.move(this.currentLevel.getStartRoom().getCoordinates());
        }
        else {
            System.out.println("There's a time and place for everything but not now!");
        }
    }

    public void printPlayerStatus() {
        System.out.println(this.player);
    }

    public void giveItem(String reciver, String itemName) {
        
        if(!reciver.equalsIgnoreCase("Smith")){
            System.out.print("Who?");
            return;
        }

        if(!itemName.equalsIgnoreCase("WeapoShard")){
            System.out.print("Wrong Item!");
            return;
        }
        
        if(this.player.searchInventoryItem(new WeaponShard()) != null){
            this.getPlayer().removeInventoryItem(this.player.searchInventoryItem(new WeaponShard()));
            this.player.getWeapon().upgrade(1);
            System.out.println("You have upgraded " + this.player.getWeapon().toString() +"!");
        }


    }




}
