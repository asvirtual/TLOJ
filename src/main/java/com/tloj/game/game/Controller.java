package com.tloj.game.game;

import java.io.IOException;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Scanner;
import java.util.Stack;
import java.util.function.Supplier;

import com.fasterxml.jackson.annotation.JsonIgnore;

import com.tloj.game.rooms.Room;
import com.tloj.game.rooms.HealingRoom;
import com.tloj.game.rooms.HostileRoom;
import com.tloj.game.collectables.Item;
import com.tloj.game.entities.Character;
import com.tloj.game.entities.FriendlyEntity;
import com.tloj.game.entities.ItemReceiverEntity;
import com.tloj.game.entities.characters.BasePlayer;
import com.tloj.game.entities.characters.Hacker;
import com.tloj.game.entities.characters.DataThief;
import com.tloj.game.entities.characters.MechaKnight;
import com.tloj.game.entities.characters.NeoSamurai;
import com.tloj.game.entities.npcs.Merchant;
import com.tloj.game.entities.npcs.Smith;
import com.tloj.game.utilities.Constants;
import com.tloj.game.utilities.Coordinates;
import com.tloj.game.utilities.GameState;
import com.tloj.game.utilities.MusicPlayer;


/**
 * Command pattern implementation to handle the different game commands<br>
 * The available commands are:<br>
 * - Go North (gn) {@link MoveNorthCommand}<br>
 * - Go South (gs) {@link MoveSouthCommand}<br>
 * - Go West (gw) {@link MoveWestCommand}<br>
 * - Go East (ge) {@link MoveEastCommand}<br>
 * - Attack (atk) {@link AttackCommand}<br>
 * - Use item (use [item]) {@link UseItemCommand}<br>
 * - Drop item (drop [item]) {@link DropItemCommand}<br>
 * - Print seed (seed) {@link PrintSeedCommand}<br>
 * - Print score (score) {@link PrintScoreCommand}<br>
 * - Quit (quit) {@link QuitCommand}<br>
 * - Back (back) {@link PreviousStateCommand} (used during complex interactions)<br>
 * - Help (help) {@link HelpCommand}<br>
 * - Return (return) {@link ReturnCommand} (used to go back to starting room)<br>
 * - Print status (status) {@link PrintStatusCommand}<br>
 * - Merchant (merchant) {@link MerchantCommand} (talk with merchant in healing room)<br>
 * - Show list (showlist) {@link ShowListCommand} (show merchant items list)<br>
 * - Buy (buy [item]) {@link BuyCommand} (buy item from merchant)<br>
 * - Smith (smith) {@link SmithCommand} (talk with smith in healing room)<br>
 * - Give (give [item]) {@link GiveCommand} (give item to npc)<br>
 * - Confirm (confirm) {@link ConfirmCommand} (confirm action)<br>
 * - New game (new) {@link NewGameCommand} (start a new game)<br>
 * - Load game (load) {@link LoadGameCommand} (load a saved game)<br>
 * - Exit game (exit) {@link ExitGameCommand} (exit the game)<br>
 * - Choose character (choose [character]) {@link ChooseCharacterGameCommand} (choose a character)<br>
 * - Activate skill (skill) {@link SkillCommand} (activate character's skill)<br>
 * - Show inventory (inventory) {@link InventoryCommand} (show player's inventory)<br>
 * - Swap weapon (swap [weapon]) {@link SwapWeaponCommand} (swap player's weapon)<br>
 */
abstract class GameCommand {
    protected Game game;
    protected String[] commands;
    protected Character player;
    protected Controller controller;
    protected List<GameState> invalidStates;
    protected List<GameState> validListStates;

    protected GameCommand(Game game, String[] commands) {
        this.game = game;
        this.commands = commands;
        if (this.game != null) this.player = game.getPlayer();
        this.controller = Controller.getInstance();
    }

    public void execute() throws IllegalStateException {
        if (
            (this.validListStates != null && !this.validListStates.contains(this.controller.getState())) ||
            (this.invalidStates != null && this.invalidStates.contains(this.controller.getState()))
        )
            throw new IllegalStateException("Invalid state to execute this command");
    };
}

/**
 * Concrete command class to move the player character to the north<br>
 * @see GameCommand <br>
 * @see Coordinates.Direction <br>
 */
class MoveNorthCommand extends GameCommand {
    public MoveNorthCommand(Game game, String[] commands) {
        super(game, null);
        this.validListStates = List.of(
            GameState.MOVING,
            GameState.HEALING_ROOM,
            GameState.BOSS_DEFEATED
        );
    }

    @Override
    public void execute() throws IllegalStateException {
        super.execute();

        try {
            this.game.movePlayer(Coordinates.Direction.NORTH);
            this.game.saveLocally();
        } catch (IllegalArgumentException e) {
            System.out.println("You can't go that way!");
        }
    }
}

/**
 * Concrete command class to move the player character to the south<br>
 * @see GameCommand <br>
 * @see Coordinates.Direction <br>
 */
class MoveSouthCommand extends GameCommand {
    public MoveSouthCommand(Game game, String[] commands) {
        super(game, null);
        this.validListStates = List.of(
            GameState.MOVING,
            GameState.HEALING_ROOM,
            GameState.BOSS_DEFEATED
        );
    }

    @Override
    public void execute() throws IllegalStateException {
        super.execute();

        try {
            this.game.movePlayer(Coordinates.Direction.SOUTH);
            this.game.saveLocally();
        } catch (IllegalArgumentException e) {
            System.out.println("You can't go that way!");
        }
    }
}

/**
 * Concrete command class to move the player character to the west<br>
 * @see GameCommand <br>
 * @see Coordinates.Direction <br>
 */
class MoveWestCommand extends GameCommand {
    public MoveWestCommand(Game game, String[] commands) {
        super(game, null);
        this.validListStates = List.of(
            GameState.MOVING,
            GameState.HEALING_ROOM,
            GameState.BOSS_DEFEATED
        );
    }

    @Override
    public void execute() throws IllegalStateException {
        super.execute();
        
        try {
            this.game.movePlayer(Coordinates.Direction.WEST);
            this.game.saveLocally();
        } catch (IllegalArgumentException e) {
            System.out.println("You can't go that way!");
        }
    }
}

/**
 * Concrete command class to move the player character to the east<br>
 * @see GameCommand <br>
 * @see Coordinates.Direction <br>
 */
class MoveEastCommand extends GameCommand {
    public MoveEastCommand(Game game, String[] commands) {
        super(game, null);
        this.validListStates = List.of(
            GameState.MOVING,
            GameState.HEALING_ROOM,
            GameState.BOSS_DEFEATED
        );
    }

    @Override
    public void execute() throws IllegalStateException {
        super.execute();
        
        try {
            this.game.movePlayer(Coordinates.Direction.EAST);
            this.game.saveLocally();
        } catch (IllegalArgumentException e) {
            System.out.println("You can't go that way!");
        } 
    }
}

/**
 * Concrete command class to attack the enemy in the room<br>
 * @see GameCommand <br>
 */
class AttackCommand extends GameCommand {
    public AttackCommand(Game game, String[] commands) {
        super(game, null);
        this.validListStates = List.of(
            GameState.FIGHTING_BOSS,
            GameState.FIGHTING_MOB
        );
    }

    @Override
    public void execute() throws IllegalStateException {
        super.execute();
        Controller.clearConsole(100);
        this.game.playerAttack();
    }
}

/**
 * Concrete command class to activate the Character's skill<br>
 * @see GameCommand <br>
 */
class SkillCommand extends GameCommand {
    public SkillCommand(Game game, String[] commands) {
        super(game, null);
        this.validListStates = List.of(
            GameState.FIGHTING_BOSS,
            GameState.FIGHTING_MOB
        );
    }

    @Override
    public void execute() throws IllegalStateException {
        super.execute();
        this.game.usePlayerSkill();
    }
}

/**
 * Concrete command class to show the inventory<br>
 * @see GameCommand <br>
 */
class InventoryCommand extends GameCommand {
    public InventoryCommand(Game game, String[] commands) {
        super(game, null);
        this.invalidStates = List.of(
            GameState.MERCHANT_SHOPPING,
            GameState.MAIN_MENU
        );
    }

    @Override
    public void execute() throws IllegalStateException {
        super.execute();
        this.game.printInventory();
    }
}

/**
 * Concrete command class to consume an item from the inventory<br>
 * @see GameCommand <br>
 */
class UseItemCommand extends GameCommand {
    public UseItemCommand(Game game, String[] commands) {
        super(game, commands);
        this.invalidStates = List.of(
            GameState.MERCHANT_SHOPPING,
            GameState.SMITH_FORGING,
            GameState.MAIN_MENU
        );
    }

    @Override
    public void execute() throws IllegalStateException {
        super.execute();
        if (!Controller.awaitConfirmation()) return;
        
        try {
            this.game.useItem(Integer.parseInt(commands[1]));
        } catch (NumberFormatException e) {
            System.out.println("Please insert a valid number");
        }
    }
}

/**
 * Concrete command class to swap the player's weapon<br>
 * @see GameCommand <br>
 */
class SwapWeaponCommand extends GameCommand {
    public SwapWeaponCommand(Game game, String[] commands) {
        super(game, commands);
        this.invalidStates = List.of(
            GameState.MERCHANT_SHOPPING,
            GameState.SMITH_FORGING,
            GameState.MAIN_MENU
        );
    }

    @Override
    public void execute() throws IllegalStateException {
        super.execute();
        
        try {
            this.game.getPlayer().swapWeapon(Integer.parseInt(commands[1]));
        } catch (NumberFormatException e) {
            System.out.println("Please insert a valid number");
        }
    }
}

/**
 * Concrete command class to drop an item<br>
 * @see GameCommand <br>
 */
class DropItemCommand extends GameCommand {
    public DropItemCommand(Game game, String[] commands) {
        super(game, commands);
        this.invalidStates = List.of(
            GameState.MERCHANT_SHOPPING,
            GameState.SMITH_FORGING,
            GameState.MAIN_MENU
        );
    }

    @Override
    public void execute() throws IllegalStateException {
        super.execute();

        if (!Controller.awaitConfirmation()) return;
        this.game.dropItem(Integer.parseInt(commands[1]));
    }
}

/**
 * Concrete command class to print the game seed<br>
 * @see GameCommand <br>
 */
class PrintSeedCommand extends GameCommand {
    public PrintSeedCommand(Game game, String[] commands) {
        super(game, null);
        this.invalidStates = List.of(
            GameState.MERCHANT_SHOPPING,
            GameState.SMITH_FORGING,
            GameState.MAIN_MENU
        );
    }

    @Override
    public void execute() throws IllegalStateException {
        super.execute();
        System.out.println("The game seed is: " + this.game.getSeed());
    }
}

/**
 * Concrete command class to quit the current game, saving the game<br>
 * @see GameCommand <br>
 */
class QuitCommand extends GameCommand {
    public QuitCommand(Game game, String[] commands) {
        super(game, null);
        this.invalidStates = List.of(
            GameState.MAIN_MENU
        );
    }

    @Override
    public void execute() throws IllegalStateException {
        super.execute();
        
        if (!Controller.awaitConfirmation()) return;
        this.game.uploadToCloud();
        this.controller.setGame(null);
        this.controller.setState(GameState.MAIN_MENU);
    }
}

/**
 * Concrete command class to return to the previous interaction status, using the gamestate's Stack<br>
 * @see GameCommand <br>
 */
class PreviousStateCommand extends GameCommand {
    public PreviousStateCommand(Game game, String[] commands) {
        super(game, null);
        this.validListStates = List.of(
            GameState.CHOOSING_CHARACTER,
            GameState.INV_MANAGEMENT,
            GameState.MERCHANT_SHOPPING, 
            GameState.SMITH_FORGING,
            GameState.MAIN_MENU
        );
    }

    @Override
    public void execute() throws IllegalStateException {
        super.execute();
        this.controller.goBackState();
    }
}

/**
 * Concrete command class to print the game score<br>
 * @see GameCommand <br>
 */
class PrintScoreCommand extends GameCommand {
    public PrintScoreCommand(Game game, String[] commands) {
        super(game, null);
        this.invalidStates = List.of(
            GameState.MAIN_MENU
        );
    }

    @Override
    public void execute() throws IllegalStateException {
        super.execute();
        System.out.println("The game score is: " + this.game.getScore());
    }
}

/**
 * Concrete command class to display the available commands
 * @see GameCommand
 */
class HelpCommand extends GameCommand {
    public HelpCommand() {
        super(null, null);
        this.invalidStates = List.of(
            GameState.MAIN_MENU
        );
    }

    @Override
    public void execute() {
        switch (this.controller.getState()) {
            case MAIN_MENU:
                System.out.println("Commands: new, load, exit");
                break;
            case CHOOSING_CHARACTER:
                System.out.println("Choose a character: default, cheater, data thief, mecha knight, neo samurai");
                break;
            case MOVING:
                System.out.println("Commands: gn, gs, gw, ge, return, inventory, status, map, score, seed, quit");
                break;
            case INV_MANAGEMENT:
                System.out.println("Commands: use, drop, back");
                break;
            case MERCHANT_SHOPPING:
                System.out.println("Commands: buy, back");
                break;
            case SMITH_FORGING:
                System.out.println("Commands: give (to upgrade), back");
                break;
            case FIGHTING_MOB:
                System.out.println("Commands: attack, skill, inventory, use, drop");
                break;
            case FIGHTING_BOSS:
                System.out.println("Commands: attack, skill, inventory, use, drop");
                break;
            case LOOTING_ROOM:
                System.out.println("Commands: confirm, inventory, use, drop");
                break;
            case HEALING_ROOM:
                System.out.println("Commands: merchant, smith, inventory, use, drop");
                break;

            default:
                break;
        }
        
    }
}

/**
 * Concrete command class to return to the starting room
 * @see GameCommand
 */
class ReturnCommand extends GameCommand {
    public ReturnCommand(Game game, String[] commands) {
        super(game, null);
        this.validListStates = List.of(
            GameState.MOVING,
            GameState.MAIN_MENU
        );
    }

    @Override
    public void execute() throws IllegalStateException {
        super.execute();
        this.game.returnToStart();
    }
}
/**
 * Concrete command class to show player status
 * @see GameCommand
 */
class PrintStatusCommand extends GameCommand {
    public PrintStatusCommand(Game game, String[] commands) {
        super(game, null);
        this.invalidStates = List.of(
            GameState.MERCHANT_SHOPPING,
            GameState.SMITH_FORGING,
            GameState.MAIN_MENU
        );
    }

    @Override
    public void execute() throws IllegalStateException {
        super.execute();
        this.game.printPlayerStatus();
    }
}

/**
 * Concrete command class to print the game map, showing visited rooms<br>
 * @see GameCommand <br>
 */
class PrintMapCommand extends GameCommand {
    public PrintMapCommand(Game game, String[] commands) {
        super(game, null);
        this.invalidStates = List.of(
            GameState.MERCHANT_SHOPPING,
            GameState.SMITH_FORGING
        );
    }

    @Override
    public void execute() throws IllegalStateException {
        super.execute();
        this.game.printMap();
    }
}

/**
 * Concrete command class to interact with the merchant
 *  @see GameCommand
 */
class MerchantCommand extends GameCommand {
    public MerchantCommand(Game game, String[] commands) {
        super(game, commands);
        this.validListStates = List.of(
            GameState.HEALING_ROOM
        );
    }

    @Override
    public void execute() throws IllegalStateException {
        super.execute();
        
        Controller.clearConsole(100);
        HealingRoom room = (HealingRoom) this.game.getCurrentRoom();
        Merchant merchant = (Merchant) room.getFriendlyEntity(Merchant.NAME);
        if (merchant != null) merchant.interact(this.player);
    }
}

/**
 * Concrete command class to buy an item from the merchant
 * @see GameCommand
 */
class BuyCommand extends GameCommand {
    public BuyCommand(Game game, String[] commands) {
        super(game, commands);
        this.invalidStates = List.of(
            GameState.MAIN_MENU
        );
    }

    @Override
    public void execute() throws IllegalStateException {
        super.execute();
        
        if (!Controller.awaitConfirmation()) return;

        HealingRoom room = (HealingRoom) this.game.getCurrentRoom();
        Merchant merchant = (Merchant) room.getFriendlyEntity(Merchant.NAME);
        
        try {
            if (merchant != null) merchant.buy(Integer.parseInt(this.commands[1]));
        } catch (NumberFormatException e) {
            System.out.println("Please enter a valid number");
        }
    }
}

/**
 * Concrete command class to interact with the smith
 * @see GameCommand
 */
class SmithCommand extends GameCommand {
    public SmithCommand(Game game, String[] commands) {
        super(game, null);
        this.validListStates = List.of(
            GameState.HEALING_ROOM
        );
    }

    @Override
    public void execute() throws IllegalStateException {
        super.execute();

        Controller.clearConsole(100);
        HealingRoom room = (HealingRoom) this.game.getCurrentRoom();
        Smith smith = (Smith) room.getFriendlyEntity(Smith.NAME);
        if (smith != null) smith.interact(this.game.getPlayer());
    }
}

/**
 * Concrete command class to give an item to an NPC
 * @see GameCommand
 */
class GiveCommand extends GameCommand {
    public GiveCommand(Game game, String[] commands) {
        super(game, commands);
        this.invalidStates = List.of(
            GameState.MAIN_MENU
        );
    }

    @Override
    public void execute() throws IllegalStateException {
        super.execute();
        
        if (this.commands.length != 3) {
            System.out.println("Invalid command. Correct Syntax: give [npc] [item]");
            return;
        }

        this.game.giveItem(commands[1], commands[2]);
    }
}

/**
 * Concrete command class to start a new game
 * @see GameCommand
 */
class NewGameCommand extends GameCommand {
    public NewGameCommand(Game game, String[] commands) {
        super(game, null);
        this.validListStates = List.of(
            GameState.MAIN_MENU
        );
    }

    @Override
    public void execute() throws IllegalStateException {
        super.execute();
        Controller.clearConsole(100);
        this.controller.newGame();
        System.out.println("Choose your starting character: 1.BasePlayer, 2.Cheater, 3.DataThief, 4.MechaKnight, 5.NeoSamurai");
    }
}

/**
 * Concrete command class to load a game
 * @see GameCommand
 */
class LoadGameCommand extends GameCommand {
    public LoadGameCommand(Game game, String[] commands) {
        super(game, null);
        this.validListStates = List.of(
            GameState.MAIN_MENU
        );
    }

    // TODO: actually load game/give choice to user
    @Override
    public void execute() throws IllegalStateException {
        super.execute();
        Controller.clearConsole(100);
        this.controller.loadGame();
    }
}

/**
 * Concrete command class to exit the game
 * @see GameCommand
 */
class ExitGameCommand extends GameCommand {
    public ExitGameCommand(Game game, String[] commands) {
        super(game, null);
        this.validListStates = List.of(
            GameState.MAIN_MENU
        );
    }

    @Override
    public void execute() throws IllegalStateException {
        super.execute();
        Controller.clearConsole(100);
        this.controller.setState(GameState.EXIT);
    }
}

/**
 * Concrete command class to choose the starting character {@link Character}, {@link BasePlayer}, {@link Hacker}, {@link DataThief}, {@link MechaKnight}, {@link NeoSamurai}
 * @see GameCommand
 */
class ChooseCharacterGameCommand extends GameCommand {
    public ChooseCharacterGameCommand(Game game, String[] commands) {
        super(game, commands);
        this.validListStates = List.of(
            GameState.CHOOSING_CHARACTER
        );
    }

    @Override
    public void execute() throws IllegalStateException {
        super.execute();

        System.out.println("\n" + switch(this.commands[0]) {
            case "1" -> BasePlayer.getDetailedInfo();
            case "2"-> Hacker.getDetailedInfo();
            case "3" -> DataThief.getDetailedInfo();
            case "4" -> MechaKnight.getDetailedInfo();
            case "5" -> NeoSamurai.getDetailedInfo();
            default -> "Invalid character choice. Please choose a valid character.";
        } + "\n");

        if (!Controller.awaitConfirmation()) {
            Controller.clearConsole(100);
            System.out.println("Choose your starting character: 1.BasePlayer, 2.Cheater, 3.DataThief, 4.MechaKnight, 5.NeoSamurai");
            return;
        }

        Controller.clearConsole(100);

        CharacterFactory factory = this.controller.characterFactory(commands[0]);
        this.controller.setPlayer(factory.create());
        this.controller.setState(GameState.MOVING);

        System.out.println(this.controller.getPlayer() + this.controller.getPlayer().getASCII());
    }
}

/**
 * Invoker class to execute the game commands
 */
class GCInvoker {
    private GameCommand command;

    public void setCommand(GameCommand command) {
        this.command = command;
    }

    public void executeCommand() {
        if (this.command == null) {
            System.out.println("Invalid command");
            return;
        }
        
        this.command.execute();
    }
}

/** Factory class to create different Characters based on inital user's choice */
abstract class CharacterFactory {
    Coordinates spawnCoordinates;

    public CharacterFactory(Coordinates spawnCoordinates) {
        this.spawnCoordinates = spawnCoordinates;
    }

    /**
     * This method creates a new Character based on the factory type. <br>
     * It calls the abstract method createCharacter() which is implemented by the concrete factories.<br>
     * @return Character subclass object created by the concrete factories.
     */
    public Character create() {
        Character character = this.createCharacter();
        return character;
    }
    
    /**
     * Abstract method to be implemented by the concrete factories to actually create the specific Character subclass object.<br>
     * @return Character subclass object to be instantiated by the concrete factories.
     */
    public abstract Character createCharacter();
}

/**
 * Concrete factory class to create a {@link BasePlayer} character
*/
class BasePlayerFactory extends CharacterFactory {
    public BasePlayerFactory(Coordinates spawnCoordinates) {
        super(spawnCoordinates);
    }

    @Override
    public Character createCharacter() {
        return new BasePlayer(this.spawnCoordinates);
    }
}

/**
 * Concrete factory class to create a {@link Hacker} character
*/
class HackerFactory extends CharacterFactory {
    public HackerFactory(Coordinates spawnCoordinates) {
        super(spawnCoordinates);
    }

    @Override
    public Character createCharacter() {
        return new Hacker(this.spawnCoordinates);
    }
}

/**
 * Concrete factory class to create a {@link DataThief} character
*/
class DataThiefFactory extends CharacterFactory {
    public DataThiefFactory(Coordinates spawnCoordinates) {
        super(spawnCoordinates);
    }

    @Override
    public Character createCharacter() {
        return new DataThief(this.spawnCoordinates);
    }
}

/**
 * Concrete factory class to create a {@link MechaKnight} character
*/
class MechaKnightFactory extends CharacterFactory {
    public MechaKnightFactory(Coordinates spawnCoordinates) {
        super(spawnCoordinates);
    }

    @Override
    public Character createCharacter() {
        return new MechaKnight(this.spawnCoordinates);
    }
}

/**
 * Concrete factory class to create a {@link NeoSamurai} character
 */
class NeoSamuraiFactory extends CharacterFactory {
    public NeoSamuraiFactory(Coordinates spawnCoordinates) {
        super(spawnCoordinates);
    }

    @Override
    public Character createCharacter() {
        return new NeoSamurai(this.spawnCoordinates);
    }
}

/**
 * Controller façade class to handle the game state and user input<br>
 * It uses the Singleton pattern to ensure only one instance of the Controller class is created<br>
 * It uses the Command pattern to handle the different game commands<br>
 * It uses the Factory pattern to create different Characters based on the user's choice<br>
 * @see Game
 */
public class Controller {
    /** Singleton Controller unique instance */
    private static Controller instance;
    private static Scanner scanner;
    private Game game;
    private Character player;
    private MusicPlayer musicPlayer;
    /**
     * Stack to keep track of the game states<br>
     */
    private Stack<GameState> history;

    private Controller() {
        Controller.scanner = new Scanner(System.in);
        this.history = new Stack<GameState>();
        this.history.push(GameState.MAIN_MENU);
    }

    public static boolean awaitConfirmation() {
        System.out.println("Are you sure? (yes/no)");

        String input = Controller.scanner.nextLine();
        return input.matches("(yes|y)");
    }

    /** 
     * Singleton pattern to ensure only one instance of the Controller class is created
     * @return the unique instance of the Controller class
     */
    public static Controller getInstance() {
        if (instance == null) instance = new Controller();
        return instance;
    }

    public GameState getState() {
        return this.history.peek();
    }

    public void setState(GameState state) {
        this.history.push(state);
    }

    public void goBackState() {
        this.history.pop();
    }

    public Character getPlayer() {
        return this.player;
    }

    public void setPlayer(Character player) {
        this.player = player;
        this.game.setPlayer(player);
        this.player.setCurrentLevel(this.game.getLevel());
    }

    public void printMap() {
        if (this.game != null) this.game.printMap();
    }
    
    public void printMapAndArt(String asciiArt){
        String[] asciiArtLines = asciiArt.split("\n");
        String[] mapLines = this.game.generateMapLines();
    
        int maxLines = Math.max(asciiArtLines.length, mapLines.length);
        int maxRows = asciiArtLines[0].length();
        for (int i = 1; i < asciiArtLines.length; i++)
            if (asciiArtLines[i].length() > maxRows) maxRows = asciiArtLines[i].length();
    
        for (int i = 0; i < maxLines; i++) {
            String asciiArtLine = i < asciiArtLines.length ? asciiArtLines[i] : "";
            String filler = " ".repeat(maxRows - asciiArtLine.length());
            String mapLine = (0 < (i - 5) && (i - 5) < mapLines.length) ? mapLines[i - 5] : "";
    
            System.out.println(asciiArtLine + filler + "\t\t\t\t\t\t\t" + mapLine);
        }
    }

    public void setGame(Game game) {
        this.game = game;
    }

    /*
     * TODO
     * Implement loading pre-defined configurations from JSON file (and maybe random map generation?)
     */
    public void newGame() {
        ArrayList<Level> map = GameData.deserializeMap(Constants.MAP);
        
        // ArrayList<Level> map = GameData.deserializeMapFromFile("map.json");
        Game game = new Game(map);
        game.setSeed(1);

        this.setState(GameState.CHOOSING_CHARACTER);
        this.setGame(game);
    }

    /**
     * TODO
     * Loads the game from the cloud, then deserializes the JSON data to create a new Game object
     * @see GameData
     */
    public void loadGame() {
        GameData gameData = GameData.deserializeJSON("{}");
        this.setGame(gameData.getGame());
        this.setState(GameState.MOVING);
    }

    /**
     * Returns the command object based on the user input
     * @param command the user input
     * @return the command object to be executed
     */
    private GameCommand getCommand(String[] commands) {
        Map<String, Supplier<GameCommand>> commandMap = new HashMap<>(
            Map.ofEntries(
                Map.entry("new", () -> new NewGameCommand(this.game, commands)),
                Map.entry("load", () -> new LoadGameCommand(this.game, commands)),
                Map.entry("exit", () -> new ExitGameCommand(this.game, commands)),
                Map.entry("gn", () -> new MoveNorthCommand(this.game, commands)),
                Map.entry("gs", () -> new MoveSouthCommand(this.game, commands)),
                Map.entry("gw", () -> new MoveWestCommand(this.game, commands)),
                Map.entry("ge", () -> new MoveEastCommand(this.game, commands)),
                Map.entry("help", () -> new HelpCommand()),
                Map.entry("atk", () -> new AttackCommand(this.game, commands)),
                Map.entry("skill", () -> new SkillCommand(this.game, commands)),
                Map.entry("inv", () -> new InventoryCommand(this.game, commands)),
                Map.entry("use", () -> new UseItemCommand(this.game, commands)),
                Map.entry("drop", () -> new DropItemCommand(this.game, commands)),
                Map.entry("swap", () -> new SwapWeaponCommand(this.game, commands)),
                Map.entry("return", () -> new ReturnCommand(this.game, commands)),
                Map.entry("status", () -> new PrintStatusCommand(this.game, commands)),
                Map.entry("score", () -> new PrintScoreCommand(this.game, commands)),
                Map.entry("map", () -> new PrintMapCommand(this.game, commands)),
                Map.entry("seed", () -> new PrintSeedCommand(this.game, commands)),
                Map.entry("quit", () -> new QuitCommand(this.game, commands)),
                Map.entry("back", () -> new PreviousStateCommand(this.game, commands)),
                Map.entry("merchant", () -> new MerchantCommand(this.game, commands)),
                Map.entry("buy", () -> new BuyCommand(this.game, commands)),
                Map.entry("smith", () -> new SmithCommand(this.game, commands)),
                Map.entry("give", () -> new GiveCommand(this.game, commands))
            )
        );

        if (commands[0].matches("\\d+")) return new ChooseCharacterGameCommand(this.game, commands);
        
        Supplier<GameCommand> command = commandMap.get(commands[0]);
        if (command != null) return command.get();
        return null;
    }

    /**
     * Returns the CharacterFactory object based on the user input
     * @param character the user's choice of character
     * @return the CharacterFactory object to create the Character subclass object
     */
    public CharacterFactory characterFactory(String character) {
        Map<String, Supplier<CharacterFactory>> characterMap = new HashMap<>(
            Map.of(
                "1", () -> new BasePlayerFactory(this.game.getLevel().getStartRoom().getCoordinates()),
                "2", () -> new HackerFactory(this.game.getLevel().getStartRoom().getCoordinates()),
                "3", () -> new DataThiefFactory(this.game.getLevel().getStartRoom().getCoordinates()),
                "4", () -> new MechaKnightFactory(this.game.getLevel().getStartRoom().getCoordinates()),
                "5", () -> new NeoSamuraiFactory(this.game.getLevel().getStartRoom().getCoordinates())
            )
        );
        
        return characterMap.get(character).get();
    }

    /**
     * Handles the user input based on the current game state
     * @param input the user input
     * @see GameState
     */
    public void handleUserInput(String input) {
        String[] commands = input.split(" ");
        if (commands.length == 0) {
            System.out.println("Please enter a command or write \"help\" for a list of commands");
            return;
        }

        GCInvoker invoker = new GCInvoker();
        GameCommand command = this.getCommand(commands);
        invoker.setCommand(command);

        try {
            invoker.executeCommand();
        } catch (IllegalStateException e) {
            System.out.println("You can't do that now!");
        }
    }

    @JsonIgnore
    public String getAvailableCommands() {
        return switch (this.getState()) {
            case MAIN_MENU -> "[new] - [load] - [exit]";
            case CHOOSING_CHARACTER -> "[1.BasePlayer] - [2.Cheater] - [3.DataThief] - [4.MechaKnight] - [5.NeoSamurai]";
            case FIGHTING_BOSS, FIGHTING_MOB -> "[atk] - [skill] - [use] - [inv]";
            case LOOTING_ROOM -> "[inv] - [use *number*] - [drop *number*] - " + this.game.getAvailableDirections();
            case MOVING -> this.game.getAvailableDirections();
            case INV_MANAGEMENT -> "[use] - [drop] - [swap] - [back]";
            case MERCHANT_SHOPPING -> "[buy *number*] - [back]";
            case SMITH_FORGING -> "[give smith weaponshard] - [back]";
            case BOSS_DEFEATED -> "[inv] - [gn, gs, gw ge to change floor]";
            case HEALING_ROOM -> "[merchant] - [smith] - [inv] - [use] - [drop] - [gn, gs, gw ge to change floor]";
            default -> "";
        };
    }

    public static void setConsoleEncoding() {
        try {
            if (System.getProperty("os.name").startsWith("Windows"))
                new ProcessBuilder("cmd", "/c", "chcp", "65001").inheritIO().start();
            else
                new ProcessBuilder("bash", "-c", "export LANG=en_US.UTF-8").inheritIO().start();
        } catch (IOException e) {
            System.out.println("Error setting UTF-8 encoding to support special characters");
            e.printStackTrace();
        }
    }

    public static void clearConsole(int delay) {
        try {
            Thread.sleep(delay);
            if (System.getProperty("os.name").startsWith("Windows")) 
                new ProcessBuilder("cmd", "/c", "cls").inheritIO().start().waitFor();
            else 
                new ProcessBuilder("bash", "-c", "clear").inheritIO().start().waitFor();
        } catch (IOException | InterruptedException e) {
            System.out.println("Error clearing console");
            e.printStackTrace();
        }
    }

    /**
     * Main game loop
     */
    public void run() {
        Controller.setConsoleEncoding();
        Controller.clearConsole(100);

        this.musicPlayer = new MusicPlayer(
            Constants.INTRO_WAV_FILE_PATH,
            new Runnable() {
                @Override
                public void run() {
                    musicPlayer.setNewFile(Constants.LOOP_WAV_FILE_PATH);
                    musicPlayer.playMusic(true);
                }
            }
        );

        this.musicPlayer.increaseVolume(-20.0f);
        this.musicPlayer.playMusic(false);

        System.out.println(Constants.GAME_TITLE);

        while (this.getState() != GameState.EXIT) {
            if (this.game != null) {
                this.player = this.game.getPlayer();
                if (this.player != null) {
                    System.out.print(
                        "You: \n" + 
                        "HP:   " + this.player.getHpBar() + " " + this.player.getHp() + "/" + this.player.getMaxHp() + 
                        "\nMana: " + this.player.getManaBar() + " " + this.player.getMana() + "/" + this.player.getMaxMana() + "\n\n"
                    );

                    // this.game.printMap();
                    System.out.println("What to do?\n" + this.getAvailableCommands() + " (write \"help\" for the complete list of commands): ");
                }
            }

            String input = Controller.scanner.nextLine();
            this.handleUserInput(input);
        }

        Controller.scanner.close();
    }
}
