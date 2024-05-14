package com.tloj.game.game;

import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Scanner;
import java.util.Stack;
import java.util.function.Supplier;

import com.fasterxml.jackson.annotation.JsonIgnore;
import org.fusesource.jansi.Ansi;
import org.fusesource.jansi.AnsiConsole;


import com.tloj.game.rooms.HealingRoom;
import com.tloj.game.rooms.LootRoom;
import com.tloj.game.collectables.ConsumableItem;
import com.tloj.game.collectables.Item;
import com.tloj.game.entities.Character;
import com.tloj.game.entities.characters.BasePlayer;
import com.tloj.game.entities.characters.Hacker;
import com.tloj.game.entities.characters.DataThief;
import com.tloj.game.entities.characters.MechaKnight;
import com.tloj.game.entities.characters.NeoSamurai;
import com.tloj.game.entities.npcs.Merchant;
import com.tloj.game.entities.npcs.Smith;
import com.tloj.game.utilities.ConsoleColors;
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
 * - Use item (use [number]) {@link UseItemCommand}<br>
 * - Drop item (drop [number]) {@link DropItemCommand}<br>
 * - Info item (info [number]) {@link DescribeCommand}<br>
 * - Print seed (seed) {@link PrintSeedCommand}<br>
 * - Print score (score) {@link PrintScoreCommand}<br>
 * - Quit (quit) {@link QuitCommand}<br>
 * - Back (back) {@link PreviousStateCommand} (used during complex interactions)<br>
 * - Help (help) {@link HelpCommand}<br>
 * - Return (return) {@link ReturnCommand} (used to go back to starting room)<br>
 * - Print status (status) {@link PrintStatusCommand}<br>
 * - Merchant (merchant) {@link MerchantCommand} (talk with merchant in healing room)<br>
 * - Show list (showlist) {@link ShowListCommand} (show merchant items list)<br>
 * - Buy (buy [number]) {@link BuyCommand} (buy item from merchant)<br>
 * - Smith (smith) {@link SmithCommand} (talk with smith in healing room)<br>
 * - Give (give [smith] [weaponshard]) {@link GiveCommand} (give item to npc)<br>
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
            GameState.BOSS_DEFEATED,
            GameState.LOOTING_ROOM
        );
    }

    @Override
    public void execute() throws IllegalStateException {
        super.execute();

        try {
            Controller.clearConsole();
            this.game.movePlayer(Coordinates.Direction.NORTH);
            this.game.saveLocally();
        } catch (IllegalArgumentException e) {
            System.out.println(e.getMessage());
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
            GameState.BOSS_DEFEATED,
            GameState.LOOTING_ROOM
        );
    }

    @Override
    public void execute() throws IllegalStateException {
        super.execute();

        try {
            Controller.clearConsole();
            this.game.movePlayer(Coordinates.Direction.SOUTH);
            this.game.saveLocally();
        } catch (IllegalArgumentException e) {
            System.out.println(e.getMessage());
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
            GameState.BOSS_DEFEATED,
            GameState.LOOTING_ROOM
        );
    }

    @Override
    public void execute() throws IllegalStateException {
        super.execute();
        
        try {
            Controller.clearConsole();
            this.game.movePlayer(Coordinates.Direction.WEST);
            this.game.saveLocally();
        } catch (IllegalArgumentException e) {
            System.out.println(e.getMessage());
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
            GameState.BOSS_DEFEATED,
            GameState.LOOTING_ROOM
        );
    }

    @Override
    public void execute() throws IllegalStateException {
        super.execute();
        
        try {
            Controller.clearConsole();
            this.game.movePlayer(Coordinates.Direction.EAST);
            this.game.saveLocally();
        } catch (IllegalArgumentException e) {
            System.out.println(e.getMessage());
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
        Controller.clearConsole();
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
        Controller.clearConsole();
        this.game.printInventory();
        System.out.println();
        if (!List.of(GameState.FIGHTING_BOSS, GameState.FIGHTING_MOB, GameState.HEALING_ROOM).contains(this.controller.getState()))
            this.game.printMap();
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

        if (commands.length != 2) {
            System.out.println(ConsoleColors.RED + "Invalid command. Correct Syntax: use [item]" + ConsoleColors.RESET);
            return;
        }

        if (!Controller.awaitConfirmation()) return;
        
        try {
            Controller.clearConsole();
            Item consumed = this.game.useItem(Integer.parseInt(commands[1]));

            if (consumed == null) {
                System.out.println();
                if (!List.of(GameState.FIGHTING_BOSS, GameState.FIGHTING_MOB, GameState.HEALING_ROOM).contains(this.controller.getState()))
                    this.game.printMap();

                return;
            }

            Controller.printSideBySideText(
                consumed.getASCII(), 
                ConsoleColors.GREEN + "Jordan consumed " + consumed + ConsoleColors.RESET + 
                "\n\n\n" + this.game.getPlayer().getPrettifiedStatus(), 
                7
            );

            System.out.println();
            if (!List.of(GameState.FIGHTING_BOSS, GameState.FIGHTING_MOB, GameState.HEALING_ROOM).contains(this.controller.getState()))
                this.game.printMap();

        } catch (NumberFormatException e) {
            System.out.println("Please insert a valid number");
        }
    }
}


class InfoItemCommand extends GameCommand {
    public InfoItemCommand(Game game, String[] commands) {
        super(game, commands);
        this.invalidStates = List.of(
            GameState.SMITH_FORGING,
            GameState.MAIN_MENU
        );
    }

    @Override
    public void execute() throws IllegalStateException {
        super.execute();

        if (commands.length != 2) {
            System.out.println(ConsoleColors.RED + "Invalid command. Correct Syntax: use [item]" + ConsoleColors.RESET);
            return;
        }
        
        try {
            this.game.infoItem(Integer.parseInt(commands[1]));
            System.out.println("\n" + this.game.getPlayer().getPrettifiedStatus());
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
            GameState.MAIN_MENU,
            GameState.FIGHTING_BOSS,
            GameState.FIGHTING_MOB,
            GameState.CHOOSING_CHARACTER
        );
    }

    @Override
    public void execute() throws IllegalStateException {
        super.execute();

        if (commands.length != 2) {
            System.out.println(ConsoleColors.RED + "Invalid command. Correct Syntax: swap [weapon]" + ConsoleColors.RESET);
            return;
        }
        
        try {
            Controller.clearConsole();
            if (!this.game.getPlayer().swapWeapon(Integer.parseInt(commands[1]))) {
                System.out.println();
                if (!List.of(GameState.FIGHTING_BOSS, GameState.FIGHTING_MOB, GameState.HEALING_ROOM).contains(this.controller.getState()))
                    this.game.printMap();
                return;
            }

            Controller.printSideBySideText(this.player.getWeapon().getASCII(), ConsoleColors.GREEN + "Jordan equipped " + this.player.getWeapon() + ConsoleColors.RESET, 7);
            System.out.println();
            
            if (!List.of(GameState.FIGHTING_BOSS, GameState.FIGHTING_MOB, GameState.HEALING_ROOM).contains(this.controller.getState()))
                this.game.printMap();
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

        if (commands.length != 2) {
            System.out.println(ConsoleColors.RED + "Invalid command. Correct Syntax: drop [item]" + ConsoleColors.RESET);
            return;
        }

        if (!Controller.awaitConfirmation()) return;

        try {
            Controller.clearConsole();
            Item dropped = this.game.dropItem(Integer.parseInt(commands[1]));
            if (dropped == null) {
                System.out.println();
                   
                if (!List.of(GameState.FIGHTING_BOSS, GameState.FIGHTING_MOB, GameState.HEALING_ROOM).contains(this.controller.getState()))
                    this.game.printMap();
                return;
            }

            Controller.printSideBySideText(dropped.getASCII(), ConsoleColors.RED + "Jordan dropped " + dropped + ConsoleColors.RESET, 7);
            System.out.println();

            if (this.controller.getState() == GameState.LOOTING_ROOM) {
                LootRoom room = (LootRoom) this.player.getCurrentRoom();
                
                if (!this.player.addInventoryItem(room.getItem())) return;

                System.out.println(ConsoleColors.GREEN + "You've picked up the " + room.getItem() + ConsoleColors.RESET);

                this.controller.setState(GameState.MOVING);
                room.clear(this.player);

                return;
            }

            if (!List.of(GameState.FIGHTING_BOSS, GameState.FIGHTING_MOB, GameState.HEALING_ROOM).contains(this.controller.getState()))
                this.game.printMap();
        } catch (NumberFormatException e) {
            System.out.println("Please insert a valid number");
        }
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
        Controller.clearConsole();
        System.out.println(ConsoleColors.PURPLE + "The game seed is: " + this.game.getSeed() + ConsoleColors.RESET + "\n");
        if (!List.of(GameState.FIGHTING_BOSS, GameState.FIGHTING_MOB, GameState.HEALING_ROOM).contains(this.controller.getState()))
            this.game.printMap();
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

        this.controller.changeMusic(Constants.MAIN_MENU_WAV_FILE_PATH, true);

        this.game.uploadToCloud();
        this.controller.setGame(null);
        this.controller.setState(GameState.MAIN_MENU);
        Controller.clearConsole();
        System.out.println(Constants.GAME_TITLE);
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
        Controller.clearConsole();
        System.out.println(ConsoleColors.GREEN + "The game current score is: " + this.game.getScore() + ConsoleColors.RESET + "\n");
        if (!List.of(GameState.FIGHTING_BOSS, GameState.FIGHTING_MOB, GameState.HEALING_ROOM).contains(this.controller.getState()))
            this.game.printMap();
    }
}

/**
 * Concrete command class to display the available commands
 * @see GameCommand
 */
class HelpCommand extends GameCommand {
    public HelpCommand(Game game) {
        super(game, null);
        this.invalidStates = List.of(
            GameState.MAIN_MENU
        );
    }

    @Override
    public void execute() {
        Controller.clearConsole();
        switch (this.controller.getState()) {
            case MAIN_MENU:
                System.out.println("Commands: new, load, exit");
                break;
            case CHOOSING_CHARACTER:
                System.out.println("Choose a character: default [1], hacker [2], data thief [3], mecha knight [4], neo samurai [5]");
                break;
            case MOVING:
                System.out.println("Commands: gn, gs, gw, ge, return, inv, status, map, score, seed, quit, use *number*, drop *number*, swap *number*");
                break;
            case MERCHANT_SHOPPING:
                System.out.println("Commands: buy *number*, back");
                break;
            case SMITH_FORGING:
                System.out.println("Commands: give smith weaponshard, back");
                break;
            case FIGHTING_MOB:
                System.out.println("Commands: attack, skill, inv, use *number*, drop *number*, info *number*");
                break;
            case FIGHTING_BOSS:
                System.out.println("Commands: attack, skill, inv, use *number*, drop *number*, info *number*");
                break;
            case LOOTING_ROOM:
                System.out.println("Commands: confirm, inv, use *number*, drop *number*, swap *number*");
                break;
            case HEALING_ROOM:
                System.out.println("Commands: merchant, smith, inv, use *number*, drop *number*, swap *number*");
                break;
            default:
                break;
        }

        System.out.println();
        if (!List.of(GameState.FIGHTING_BOSS, GameState.FIGHTING_MOB, GameState.HEALING_ROOM).contains(this.controller.getState()))
            this.game.printMap();
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
        Controller.clearConsole();
        this.game.returnToStart();
        if (!List.of(GameState.FIGHTING_BOSS, GameState.FIGHTING_MOB, GameState.HEALING_ROOM).contains(this.controller.getState()))
            this.game.printMap();
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
        Controller.clearConsole();
        System.out.println(this.player + "\n");
        if (!List.of(GameState.FIGHTING_BOSS, GameState.FIGHTING_MOB, GameState.HEALING_ROOM).contains(this.controller.getState()))
            this.game.printMap();
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
        
        Controller.clearConsole();
        HealingRoom room = (HealingRoom) this.game.getCurrentRoom();
        Merchant merchant = (Merchant) room.getFriendlyEntityByName(Merchant.NAME);
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

        if (commands.length != 2) {
            System.out.println(ConsoleColors.RED + "Invalid command. Correct Syntax: buy [item]" + ConsoleColors.RESET);
            return;
        }
        
        if (!Controller.awaitConfirmation()) return;

        HealingRoom room = (HealingRoom) this.game.getCurrentRoom();
        Merchant merchant = (Merchant) room.getFriendlyEntityByName(Merchant.NAME);
        
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

        Controller.clearConsole();
        HealingRoom room = (HealingRoom) this.game.getCurrentRoom();
        Smith smith = (Smith) room.getFriendlyEntityByName(Smith.NAME);
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
            System.out.println(ConsoleColors.RED + "Invalid command. Correct Syntax: give [npc] [item]" + ConsoleColors.RESET);
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
        Controller.clearConsole();
        this.controller.newGame();
        System.out.println("Choose your starting character: [1] - BasePlayer, [2] - Hacker, [3] - DataThief, [4] - MechaKnight, [5] - NeoSamurai");
        System.out.println("\n" + Constants.CLASS_CHOICE);
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
        Controller.clearConsole();
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
        Controller.clearConsole();
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
            System.out.println("Choose your starting character: [1] - BasePlayer, [2] - Hacker, [3] - DataThief, [4] - MechaKnight, [5] - NeoSamurai");
            System.out.println("\n" + Constants.CLASS_CHOICE);
            return;
        }
            
        this.controller.changeMusic(
            Constants.INTRO_WAV_FILE_PATH,
            false,
            new Runnable() {
                @Override
                public void run() {
                    Controller.getInstance().changeMusic(
                        Constants.LOOP_WAV_FILE_PATH,
                        true
                    );
                }
            }
        );

        Controller.clearConsole();

        CharacterFactory factory = this.controller.characterFactory(commands[0]);
        this.game.setPlayer(factory.create());
        this.controller.setState(GameState.MOVING);

        Controller.printSideBySideText(
            this.game.getPlayer().getASCII(), 
            this.game.getPlayer().toString() + "\n\n\n" + this.game.getPlayer().getWeapon() + "\n" +
            this.game.getPlayer().getWeapon().getASCII()
        );
        
        System.out.println();
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
            System.out.println(ConsoleColors.RED + "Invalid command" + ConsoleColors.RESET);
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

    /**
     * Await user confirmation before proceeding with an action
     * @return true if the user confirms the action, false otherwise
     */
    public static boolean awaitConfirmation() {
        System.out.println("Are you sure? (yes/no)");
        String input;
        do {
            input = Controller.scanner.nextLine();
        } while (!input.matches("(yes|y|no|n)"));

        return input.matches("(yes|y)");
    }

    /**
     * Await user input before proceeding with an action
     */
    public static void awaitEnter() {
        System.out.println("Press Enter to continue...");
        Controller.scanner.nextLine();
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

    public void setGame(Game game) {
        this.game = game;
    }

    public int getScore() {
        return this.game.getScore();
    }

    public void printMap() {
        this.game.printMap();
    }

    public void printMapAndStatus() {
        String[] firstLines = this.game.generateMapLines();
        String[] secondLines = ("\n" + this.game.getPlayer().getPrettifiedStatus()).split("\n");
        
        // Find the maximum number of rows between the ASCII art and the map
        int maxLines = Math.max(firstLines.length, secondLines.length);

        // Find the maximum number of columns in the ASCII art (to pad the map with spaces)
        int maxRows = firstLines[0].length();
        for (int i = 1; i < firstLines.length; i++)
            if (firstLines[i].length() > maxRows) maxRows = firstLines[i].length();
    
        for (int i = 0; i < maxLines; i++) {
            String firstLine = i < firstLines.length ? firstLines[i] : "";

            String secondLine = i < secondLines.length ? secondLines[i] : "";
            System.out.println(firstLine + "\t\t\t\t" + secondLine);
        }

    }

    public static void printSideBySideText(String first, String second, int offsetRows) {
        String[] firstLines = first.split("\n");
        String[] secondLines = second.split("\n");
        
        // Find the maximum number of rows between the ASCII art and the map
        int maxLines = Math.max(firstLines.length, secondLines.length);

        // Find the maximum number of columns in the ASCII art (to pad the map with spaces)
        int maxRows = firstLines[0].length();
        for (int i = 1; i < firstLines.length; i++)
            if (firstLines[i].length() > maxRows) maxRows = firstLines[i].length();
    
        for (int i = 0; i < maxLines; i++) {
            String firstLine = i < firstLines.length ? firstLines[i] : "";
            String pad = " ".repeat(maxRows - firstLine.length());

            // Offset the map by 5 rows
            String secondLine = (i >= offsetRows && (i - offsetRows) < secondLines.length) ? secondLines[i - offsetRows] : "";
            System.out.println(firstLine + pad + "\t\t\t" + secondLine);
        }
    }

    public static void printSideBySideText(String first, String second) {
        // Offset of the String second from the top of the console
        // final int DEFAULT_OFFSET_ROWS = 7; 
        final int DEFAULT_OFFSET_ROWS = 1; 
        Controller.printSideBySideText(first, second, DEFAULT_OFFSET_ROWS);
    }

    /**
     * Prints an ASCII art and the game map side by side
     * @param asciiArt the ASCII art to be printed
     */
    public void printMapAndArt(String asciiArt) {
        Controller.printSideBySideText(asciiArt, String.join("\n", this.game.generateMapLines()));
    }

    /*
     * TODO
     * Implement loading pre-defined configurations from JSON file (and maybe random map generation?)
     */
    public void newGame() {
        ArrayList<Level> map = GameData.deserializeMap(Constants.MAP);
        // ArrayList<Level> map = GameData.deserializeMapFromFile("map.json");
        Game game = new Game(map);
        // game.setSeed(1);

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
        if (commands[0].matches("\\d+")) return new ChooseCharacterGameCommand(this.game, commands);

        Map<String, Supplier<GameCommand>> commandMap = new HashMap<>(
            Map.ofEntries(
                Map.entry("new", () -> new NewGameCommand(this.game, commands)),
                Map.entry("load", () -> new LoadGameCommand(this.game, commands)),
                Map.entry("exit", () -> new ExitGameCommand(this.game, commands)),
                Map.entry("gn", () -> new MoveNorthCommand(this.game, commands)),
                Map.entry("gs", () -> new MoveSouthCommand(this.game, commands)),
                Map.entry("gw", () -> new MoveWestCommand(this.game, commands)),
                Map.entry("ge", () -> new MoveEastCommand(this.game, commands)),
                Map.entry("help", () -> new HelpCommand(this.game)),
                Map.entry("atk", () -> new AttackCommand(this.game, commands)),
                Map.entry("skill", () -> new SkillCommand(this.game, commands)),
                Map.entry("inv", () -> new InventoryCommand(this.game, commands)),
                Map.entry("use", () -> new UseItemCommand(this.game, commands)),
                Map.entry("drop", () -> new DropItemCommand(this.game, commands)),
                Map.entry("info", () -> new InfoItemCommand(this.game, commands)),
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
            System.out.println("Jordan can't do that now!");
        }
    }

    @JsonIgnore
    public String getAvailableCommands() {
        return switch (this.getState()) {
            case MAIN_MENU -> "[new] - [load] - [exit]";
            case CHOOSING_CHARACTER -> "[1] - BasePlayer, [2] - Hacker, [3] - DataThief, [4] - MechaKnight, [5] - NeoSamurai";
            case FIGHTING_BOSS, FIGHTING_MOB -> "[atk] - [skill] - [use *number*] - [inv]";
            case LOOTING_ROOM -> "[inv] - [use *number*] - [drop *number*] - " + this.game.getAvailableDirections();
            case MOVING -> this.game.getAvailableDirections();
            case MERCHANT_SHOPPING -> "[buy *number*] - [back]";
            case SMITH_FORGING -> "[give smith weaponshard] - [back]";
            case BOSS_DEFEATED -> "[inv] - [gn, gs, gw ge to change floor]";
            case HEALING_ROOM -> "[merchant] - [smith] - [inv] - [use *number*] - [drop *number*] - [gn, gs, gw ge to change floor]";
            case WIN -> "[gn, gs, gw ge to escape the dungeon and become a LEGEND!]";
            default -> "";
        };
    }

    public void changeMusic(String filename, boolean loop) {
        this.musicPlayer.setNewFile(filename);
        this.musicPlayer.playMusic(loop);
    }

    public void changeMusic(String filename, boolean loop, Runnable callback) {
        this.musicPlayer.stop();
        this.musicPlayer = new MusicPlayer(filename, callback);
        this.musicPlayer.playMusic(loop);
    }

    public static void setConsoleEncoding() {

        try {
            if (System.getProperty("os.name").startsWith("Windows")) {
                AnsiConsole.systemInstall();
                new ProcessBuilder("cmd", "/c", "chcp", "65001").inheritIO().start();
            } else
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

    public static void clearConsole() {
        final int DEFAULT_DELAY = 100;
        Controller.clearConsole(DEFAULT_DELAY);
    }

    public static void wait(int delay) {
        try {
            Thread.sleep(delay);
        } catch (InterruptedException e) {
            System.out.println("Error waiting");
            e.printStackTrace();
        }
    }

    public static void printColored(String text, Ansi.Color color) {
        System.out.println(Ansi.ansi().fg(color).a(text).reset());
    }

    /**
     * Main game loop
     */
    public void run() {
        Controller.setConsoleEncoding();
        Controller.clearConsole();

        this.musicPlayer = new MusicPlayer(Constants.MAIN_MENU_WAV_FILE_PATH);
        this.musicPlayer.playMusic(true);

        System.out.println(Constants.GAME_TITLE);

        while (this.getState() != GameState.EXIT) {
            if (this.game != null) {
                Character player = this.game.getPlayer();
                if (player != null) System.out.println("\nWhat to do?\n" + this.getAvailableCommands() + " (write \"help\" for the complete list of commands): ");
            }
            
            String input = Controller.scanner.nextLine();
            this.handleUserInput(input);
        }
        
        Controller.scanner.close();
        this.musicPlayer.stop();

        if (System.getProperty("os.name").startsWith("Windows")) AnsiConsole.systemUninstall(); 
    }
}
