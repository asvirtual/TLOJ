package com.tloj.game.game;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonProperty;
import com.tloj.game.collectables.ConsumableItem;
import com.tloj.game.collectables.Item;
import com.tloj.game.collectables.items.NorthStar;
import com.tloj.game.collectables.items.SpecialKey;
import com.tloj.game.entities.Boss;
import com.tloj.game.entities.Character;
import com.tloj.game.entities.FriendlyEntity;
import com.tloj.game.entities.ItemReceiverEntity;
import com.tloj.game.entities.Mob;
import com.tloj.game.entities.bosses.FlyingBoss;
import com.tloj.game.rooms.BossRoom;
import com.tloj.game.rooms.HostileRoom;
import com.tloj.game.rooms.Room;
import com.tloj.game.rooms.RoomType;
import com.tloj.game.skills.CharacterSkill;
import com.tloj.game.utilities.ConsoleHandler;
import com.tloj.game.utilities.Constants;
import com.tloj.game.utilities.Coordinates;
import com.tloj.game.utilities.Dice;
import com.tloj.game.utilities.FirebaseHandler;
import com.tloj.game.utilities.GameState;
import com.tloj.game.utilities.JsonParser;


/**
 * The Game class represents the main game logic and state management.<br>
 * It controls player movement, interactions with rooms and entities,
 * combat, inventory management, saving and loading game data, and more.
 */
public class Game implements CharacterObserver {
    /** The random seed used for generating random numbers in the game. */
    private long seed;
    /** The current score of the player. */
    private int score;
    /** The current level the player is in. */
    private Level currentLevel;
     /** The player character controlled by the player. */
    private Character player;
    /** The list of levels in the game. */
    @JsonProperty
    private ArrayList<Level> levels;
    /** The controller responsible for managing game state. */
    private Controller controller;
     /** The elapsed time since the start of the game session. */
    @JsonProperty
    private long elapsedTime;
    /** The start time of the game session. */
    private long sessionStartTime;

    private int currentId;

    /** ID of the game istances. */
    //@Json
    private static int id=0;

    /**
     * Constructs a new Game object with the given list of levels.
     * @param levels The list of levels in the game.
     */
    public Game(ArrayList<Level> levels) {
        this.levels = levels;

        this.currentLevel = this.levels.get(0);
        this.controller = Controller.getInstance();
        this.seed = new Date().getTime();
        this.elapsedTime = 0;
        this.sessionStartTime = new Date().getTime();
        this.currentId=id++;

        Dice.setSeed(this.seed);
    }
    
    /**
     * Constructs a new Game object with the given parameters.
     *
     * @param seed         The random seed used for generating random numbers in the game.
     * @param currentLevel The current level the player is in.
     * @param player       The player character controlled by the player.
     * @param levels       The list of levels in the game.
     */
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

    public int getId() {
        return this.currentId;
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

    /**
     * Moves the player character in the specified direction.
     * Throws an IllegalArgumentException if the move is invalid.
     *
     * @param direction The direction in which to move the player.
     * @throws IllegalArgumentException If the move is invalid.
     */
    public void movePlayer(Coordinates.Direction direction) throws IllegalArgumentException {  
        // Create a visitor for the player to interact with the room.
        PlayerRoomVisitor playerRoomVisitor = new PlayerRoomVisitor(this.player);

        if (
            this.controller.getState() == GameState.BOSS_DEFEATED ||
            this.controller.getState() == GameState.WIN || 
            this.getCurrentRoom().getType() == RoomType.HEALING_ROOM
        ) {
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

        // Check if the room at the new coordinates is locked and the player does not have a special key.
        if (!this.getLevel().areCoordinatesValid(newCoordinates)) throw new IllegalArgumentException("Invalid coordinates");
        if (
            this.currentLevel.getRoom(newCoordinates).isLocked() && 
            !this.player.hasItem(new SpecialKey())
        ) throw new IllegalArgumentException("That room is locked");
        
        /** Reset fight stats, so that elixirs' effects are canceled */
        this.player.resetFightStats();

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
            mob.attack(this.player);

            /**
             * No further action, the fight continues
             */
            if (mob.getPosition().equals(this.player.getPosition())) return; 
        }

        CharacterSkill playerSkill = this.player.getSkill();
        if (playerSkill != null) playerSkill.setActivated(false);

        // Boss defeated
        if (room.getType() == RoomType.BOSS_ROOM) return;

        // If no mobs left in the room, clear it and go back to moving state
        if (room.getMobsCount() == 0) {
            room.setCleared(true);
            this.printMap();
            this.controller.setState(GameState.MOVING);
            return;
        }
        
        // Get new mob if there is one
        mob = room.getMob();        

        ConsoleHandler.clearConsole();

        ConsoleHandler.println(ConsoleHandler.PURPLE + "You've encountered " + room.getMob() + ConsoleHandler.RESET + "\n");
        Controller.printSideBySideText(
            room.getMob().getASCII(), 
            room.getMob().getPrettifiedStatus() + "\n\n\n" + this.player.getPrettifiedStatus() + "\n\n"
        );

        System.out.println();
    }

    public void usePlayerSkill() {
        this.player.useSkill();
    }

    public void saveLocally() {
        this.elapsedTime += new Date().getTime() - this.sessionStartTime;
        String path = "/path"+this.currentId + ".json";
        JsonParser.saveToFile(this, path);
        uploadToCloud(path);
    }

    public void uploadToCloud(String filepath) {
        FirebaseHandler.saveToFirebaseBucket(filepath);
    }
    
    @JsonIgnore
    public Room getCurrentRoom() {
        return this.currentLevel.getRoom(this.player.getPosition());
    }

    public Item dropItem(int index) {
        if (index < 1 || index > this.player.getInventorySize()) {
            System.out.println("Couldn't find that item in your inventory");
            return null;
        }

        return this.player.removeInventoryItem(index - 1);
    }

    @Override
    public void onMobDefeated(Mob mob) {
        ConsoleHandler.clearConsole();

        HostileRoom room = (HostileRoom) this.getCurrentRoom();

        this.player.lootMob(mob);

        Controller.awaitEnter();
        
        this.increaseScore(Mob.SCORE_DROP);

        // There are other mobs in the room
        if (room.getMobsCount() == 1) room.clear(this.player);
        else {
            room.removeMob(mob);
            ConsoleHandler.println(ConsoleHandler.PURPLE + "You've encountered " + room.getMob() + ConsoleHandler.RESET + "\n" + room.getMob().getASCII() + "\n");
        }
    }

    @Override
    public void onBossDefeated(Boss boss) {
        ConsoleHandler.clearConsole();

        BossRoom room = (BossRoom) this.getCurrentRoom();

        room.clear(this.player);

        /** Reset stats to how they were before the fight, so that elixirs' effects are canceled */
        this.player.resetFightStats();
        this.player.lootMob(boss);

        this.increaseScore(Boss.SCORE_DROP);
        
        // If the boss is the final boss, the game is won
        this.controller.setState(boss instanceof FlyingBoss ? GameState.WIN : GameState.BOSS_DEFEATED);
    }

    @Override
    public void onPlayerDefeated() {
        System.out.println();

        Controller.awaitEnter();
        ConsoleHandler.clearConsole();

        Runnable loopRunnable = new Runnable() {
            @Override
            public void run() {
                Controller.getInstance().changeMusic(
                    Constants.LOOP_WAV_FILE_PATH,
                    true
                );
            }
        };

        Runnable introRunnable = new Runnable() {
            @Override
            public void run() {
                Game.this.controller.changeMusic(
                    Constants.INTRO_WAV_FILE_PATH,
                    false,
                    loopRunnable
                );
            }
        };

        this.controller.changeMusic(
            Constants.GAME_OVER_WAV_FILE_PATH, 
            false,
            introRunnable
        );
        
        System.out.println(ConsoleHandler.RED_BOLD_BRIGHT +
            "\n" + Constants.GAME_OVER + ConsoleHandler.RESET + "\n" +
            "Jordan ended his adventure with " + this.score + " points!\n\n"
        );

        Controller.awaitEnter();
        ConsoleHandler.clearConsole();

        System.out.println(Constants.GAME_TITLE);
        this.controller.setState(GameState.MAIN_MENU);
    }

    public void printMap() {
        System.out.println(String.join("\n", this.generateMapLines()) + "\n");
    }

    /**
     * Generates an array of strings representing the map layout.
     * Each string in the array represents a row of the map.
     *
     * @return An array of strings representing the map layout.
     */
    public String[] generateMapLines() {
        StringBuilder mapBuilder = new StringBuilder();

        // Append the top border of the map.
        mapBuilder
            .append(" -")
            
            .append("--".repeat(this.currentLevel.getRoomsColCount() / 2 - ((this.currentLevel.getRoomsColCount() + 1) % 2)))
            .append(this.currentLevel.getRoomsColCount() % 2 == 0 ? "GN" : "N")
            .append("--".repeat(this.currentLevel.getRoomsColCount() / 2 - ((this.currentLevel.getRoomsColCount() + 1) % 2)))
            .append("--\n");
        
        // Iterate through each row of rooms in the current level.
        for (int i = 0; i < this.currentLevel.getRoomsRowCount(); i++) {
            if ((this.currentLevel.getRoomsRowCount() + 1) % 2 == 0) {
                if (i == this.currentLevel.getRoomsRowCount() / 2) mapBuilder.append("\bW ");
                else mapBuilder.append("\b| ");
            } else {
                if (i == this.currentLevel.getRoomsRowCount() / 2 - 1) mapBuilder.append("G ");
                else if (i == this.currentLevel.getRoomsRowCount() / 2) mapBuilder.append("W ");
                else mapBuilder.append("| ");
            }

            // Iterate through each column of rooms in the current row.
            for (int j = 0; j < this.currentLevel.getRoomsColCount(); j++) {
                Room room = this.currentLevel.getRoom(new Coordinates(j, i));
                if (room == null) {
                    mapBuilder.append("\u00A0 ");                    
                    continue;
                }

                // Highlight the current room if it is the player's current location.
                if (this.getCurrentRoom().equals(room)) mapBuilder.append(ConsoleHandler.YELLOW_BOLD_BRIGHT + "\u0398 " + ConsoleHandler.RESET);
                else {
                    if (this.player.hasItem(new NorthStar())) mapBuilder.append(room.getRoomRepresentation() + "\s");
                    else mapBuilder.append(room + "\s");
                }
            }

            // Append vertical borders or gates at the end of each row based on the current column count.
            if ((this.currentLevel.getRoomsRowCount() + 1) % 2 == 0) {
                if (i == this.currentLevel.getRoomsRowCount() / 2) mapBuilder.append("\bE\n");
                else mapBuilder.append("\b|\n");
            } else {
                if (i == this.currentLevel.getRoomsRowCount() / 2 - 1) mapBuilder.append("G\n");
                else if (i == this.currentLevel.getRoomsRowCount() / 2) mapBuilder.append("E\n");
                else mapBuilder.append("|\n");
            }
            
        }

        // Append the bottom border of the map.
        mapBuilder
            .append(" -")
            .append("--".repeat(this.currentLevel.getRoomsColCount() / 2 - ((this.currentLevel.getRoomsColCount() + 1) % 2)))
            .append(this.currentLevel.getRoomsColCount() % 2 == 0 ? "GS" : "S")
            .append("--".repeat(this.currentLevel.getRoomsColCount() / 2 - ((this.currentLevel.getRoomsColCount() + 1) % 2)))
            .append("--\n");
        
        return mapBuilder.toString().split("\n");
    }

    public void printInventory() {
        this.player.sortInventory();
        System.out.println("Inventory:");

        for (int i = 0; i < this.player.getInventorySize(); i++) 
            System.out.println((i + 1) + ". " + this.player.getInventoryItem(i));
    }

    public Item useItem(int index) {
        if (index < 1 || index > this.player.getInventorySize()) {
            System.out.println(ConsoleHandler.RED + "Couldn't find that item in your inventory" + ConsoleHandler.RESET);
            return null;
        }

        Item item = this.player.getInventoryItem(index - 1);
        if (!(item instanceof ConsumableItem)) {
            System.out.println(ConsoleHandler.RED + item + " cannot be consumed!" + ConsoleHandler.RESET);
            return null;
        }

        ConsumableItem consumableItem = (ConsumableItem) item;
        consumableItem.consume(this.player);

        return item;
    }

    public void infoItem(int index) {
        if (index < 1 || index > this.player.getInventorySize()) {
            System.out.println("Couldn't find that item in your inventory");
            return;
        }

        Item item = this.player.getInventoryItem(index - 1);
        System.out.println("\n" + item.describe());
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

    /**
     * Returns a string indicating the available directions for the player to move.
     * The string includes symbols representing each valid direction and any special notes.
     *
     * @return A string indicating the available directions for the player to move.
     */
    @JsonIgnore
    public String getAvailableDirections() {
        Coordinates coordinates = this.player.getPosition();
        String directions = "You can: \n";

        if (this.currentLevel.areCoordinatesValid(coordinates.getAdjacent(Coordinates.Direction.NORTH))) {
            Room northRoom = currentLevel.getRoom(coordinates.getAdjacent(Coordinates.Direction.NORTH));

            if (northRoom.getType() == RoomType.BOSS_ROOM) directions += ConsoleHandler.RED + "[gn - Something's off... ]" + ConsoleHandler.RESET;
            else directions += northRoom.isVisited() ? "[" + ConsoleHandler.CYAN_UNDERLINED + "gn" + ConsoleHandler.RESET + "] " : "[gn] ";    
        }
        
        if (this.currentLevel.areCoordinatesValid(coordinates.getAdjacent(Coordinates.Direction.SOUTH))) {
            Room southRoom = currentLevel.getRoom(coordinates.getAdjacent(Coordinates.Direction.SOUTH));

            if (southRoom.getType() == RoomType.BOSS_ROOM) directions += ConsoleHandler.RED + "[gs - Something's off... ]" + ConsoleHandler.RESET;
            else directions += southRoom.isVisited() ? "[" + ConsoleHandler.CYAN_UNDERLINED + "gs" + ConsoleHandler.RESET + "] " : "[gs] ";    
        }

        if (this.currentLevel.areCoordinatesValid(coordinates.getAdjacent(Coordinates.Direction.EAST))) {
            Room eastRoom = currentLevel.getRoom(coordinates.getAdjacent(Coordinates.Direction.EAST));

            if (eastRoom.getType() == RoomType.BOSS_ROOM) directions += ConsoleHandler.RED + "[ge - Something's off... ]" + ConsoleHandler.RESET;
            else directions += eastRoom.isVisited() ? "[" + ConsoleHandler.CYAN_UNDERLINED + "ge" + ConsoleHandler.RESET + "] " : "[ge] ";    
        }

        if (this.currentLevel.areCoordinatesValid(coordinates.getAdjacent(Coordinates.Direction.WEST))) {
            Room westRoom = currentLevel.getRoom(coordinates.getAdjacent(Coordinates.Direction.WEST));

            if (westRoom.getType() == RoomType.BOSS_ROOM) directions += ConsoleHandler.RED + "[gw - Something's off... ]" + ConsoleHandler.RESET;
            else directions += westRoom.isVisited() ? "[" + ConsoleHandler.CYAN_UNDERLINED + "gw" + ConsoleHandler.RESET + "] " : "[gw] ";    
        }
        
        return directions;
    }
}
