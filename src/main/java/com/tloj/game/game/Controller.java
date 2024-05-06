package com.tloj.game.game;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;
import java.util.Scanner;
import java.util.function.Supplier;

import com.tloj.game.entities.Character;
import com.tloj.game.rooms.HostileRoom;
import com.tloj.game.rooms.Room;
import com.tloj.game.rooms.RoomType;
import com.tloj.game.entities.BasePlayer;
import com.tloj.game.utilities.Coordinates;
import com.tloj.game.utilities.GameState;


/**
 * Command pattern implementation to handle the different game commands<br>
 * Available commands are:<br>
 * - gn {@link MoveNorthCommand}<br>
 * - gs {@link MoveSouthCommand}<br>
 * - gw {@link MoveWestCommand}<br>
 * - ge {@link MoveEastCommand}<br>
 * - attack {@link AttackCommand}<br>
 * - save {@link SaveGameCommand}<br>
 * - help {@link HelpCommand}<br>
 */
abstract class GameCommand {
    protected Game game;
    protected String[] commands;
    protected Character player;

    protected GameCommand(Game game, String[] commands) {
        this.game = game;
        this.commands = commands;
        if (this.game != null) this.player = game.getPlayer();
    }

    public abstract void execute();
}

/**
 * Concrete command class to move the player character to the north<br>
 * @see GameCommand <br>
 * @see Coordinates.Direction <br>
 */
class MoveNorthCommand extends GameCommand {
    public MoveNorthCommand(Game game, String[] commands) {
        super(game, null);        
    }

    @Override
    public void execute() {
        try {
            this.game.movePlayer(Coordinates.Direction.NORTH);
            this.game.save();
        } catch (IllegalArgumentException e) {
            e.printStackTrace();
        } catch (IllegalStateException e) {
            e.printStackTrace();
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
    }

    @Override
    public void execute() {
        try {
            this.game.movePlayer(Coordinates.Direction.SOUTH);
            this.game.save();
        } catch (IllegalArgumentException e) {
            e.printStackTrace();
        } catch (IllegalStateException e) {
            e.printStackTrace();
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
    }

    @Override
    public void execute() {
        try {
            this.game.movePlayer(Coordinates.Direction.WEST);
            this.game.save();
        } catch (IllegalArgumentException e) {
            e.printStackTrace();
        } catch (IllegalStateException e) {
            e.printStackTrace();
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
    }

    @Override
    public void execute() {
        try {
            this.game.movePlayer(Coordinates.Direction.EAST);
            this.game.save();
        } catch (IllegalArgumentException e) {
            e.printStackTrace();
        } catch (IllegalStateException e) {
            e.printStackTrace();
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
    }

    @Override
    public void execute() {
        /* 
        * TODO
        * Attack the enemy in the room
        */ 
    }
}

/**
 * Concrete command class to activate the Character's skill<br>
 * @see GameCommand <br>
 */
class SkillCommand extends GameCommand {
    public SkillCommand(Game game, String[] commands) {
        super(game, null);
    }

    @Override
    public void execute() {
        /* 
        * TODO
        * Activate the skill
        */ 
    }
}

/**
 * Concrete command class to show the inventory<br>
 * @see GameCommand <br>
 */
class InventoryCommand extends GameCommand {
    public InventoryCommand(Game game, String[] commands) {
        super(game, null);
    }

    @Override
    public void execute() {
        this.player.getInventory().forEach(item -> {
            System.out.println(item);
        });
    }
}

/**
 * Concrete command class to consume an item from the inventory<br>
 * @see GameCommand <br>
 */
class UseItemCommand extends GameCommand {
    public UseItemCommand(Game game, String[] commands) {
        super(game, commands);
    }

    @Override
    public void execute() {
        /* 
        * TODO
        * Consume an item from the inventory
        */ 
    }
}

/**
 * Concrete command class to drop an item<br>
 * @see GameCommand <br>
 */
class DropItemCommand extends GameCommand {
    public DropItemCommand(Game game, String[] commands) {
        super(game, commands);
    }

    @Override
    public void execute() {
        /* 
        * TODO
        * Consume an item from the inventory
        */ 
    }
}

/**
 * Concrete command class to print the game seed<br>
 * @see GameCommand <br>
 */
class PrintSeedCommand extends GameCommand {
    public PrintSeedCommand(Game game, String[] commands) {
        super(game, null);
    }

    @Override
    public void execute() {
        System.out.println("The game seed is: " + this.game.getSeed());
    }
}

/**
 * Concrete command class to print the game seed<br>
 * @see GameCommand <br>
 */
class PrintMapCommand extends GameCommand {
    public PrintMapCommand(Game game, String[] commands) {
        super(game, null);
    }

    @Override
    public void execute() {
        /* 
        * TODO
        * Print the game map
        */ 
    }
}

/**
 * Concrete command class to print the game seed<br>
 * @see GameCommand <br>
 */
class PrintStatsCommand extends GameCommand {
    public PrintStatsCommand(Game game, String[] commands) {
        super(game, null);
    }

    @Override
    public void execute() {
        /* 
        * TODO
        * Print the game and player stats
        */ 
    }
}

/**
 * Concrete command class to quit the current game<br>
 * @see GameCommand <br>
 */
class QuitCommand extends GameCommand {
    public QuitCommand(Game game, String[] commands) {
        super(game, null);
    }

    @Override
    public void execute() {
        /* 
        * TODO
        * Quit the current game
        */ 
        this.game.save();

        Controller controller = Controller.getInstance();
        controller.setState(GameState.MAIN_MENU);
    }
}

/**
 * Concrete command class to print the game seed<br>
 * @see GameCommand <br>
 */
class ScoreCommand extends GameCommand {
    public ScoreCommand(Game game, String[] commands) {
        super(game, null);
    }

    @Override
    public void execute() {
        /* 
        * TODO
        * Show the player's current score
        */ 
    }
}

/**
 * Concrete command class to display the available commands
 */
class HelpCommand extends GameCommand {
    public HelpCommand() {
        super(null, null);
    }

    @Override
    public void execute() {
        System.out.println("Commands: gn, gs, gw, ge, attack, use, save, exit");
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
        if (this.command == null) return;
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
 * Concrete factory class to create a Base Player character
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
 * Concrete factory class to create a Cheater character
*/
class CheaterFactory extends CharacterFactory {
    public CheaterFactory(Coordinates spawnCoordinates) {
        super(spawnCoordinates);
    }

    @Override
    public Character createCharacter() {
        return new BasePlayer(this.spawnCoordinates);
    }
}

/**
 * Concrete factory class to create a Data Thief character
*/
class DataThiefFactory extends CharacterFactory {
    public DataThiefFactory(Coordinates spawnCoordinates) {
        super(spawnCoordinates);
    }

    @Override
    public Character createCharacter() {
        return new BasePlayer(this.spawnCoordinates);
    }
}

/**
 * Concrete factory class to create a Mecha Knight character
*/
class MechaKnightFactory extends CharacterFactory {
    public MechaKnightFactory(Coordinates spawnCoordinates) {
        super(spawnCoordinates);
    }

    @Override
    public Character createCharacter() {
        return new BasePlayer(this.spawnCoordinates);
    }
}

/**
 * Concrete factory class to create a Neo Samurai character
 */
class NeoSamuraiFactory extends CharacterFactory {
    public NeoSamuraiFactory(Coordinates spawnCoordinates) {
        super(spawnCoordinates);
    }

    @Override
    public Character createCharacter() {
        return new BasePlayer(this.spawnCoordinates);
    }
}

/**
 * Controller class to handle the game state and user input<br>
 * It uses the Singleton pattern to ensure only one instance of the Controller class is created<br>
 * It uses the Command pattern to handle the different game commands<br>
 * It uses the Factory pattern to create different Characters based on the user's choice<br>
 */
public class Controller {
    /** Singleton Controller unique instance */
    private static Controller instance;
    private Game game;
    private Character player;
    private GameState state;

    private Controller() {}

    public static Controller getInstance() {
        if (instance == null) instance = new Controller();
        return instance;
    }

    public GameState getState() {
        return this.state;
    }

    public void setState(GameState state) {
        this.state = state;
    }

    public Character getPlayer() {
        return this.player;
    }

    public void setPlayer(Character player) {
        this.player = player;
        this.game.setPlayer(player);
    }

    public void setGame(Game game) {
        this.game = game;
    }

    /*
     * TODO
     * Implement loading pre-defined configurations from JSON file (and maybe random map generation?)
     */
    public void newGame() {
        ArrayList<ArrayList<ArrayList<Room>>> map = new ArrayList<ArrayList<ArrayList<Room>>>();

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

        this.setGame(new Game(map));
        this.setState(GameState.CHOOSING_CHARACTER);
    }

    /**
     * Loads the game from the cloud, then deserializes the JSON data to create a new Game object
     * @see GameData
     */
    public void loadGame() {
        GameData gameData = GameData.deserializeJSON("{}");
        this.setGame(new Game(gameData));
        this.state = GameState.MOVING;
    }

    public void exitGame() {
        this.state = GameState.EXIT;
    }

    public void displayHelp() {
        System.out.println("Commands: new, load, exit");
    }

    /**
     * Returns the command object based on the user input
     * @param command the user input
     * @return the command object to be executed
     */
    private GameCommand getCommand(String[] commands) {
        Map<String, Supplier<GameCommand>> commandMap = new HashMap<>(
            Map.of(
                "gn", () -> new MoveNorthCommand(this.game, commands),
                "gs", () -> new MoveSouthCommand(this.game, commands),
                "gw", () -> new MoveWestCommand(this.game, commands),
                "ge", () -> new MoveEastCommand(this.game, commands),
                "help", () -> new HelpCommand(),
                "attack", () -> new AttackCommand(this.game, commands)
            )
        );
        
        return commandMap.get(commands[0]).get();
    }

    /**
     * Returns the CharacterFactory object based on the user input
     * @param character the user's choice of character
     * @return the CharacterFactory object to create the Character subclass object
     */
    private CharacterFactory characterFactory(String character) {
        Map<String, Supplier<CharacterFactory>> characterMap = new HashMap<>(
            Map.of(
                "default", () -> new BasePlayerFactory(this.game.getLevel().getStartRoom().getCoordinates()),
                "cheater", () -> new CheaterFactory(this.game.getLevel().getStartRoom().getCoordinates()),
                "data thief", () -> new DataThiefFactory(this.game.getLevel().getStartRoom().getCoordinates()),
                "mecha knight", () -> new MechaKnightFactory(this.game.getLevel().getStartRoom().getCoordinates()),
                "neo samurai", () -> new NeoSamuraiFactory(this.game.getLevel().getStartRoom().getCoordinates())
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

        GCInvoker invoker;
        switch (this.state) {
            case MAIN_MENU:
                /* New, load, exit game */
                switch (commands[0]) {
                    case "new":
                        this.newGame();
                        break;
                    case "load":
                        this.loadGame();
                        break;
                    case "exit":
                        this.exitGame();
                        break;
                    case "help":
                        this.displayHelp();
                        break;
                    default:
                        System.out.println("Invalid command");
                        break;
                }

                break;  

            case CHOOSING_CHARACTER:
                CharacterFactory factory = this.characterFactory(commands[0]);
                this.setPlayer(factory.create());
                this.state = GameState.MOVING;
                break;
            
            case MOVING:
            case LOOTING_ROOM:
            case FIGHTING_MOB:
            case FIGHTING_BOSS:
                invoker = new GCInvoker();
                invoker.setCommand(this.getCommand(commands));
                invoker.executeCommand();
                break;

            default:
                break;
        }
    }

    /**
     * Main game loop to handle user input
     */
    public void run() {
        Scanner scanner = new Scanner(System.in);

        while (this.state != GameState.EXIT) {
            String input = scanner.nextLine();
            this.handleUserInput(input);
        }

        scanner.close();
    }
}
