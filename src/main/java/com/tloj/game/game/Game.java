package com.tloj.game.game;

import java.util.ArrayList;
import java.util.Date;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonProperty;
import com.tloj.game.collectables.ConsumableItem;
import com.tloj.game.collectables.Item;
import com.tloj.game.collectables.PurchasableItem;
import com.tloj.game.collectables.items.SpecialKey;
import com.tloj.game.entities.Boss;
import com.tloj.game.entities.Character;
import com.tloj.game.entities.FriendlyEntity;
import com.tloj.game.entities.ItemReceiverEntity;
import com.tloj.game.entities.Mob;
import com.tloj.game.rooms.BossRoom;
import com.tloj.game.rooms.EndRoom;
import com.tloj.game.rooms.HealingRoom;
import com.tloj.game.rooms.HostileRoom;
import com.tloj.game.rooms.Room;
import com.tloj.game.rooms.RoomType;
import com.tloj.game.utilities.ConsoleColors;
import com.tloj.game.utilities.Constants;
import com.tloj.game.utilities.Coordinates;
import com.tloj.game.utilities.Dice;
import com.tloj.game.utilities.GameState;


public class Game implements CharacterObserver {
    private long seed;
    private int score;
    private Level currentLevel;
    private Character player;
    @JsonProperty
    private ArrayList<Level> levels;
    private Controller controller;
    @JsonProperty
    private long elapsedTime;
    private long sessionStartTime;

    public Game(ArrayList<Level> levels) {
        this.levels = levels;

        this.currentLevel = this.levels.get(0);
        this.controller = Controller.getInstance();
        this.seed = new Date().getTime();
        this.elapsedTime = 0;
        this.sessionStartTime = new Date().getTime();

        Dice.setSeed(this.seed);
    }
    
    public Game(long seed, Level currentLevel, Character player, ArrayList<Level> levels) {
        this.player = player;
        this.levels = levels;
        this.currentLevel = currentLevel;
        this.controller = Controller.getInstance();
        this.seed = seed;
        this.sessionStartTime = new Date().getTime();
        
        this.player.addObserver(this);
        Dice.setSeed(this.seed);
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

    public void increaseScore(int amount) {
        this.score += amount;
    }
    
    public long getSeed() {
        return this.seed;
    }

    public void setSeed(long seed) {
        this.seed = seed;
        Dice.setSeed(seed);
    }

    public void setPlayer(Character player) {
        this.player = player;
        this.player.setCurrentLevel(this.currentLevel);
        this.player.addObserver(this);
    }

    public Character getPlayer() {
        return this.player;
    }

    public Level getLevel() {
        return this.currentLevel;
    }

    public void movePlayer(Coordinates.Direction direction) throws IllegalArgumentException {    
        PlayerRoomVisitor playerRoomVisitor = new PlayerRoomVisitor(this.player); 

        if (this.controller.getState() == GameState.BOSS_DEFEATED || this.getCurrentRoom().getType() == RoomType.HEALING_ROOM) {
            /**
             * Move to the next level (level numbers are 1-based, so this.currentLevel.getLevelNumber() 
             * is the index of the next level in the levels (0-based) list)
            */
            this.currentLevel = this.levels.get(this.currentLevel.getLevelNumber());
            this.player.setCurrentLevel(this.currentLevel);

            if (this.currentLevel.getStartRoom() != null)
                this.currentLevel.getStartRoom().accept(playerRoomVisitor);

            return;
        }

        Coordinates newCoordinates = this.player.getPosition().getAdjacent(direction);

        if (!this.getLevel().areCoordinatesValid(newCoordinates)) throw new IllegalArgumentException("Invalid coordinates");
        if (
            this.currentLevel.getRoom(newCoordinates).isLocked() && 
            !this.player.hasItem(new SpecialKey())
        ) throw new IllegalArgumentException("That room is locked");
        
        this.player.move(newCoordinates);

        Room room = this.currentLevel.getRoom(newCoordinates);
        room.accept(playerRoomVisitor);
    }

    public void playerAttack() {
        /** 
         * Player in a HostileRoom/BossRoom, attack its Mob/Boss
        */
        HostileRoom room = (HostileRoom) this.getCurrentRoom();
        Mob mob = room.getMob();
        this.player.attack(mob);
        
        if (mob.isAlive()) {
            Controller.clearConsole(1000);
            mob.attack(this.player);

            /**
             * No further action, the fight continues
             */
            if (mob.getPosition() == this.player.getPosition()) return; 
        }

        // Get new mob if there is one
        if (room.getMobsCount() == 0) return;
        mob = room.getMob();

        Controller.clearConsole(2000);
        System.out.println("You've encountered " + mob + mob.getASCII() + "\n");
    }

    public void usePlayerSkill() {
        this.player.useSkill();
    }

    public void saveLocally() {
        this.elapsedTime += new Date().getTime() - this.sessionStartTime;
        GameData.saveToFile(this, "test.json");
        // TODO: Save in JSON file (and/or in cloud)
    }

    public void uploadToCloud() {
        // TODO: Upload to cloud
    }

    public Room getCurrentRoom() {
        return this.currentLevel.getRoom(this.player.getPosition());
    }

    public void dropItem(int index) {
        if (index < 1 || index > this.player.getInventorySize()) {
            System.out.println("Couldn't find that item in your inventory");
            return;
        }

        Item item = this.player.getInventoryItem(index - 1);

        if (item instanceof PurchasableItem)
            this.player.setMoney(
                this.player.getMoney() + 
                ((PurchasableItem) this.player.getInventoryItem(index - 1)).getPrice() / 2
            );

        this.player.removeInventoryItem(index - 1);
    }

    @Override
    public void onMobDefeated(Mob mob) {
        HostileRoom room = (HostileRoom) this.getCurrentRoom();

        /** Reset stats to how they were before the fight, so that elixirs' effects are canceled */
        this.player.resetFightStats();
        this.player.lootMob(mob);

        System.out.println("You've defeated the " + mob + "!\n");
        
        this.increaseScore(Mob.SCORE_DROP);

        if (room.getMobsCount() == 1) {
            room.clear(this.player);
            this.controller.setState(GameState.MOVING);
            this.printMap();
        } else {
            room.removeMob(mob);
            Controller.clearConsole(2000);
            System.out.println("You've encountered " + room.getMob() + room.getMob().getASCII() + "\n");
        }
    }

    @Override
    public void onBossDefeated() {
        BossRoom room = (BossRoom) this.getCurrentRoom();
        Boss boss = room.getBoss();

        room.clear(this.player);

        /** Reset stats to how they were before the fight, so that elixirs' effects are canceled */
        this.player.resetFightStats();
        this.player.lootMob(boss);

        System.out.println(ConsoleColors.GREEN_BOLD_BRIGHT + "You've defeated " + boss + "!" + ConsoleColors.RESET + "\n");
        this.increaseScore(Boss.SCORE_DROP);
        this.controller.setState(GameState.BOSS_DEFEATED);
    }

    @Override
    public void onPlayerDefeated() {
        System.out.println(ConsoleColors.RED_BOLD_BRIGHT +
            "\n" + Constants.GAME_OVER + ConsoleColors.RESET + "\n" +
            "Jordan ended his adventure with " + this.score + "points!\n" +
            Constants.GAME_TITLE
        );

        this.controller.setState(GameState.MAIN_MENU);
    }

    @Override
    public void onPlayerLevelUp() {
        System.out.println(ConsoleColors.GREEN_BRIGHT + "You've leveled up! You are now level " + this.player.getLvl() + "!\n" + ConsoleColors.RESET);
    }

    public void printMap(){
        System.out.println(String.join("\n", this.generateMapLines()) + "\n");
    }
    
    public String[] generateMapLines() {
        StringBuilder mapBuilder = new StringBuilder();

        
        mapBuilder
            .append("  ")
            .append("--".repeat(this.currentLevel.getRoomsColCount() / 2 - 1))
            .append(this.currentLevel.getRoomsColCount() % 2 == 0 ? "GN" : "N")
            .append("--".repeat(this.currentLevel.getRoomsColCount() / 2 - 1))
            .append("\n");
        
        for (int i = 0; i < this.currentLevel.getRoomsRowCount(); i++) {

            if (this.currentLevel.getRoomsColCount() % 2 == 0) {
                if (i == this.currentLevel.getRoomsColCount() / 2) mapBuilder.append("W ");
                else mapBuilder.append("| ");
            } else {
                if (i == this.currentLevel.getRoomsColCount() / 2 - 1) mapBuilder.append("G ");
                else if (i == this.currentLevel.getRoomsColCount() / 2) mapBuilder.append("W ");
                else mapBuilder.append("| ");
            }

            for (int j = 0; j < this.currentLevel.getRoomsColCount(); j++) {
                Room room = this.currentLevel.getRoom(new Coordinates(j, i));
                if (room == null) {
                    mapBuilder.append("\u00A0" + " ");                    
                    continue;
                }
    
                if (this.getCurrentRoom().equals(room)) mapBuilder.append(ConsoleColors.YELLOW_BOLD_BRIGHT + "\u0398 " + ConsoleColors.RESET);
                else mapBuilder.append(room + " ");
            }

            if (this.currentLevel.getRoomsColCount() % 2 == 0) {
                if (i == this.currentLevel.getRoomsColCount() / 2) mapBuilder.append("\bE\n");
                else mapBuilder.append("\b|\n");
            } else {
                if (i == this.currentLevel.getRoomsColCount() / 2 - 1) mapBuilder.append("\bG\n");
                else if (i == this.currentLevel.getRoomsColCount() / 2) mapBuilder.append("\bE\n");
                else mapBuilder.append("\b|\n");
            }
            
        }
        
        mapBuilder
            .append("  ")
            .append("--".repeat(this.currentLevel.getRoomsRowCount() / 2 - 1))
            .append(this.currentLevel.getRoomsColCount() % 2 == 0 ? "GS" : "S")
            .append("--".repeat(this.currentLevel.getRoomsRowCount() / 2 - 1))
            .append("\n");
        
        return mapBuilder.toString().split("\n");
    }

    public void printInventory() {
        this.player.sortInventory();
        System.out.println("Inventory:");

        for (int i = 0; i < this.player.getInventorySize(); i++) 
            System.out.println((i + 1) + ". " + this.player.getInventoryItem(i));
    }

    public void useItem(int index) {
        if (index < 1 || index > this.player.getInventorySize()) {
            System.out.println("Couldn't find that item in your inventory");
            return;
        }

        ConsumableItem item = (ConsumableItem) this.player.getInventoryItem(index - 1);
        item.consume(this.player);
    }
    
    public void returnToStart() {
        Coordinates startCoordinates = this.currentLevel.getStartRoom().getCoordinates();
        this.player.move(startCoordinates);
    }

    public void giveItem(String receiverName, String itemName) {
        Item item = this.player.getItemByName(itemName);
        if (item == null) return;

        FriendlyEntity entity = this.getCurrentRoom().getFriendlyEntityByName(receiverName);
        
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

        if (this.currentLevel.areCoordinatesValid(coordinates.getAdjacent(Coordinates.Direction.NORTH))) {
            if (currentLevel.getRoom(coordinates.getAdjacent(Coordinates.Direction.NORTH)).getType() == RoomType.BOSS_ROOM)
                directions += "[gn - Something's off... ]";
            else
                directions += "[gn] ";    
        }
        
        if (this.currentLevel.areCoordinatesValid(coordinates.getAdjacent(Coordinates.Direction.SOUTH))) {
            if (currentLevel.getRoom(coordinates.getAdjacent(Coordinates.Direction.SOUTH)).getType() == RoomType.BOSS_ROOM)
                directions += "[gs - Something's off... ]";
            else
                directions += "[gs] ";  
        }

        if (this.currentLevel.areCoordinatesValid(coordinates.getAdjacent(Coordinates.Direction.EAST))) {
            if (currentLevel.getRoom(coordinates.getAdjacent(Coordinates.Direction.EAST)).getType() == RoomType.BOSS_ROOM)
                directions += "[ge - Something's off... ]";
            else
                directions += "[ge] ";
        }

        if (this.currentLevel.areCoordinatesValid(coordinates.getAdjacent(Coordinates.Direction.WEST))) {
            if (currentLevel.getRoom(coordinates.getAdjacent(Coordinates.Direction.WEST)).getType() == RoomType.BOSS_ROOM)
                directions += "[gw - Something's off... ]";
            else
                directions += "[gw] ";    
        }
        
        return directions;
    }
}
