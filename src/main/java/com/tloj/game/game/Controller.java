package com.tloj.game.game;


import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.nio.charset.StandardCharsets;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Scanner;
import java.util.Stack;
import java.util.function.Supplier;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.google.api.Control;
import com.tloj.game.rooms.HealingRoom;
import com.tloj.game.rooms.LootRoom;
import com.tloj.game.collectables.Item;
import com.tloj.game.entities.Character;
import com.tloj.game.entities.characters.BasePlayer;
import com.tloj.game.entities.characters.Hacker;
import com.tloj.game.entities.characters.DataThief;
import com.tloj.game.entities.characters.MechaKnight;
import com.tloj.game.entities.characters.NeoSamurai;
import com.tloj.game.entities.npcs.Merchant;
import com.tloj.game.entities.npcs.Smith;
import com.tloj.game.utilities.ConsoleHandler;
import com.tloj.game.utilities.Constants;
import com.tloj.game.utilities.Coordinates;
import com.tloj.game.utilities.FirebaseHandler;
import com.tloj.game.utilities.GameState;
import com.tloj.game.utilities.JsonParser;
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
 * - Buy (buy [number]) {@link BuyCommand} (buy item from merchant)<br>
 * - Smith (smith) {@link SmithCommand} (talk with smith in healing room)<br>
 * - Give (give [smith] [weaponshard]) {@link GiveCommand} (give item to npc)<br>
 * - New game (new) {@link NewGameCommand} (start a new game)<br>
 * - Load game (load) {@link LoadGameCommand} (load a saved game)<br>
 * - Exit game (exit) {@link ExitGameCommand} (exit the game)<br>
 * - Choose character (choose [character]) {@link ChooseCharacterGameCommand} (choose a character)<br>
 * - Activate skill (skill) {@link SkillCommand} (activate character's skill)<br>
 * - Show inventory (inventory) {@link InventoryCommand} (show player's inventory)<br>
 * - Swap weapon (swap [weapon]) {@link SwapWeaponCommand} (swap player's weapon)<br>
 * - Pick item (pick) {@link PickItemCommand} (pick item in loot room)<br>
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
            GameState.LOOTING_ROOM,
            GameState.WIN
        );
    }

    @Override
    public void execute() throws IllegalStateException {
        super.execute();

        try {
            ConsoleHandler.clearConsole();
            this.game.movePlayer(Coordinates.Direction.NORTH);
        } catch (IllegalArgumentException e) {
            System.out.println(ConsoleHandler.RED + e.getMessage() + ConsoleHandler.RESET + "\n");
            this.game.printMap();
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
            GameState.LOOTING_ROOM,
            GameState.WIN
        );
    }

    @Override
    public void execute() throws IllegalStateException {
        super.execute();

        try {
            ConsoleHandler.clearConsole();
            this.game.movePlayer(Coordinates.Direction.SOUTH);
        } catch (IllegalArgumentException e) {
            System.out.println(ConsoleHandler.RED + e.getMessage() + ConsoleHandler.RESET + "\n");
            this.game.printMap();
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
            GameState.LOOTING_ROOM,
            GameState.WIN
        );
    }

    @Override
    public void execute() throws IllegalStateException {
        super.execute();
        
        try {
            ConsoleHandler.clearConsole();
            this.game.movePlayer(Coordinates.Direction.WEST);
        } catch (IllegalArgumentException e) {
            System.out.println(ConsoleHandler.RED + e.getMessage() + ConsoleHandler.RESET + "\n");
            this.game.printMap();
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
            GameState.LOOTING_ROOM,
            GameState.WIN
        );
    }

    @Override
    public void execute() throws IllegalStateException {
        super.execute();
        
        try {
            ConsoleHandler.clearConsole();
            this.game.movePlayer(Coordinates.Direction.EAST);
        } catch (IllegalArgumentException e) {
            System.out.println(ConsoleHandler.RED + e.getMessage() + ConsoleHandler.RESET + "\n");
            this.game.printMap();
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
        ConsoleHandler.clearConsole();
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
        ConsoleHandler.clearConsole();
        this.game.printInventory();
        System.out.println();
        if (!List.of(GameState.FIGHTING_BOSS, GameState.FIGHTING_MOB, GameState.HEALING_ROOM, GameState.SMITH_FORGING).contains(this.controller.getState()))
            this.game.printMap();

        System.out.print(
            "\n You can: " + 
            "\n -Type [info *item number*] to see the item details,\n"+
            " -Type [use *item number*] to consume an item,\n"+
            " -Type [drop *item number*] to drop it,\n" + 
            "Or press Enter to continue.\n" 
            );
        String input = Controller.getScanner().nextLine();
        if (input.isBlank()) {
            ConsoleHandler.clearAndReprint();
            return;
        }

        this.controller.handleUserInput(input);
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
            GameState.SMITH_FORGING,
            GameState.MAIN_MENU
        );
    }

    @Override
    public void execute() throws IllegalStateException {
        super.execute();

        if (commands.length != 2) {
            System.out.println(ConsoleHandler.RED + "Invalid command. Correct Syntax: use [item]" + ConsoleHandler.RESET);
            return;
        }

        if (this.player.hasUsedItem()) {
            System.out.println(ConsoleHandler.RED + "You've already used an item this turn" + ConsoleHandler.RESET);
            return;
        }

        if (!Controller.awaitConfirmation()) return;
        
        try {
            ConsoleHandler.clearConsole();
            Item consumed = this.game.useItem(Integer.parseInt(commands[1]));

            if (consumed == null) {
                System.out.println();
                if (!List.of(GameState.FIGHTING_BOSS, GameState.FIGHTING_MOB, GameState.HEALING_ROOM, GameState.MERCHANT_SHOPPING).contains(this.controller.getState()))
                    this.game.printMap();

                Controller.awaitEnter();
                ConsoleHandler.clearAndReprint();
                return;
            }

            Controller.printSideBySideText(
                consumed.getASCII(), 
                ConsoleHandler.GREEN + "Jordan consumed " + consumed + ConsoleHandler.RESET + 
                "\n\n\n" + this.game.getPlayer().getPrettifiedStatus(), 
                7,
                false
            );

            System.out.println();
            if (!List.of(GameState.FIGHTING_BOSS, GameState.FIGHTING_MOB, GameState.HEALING_ROOM, GameState.MERCHANT_SHOPPING).contains(this.controller.getState()))
                this.game.printMap();

            Controller.awaitEnter();
            ConsoleHandler.clearConsole();
            Controller.printSideBySideText(
                this.game.getPlayer().getASCII(),
                this.game.getPlayer().getPrettifiedStatus(), 
                7,
                false
            );
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
            System.out.println(ConsoleHandler.RED + "Invalid command. Correct Syntax: use [item]" + ConsoleHandler.RESET);
            return;
        }
        
        try {
            ConsoleHandler.clearConsole();
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
            System.out.println(ConsoleHandler.RED + "Invalid command. Correct Syntax: swap [weapon]" + ConsoleHandler.RESET);
            return;
        }
        
        try {
            ConsoleHandler.clearConsole();
            if (!this.game.getPlayer().swapWeapon(Integer.parseInt(commands[1]))) {
                System.out.println();
                if (!List.of(GameState.FIGHTING_BOSS, GameState.FIGHTING_MOB, GameState.HEALING_ROOM).contains(this.controller.getState()))
                    this.game.printMap();
                return;
            }

            Controller.printSideBySideText(this.player.getWeapon().getASCII(), ConsoleHandler.GREEN + "Jordan equipped " + this.player.getWeapon() + ConsoleHandler.RESET, 7);
            System.out.println();
            
            if (!List.of(GameState.FIGHTING_BOSS, GameState.FIGHTING_MOB, GameState.HEALING_ROOM, GameState.MERCHANT_SHOPPING).contains(this.controller.getState()))
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
            GameState.SMITH_FORGING,
            GameState.MAIN_MENU,
            GameState.FIGHTING_MOB,
            GameState.FIGHTING_BOSS
        );
    }

    @Override
    public void execute() throws IllegalStateException {
        super.execute();

        if (commands.length != 2) {
            System.out.println(ConsoleHandler.RED + "Invalid command. Correct Syntax: drop [item]" + ConsoleHandler.RESET);
            return;
        }

        if (!Controller.awaitConfirmation()) return;

        try {
            ConsoleHandler.clearConsole();
            Item dropped = this.game.dropItem(Integer.parseInt(commands[1]));
            if (dropped == null) {
                System.out.println();
                   
                if (!List.of(GameState.HEALING_ROOM, GameState.MERCHANT_SHOPPING).contains(this.controller.getState()))
                    this.game.printMap();

                Controller.awaitEnter();
                ConsoleHandler.clearAndReprint();
                return;
            }

            Controller.printSideBySideText(dropped.getASCII(), ConsoleHandler.RED + "Jordan dropped " + dropped + ConsoleHandler.RESET, 7);
            System.out.println();

            if (this.controller.getState() == GameState.LOOTING_ROOM) {
                LootRoom room = (LootRoom) this.player.getCurrentRoom();
                
                if (!this.player.addInventoryItem(room.getItem())) return;

                System.out.println(ConsoleHandler.GREEN + "You've picked up the " + room.getItem() + ConsoleHandler.RESET);

                this.controller.setState(GameState.MOVING);
                room.clear(this.player);

                Controller.awaitEnter();
                ConsoleHandler.clearAndReprint();
                return;
            }

            if (!List.of(GameState.HEALING_ROOM, GameState.MERCHANT_SHOPPING).contains(this.controller.getState()))
                this.game.printMap();

            Controller.awaitEnter();
            ConsoleHandler.clearAndReprint();
        } catch (NumberFormatException e) {
            System.out.println("Please insert a valid number");
        }
    }
}

/**
 * Concrete command class to drop an item<br>
 * @see GameCommand <br>
 */
class PickItemCommand extends GameCommand {
    public PickItemCommand(Game game, String[] commands) {
        super(game, commands);
        this.validListStates = List.of(
            GameState.LOOTING_ROOM
        );
    }

    @Override
    public void execute() throws IllegalStateException {
        super.execute();

        ConsoleHandler.clearConsole();
        LootRoom room = (LootRoom) this.game.getCurrentRoom();
        Item item = room.getItem();

        if (!this.player.addInventoryItem(item)) {
            System.out.println(ConsoleHandler.RED + "You can't carry more weight" + ConsoleHandler.RESET);
            return;
        }

        System.out.println(item.getASCII() + "\n");
        System.out.println(ConsoleHandler.GREEN + "You've picked up the " + item + ConsoleHandler.RESET);
        this.controller.setState(GameState.MOVING);
        room.clear(this.player);
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
        ConsoleHandler.clearConsole();
        System.out.println(ConsoleHandler.PURPLE + "The game seed is: " + this.game.getSeed() + ConsoleHandler.RESET + "\n");
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

        this.controller.saveCurrentGameToCloud();

        this.controller.setGame(null);
        this.controller.setState(GameState.MAIN_MENU);
        ConsoleHandler.clearConsole();
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
        ConsoleHandler.clearConsole();
        System.out.println(ConsoleHandler.GREEN + "The game current score is: " + this.game.getScore() + ConsoleHandler.RESET + "\n");
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
        ConsoleHandler.clearConsole();
        switch (this.controller.getState()) {
            case MAIN_MENU:
                System.out.println(
                    "Commands:\n new --> to start a new game,\n load --> to load a saved game,\n exit --> to exit the game\n");
                break;
            case CHOOSING_CHARACTER:
                System.out.println("\n" + Constants.CLASS_CHOICE + "\n");
                break;
            case MOVING:
                System.out.println(
                    "Commands:\n gn --> to go north,\n gs --> to go south,\n gw --> to go west,\n ge --> to go east" + 
                    "\nreturn --> to return to the starting room of the floor,\n inv --> to show your inventory" + 
                    "\nstatus --> to show your detailed statistics,\n map --> to print the map" + 
                    "\n score --> to show your current score,\n seed --> to print the game seed,\n quit --> to return to the main menu," +
                    "\n use *number* --> to use an Item,\n drop *number* --> to drop an Item,\n swap *number* --> to swap your weapon,"
                );
                break;
            case MERCHANT_SHOPPING:
                System.out.println("Commands: buy *number* --> to buy an Item,\n back --> to return to the previous state");
                break;
            case SMITH_FORGING:
                System.out.println("Commands: give smith weaponshard --> to give weaponshard to smith,\n back --> to return to the previous state");
                break;
            case FIGHTING_MOB:
            case FIGHTING_BOSS:
                System.out.println(
                    "Commands: attack --> to attack,\n skill --> to use your ability,\n inv --> to show your inventory," +
                    "\n use *number* --> to use an Item,\n drop *number* --> to drop an Item,\n info *number* --> to get information about an Item"
                );
                break;
            case LOOTING_ROOM:
                System.out.println("Commands: pick --> to pick the Item in the room,\n inv --> to show your inventory,\n drop *number* --> to drop an Item,\n info *number* --> to get information about an Item");
                break;
            case HEALING_ROOM:
                System.out.println("Commands: merchant --> to talk with the Merchant,\n smith --> to talk with the Smith,\n drop *number* --> to drop an Item,\n info *number* --> to get information about an Item");
                break;
            default:
                break;
        }

        System.out.println();
        if (
            !List.of(
                GameState.FIGHTING_BOSS, 
                GameState.FIGHTING_MOB, 
                GameState.HEALING_ROOM, 
                GameState.MAIN_MENU, 
                GameState.CHOOSING_CHARACTER
            ).contains(this.controller.getState())
        )
            this.game.printMap();

        Controller.awaitEnter();
        ConsoleHandler.clearAndReprint();
    }
}

/**
 * Concrete command class to return to the starting room
 * @see GameCommand
 */
class ReturnCommand extends GameCommand {
    public ReturnCommand(Game game, String[] commands) {
        super(game, null);
        this.validListStates = List.of(GameState.MOVING);
    }

    @Override
    public void execute() throws IllegalStateException {
        super.execute();
        ConsoleHandler.clearConsole();
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
        ConsoleHandler.clearConsole();
        System.out.println(this.player + "\n");
        if (!List.of(GameState.FIGHTING_BOSS, GameState.FIGHTING_MOB, GameState.HEALING_ROOM).contains(this.controller.getState()))
            this.game.printMap();
        
        Controller.awaitEnter();
        ConsoleHandler.clearAndReprint();
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
        
        ConsoleHandler.clearConsole();
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
            System.out.println(ConsoleHandler.RED + "Invalid command. Correct Syntax: buy [item]" + ConsoleHandler.RESET);
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

        ConsoleHandler.clearConsole();
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
            System.out.println(ConsoleHandler.RED + "Invalid command. Correct Syntax: give [npc] [item]" + ConsoleHandler.RESET);
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
        ConsoleHandler.clearConsole();

        System.out.print(ConsoleHandler.YELLOW + "Enter a custom name for this save: " + ConsoleHandler.RESET);
        String saveName = Controller.getScanner().nextLine();
        String seed;

        do {
            System.out.print(ConsoleHandler.YELLOW + "Enter a custom seed for the game or press Enter to generate it automatically: " + ConsoleHandler.RESET);
            seed = Controller.getScanner().nextLine();

            if (!seed.isBlank() && !seed.matches("\\d+")) 
                System.out.println(ConsoleHandler.RED + "Please insert a valid number as the seed!" + ConsoleHandler.RESET);
        } while (!seed.isBlank() && !seed.matches("\\d+"));

        this.controller.newGame(saveName, seed);
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
        
        ConsoleHandler.clearConsole();
        int idx = 1;
        SimpleDateFormat formatter = new SimpleDateFormat("dd/MM/yyyy - HH:mm:ss");

        GameIndex.getEntries().forEach(filename -> {
            try {
                Game game = JsonParser.loadFromFile(filename);
                System.out.println(
                    idx + ". " + filename.split(Constants.SAVE_GAME_FILENAME_SEPARATOR)[0] + " " + game.getPlayer().getName() + 
                    formatter.format(game.getCreationTime())
                );
            } catch (IOException e) {
                e.printStackTrace();
            }
        });

        try {
            ConsoleHandler.clearConsole();
            this.controller.loadGame(Integer.parseInt(commands[1]));
        } catch (NumberFormatException e) {
            System.out.println(ConsoleHandler.RED + "Insert a valid number" + ConsoleHandler.RESET);
            return;
        }
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
        ConsoleHandler.clearConsole();
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
            ConsoleHandler.clearConsole();
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

        ConsoleHandler.clearConsole();

        CharacterFactory factory = this.controller.characterFactory(commands[0]);
        this.game.setPlayer(factory.create());
        this.game.saveLocally();
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
            System.out.println(ConsoleHandler.RED + "Invalid command" + ConsoleHandler.RESET);
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
    /** 
     * Singleton Controller unique instance 
     */
    private static Controller instance;
    private static Scanner scanner;
    private Game game;
    private int currentGameId;
    private FirebaseHandler saveHandler;
    /**
     * Music player to handle the game music
     */
    private MusicPlayer musicPlayer;
    /**
     * Stack to keep track of the game states<br>
     */
    private Stack<GameState> history;

    private Controller() {
        Controller.scanner = new Scanner(System.in);
        this.saveHandler = FirebaseHandler.getInstance();
        this.history = new Stack<GameState>();
        this.history.push(GameState.MAIN_MENU);
    }

    public static Scanner getScanner() {
        return Controller.scanner;
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
        String input = "";
        do {
            System.out.println("Press Enter to continue...");
            input = Controller.scanner.nextLine();
        } while (!input.isBlank());
    }

    /** 
     * Singleton pattern to ensure only one instance of the Controller class is created
     * @return the unique instance of the Controller class
     */
    public static Controller getInstance() {
        if (instance == null) instance = new Controller();
        return instance;
    }

    public FirebaseHandler getSaveHandler() {
        return this.saveHandler;
    }

    public int getCurrentGameId() {
        return this.currentGameId;
    }

    /**
     * Get the current game state by peeking the top of the history stack
     * @return the current game state
     * @see GameState
     */
    public GameState getState() {
        return this.history.peek();
    }

    /**
     * Change the current game state by pushing it to the history stack
     * @param state the new game state
     * @see GameState
     */
    public void setState(GameState state) {
        this.history.push(state);
    }

    /**
     * Go back to the previous game state by popping the top of the history stack
     * @see GameState
     */
    public void goBackState() {
        this.history.pop();
    }

    public void setGame(Game game) {
        this.game = game;
    }

    /**
     * Facade method to get the game score
     * @see Game#getScore()
     * @return the game score
     */
    public int getScore() {
        return this.game.getScore();
    }

    /**
     * Facade method to print the game map
     * @see Game#printMap()
     */
    public void printMap() {
        this.game.printMap();
    }

    /**
     * Prints the game map and the player status side by side
     */
    public void printMapAndStatus() {
        String[] firstLines = this.game.generateMapLines();
        String[] secondLines = ("\n" + this.game.getPlayer().getPrettifiedStatus()).split("\n");
        String toPrint = "";
        
        // Find the maximum number of rows between the ASCII art and the map
        int maxLines = Math.max(firstLines.length, secondLines.length);

        // Find the maximum number of columns in the ASCII art (to pad the map with spaces)
        int maxRows = firstLines[0].length();
        for (int i = 1; i < firstLines.length; i++)
            if (firstLines[i].length() > maxRows) maxRows = firstLines[i].length();
    
        for (int i = 0; i < maxLines; i++) {
            String firstLine = i < firstLines.length ? firstLines[i] : "";

            String secondLine = i < secondLines.length ? secondLines[i] : "";
            toPrint += firstLine + "\t\t\t\t" + secondLine + "\n";
        }

        ConsoleHandler.clearLog();
        ConsoleHandler.println(toPrint);
        ConsoleHandler.saveToLog(toPrint);
    }

    /**
     * Prints two Strings side by side
     * @param first the first String to be printed (to the left)
     * @param second the second String to be printed (to the right)
     * @param offsetRows the number of rows to offset the second String from the top of the console
     */
    public static void printSideBySideText(String first, String second, int offsetRows, boolean saveToLog) {
        String[] firstLines = first.split("\n");
        String[] secondLines = second.split("\n");
        String toPrint = "";
        
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
            toPrint += firstLine + pad + "\t\t\t" + secondLine + "\n";
        }

        if (saveToLog) {
            ConsoleHandler.clearLog();
            ConsoleHandler.println(toPrint);
        } else System.out.println(toPrint);
    }

    public static void printSideBySideText(String first, String second, int offsetRows) {
        Controller.printSideBySideText(first, second, offsetRows, true);
    }

    /**
     * Prints two Strings side by side
     * @param first the first String to be printed (to the left)
     * @param second the second String to be printed (to the right)
     * {@link Controller#printSideBySideText(String, String, int)}
     * @param first
     * @param second
     */
    public static void printSideBySideText(String first, String second) {
        // Offset of the String second from the top of the console
        final int DEFAULT_OFFSET_ROWS = 1; 
        Controller.printSideBySideText(first, second, DEFAULT_OFFSET_ROWS);
    }

    /**
     * Prints an ASCII art and the game map side by side
     * @param asciiArt the ASCII art to be printed
     * {@link Controller#printSideBySideText(String, String)}
     */
    public void printMapAndArt(String asciiArt) {
        Controller.printSideBySideText(asciiArt, String.join("\n", this.game.generateMapLines()));
    }

    // TODO: maybe only save the game when the player chooses a character, since without a character the game is not really started
    public void newGame(String name, String seed) {
        try {
            // ArrayList<Level> map = JsonParser.deserializeMap(Constants.MAP);
            ArrayList<Level> map = JsonParser.deserializeMapFromFile(Constants.MAP_FILE_PATH);

            Game game;
            if (seed.isBlank()) game = new Game(map);
            else game = new Game(map, Long.parseLong(seed));

            // TODO: Sanitize the save name
            String saveName = name + Constants.SAVE_GAME_FILENAME_SEPARATOR + game.getCreationTime() + ".json";
            JsonParser.saveToFile(game, Constants.BASE_SAVES_DIRECTORY + saveName);
            this.currentGameId = GameIndex.addEntry(saveName);

            this.setState(GameState.CHOOSING_CHARACTER);
            this.setGame(game);
            
            this.saveHandler.saveToCloud(Constants.GAMES_INDEX_FILE_PATH);
        } catch (NumberFormatException e) {
            System.out.println(ConsoleHandler.RED + "Please insert a valid number as the seed!" + ConsoleHandler.RESET);
        }
    }

    public void saveCurrentGameToCloud() {
        this.saveHandler.saveToCloud(GameIndex.getFile(String.valueOf(this.currentGameId)));
        this.saveHandler.saveToCloud(Constants.GAMES_INDEX_FILE_PATH);
    }

    public void loadGame(int index) {
        try {
            String saveName = GameIndex.getFile(String.valueOf(index));
            this.currentGameId = index;
            this.game = JsonParser.loadFromFile(Constants.BASE_SAVES_DIRECTORY + saveName);
            this.setState(GameState.MOVING);
        } catch (IOException e) {
            System.out.println(ConsoleHandler.RED + "Please insert a number from the list above" + ConsoleHandler.RESET);
        }
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
                Map.entry("give", () -> new GiveCommand(this.game, commands)),
                Map.entry("pick", () -> new PickItemCommand(this.game, commands))
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
            System.out.println("There's a time and a place for everything...");
        }
    }

    /**
     * Returns the available commands based on the current game state
     * @return the available commands
     */
    @JsonIgnore
    public String getAvailableCommands() {
        return switch (this.getState()) {
            case MAIN_MENU -> "[new] - [load] - [exit]";
            case FIGHTING_BOSS, FIGHTING_MOB -> "[atk] - [skill] - [use *number*] - [inv]";
            case LOOTING_ROOM -> "[inv] - [use *number*] - [drop *number*] - " + this.game.getAvailableDirections();
            case MOVING -> this.game.getAvailableDirections();
            case MERCHANT_SHOPPING -> "[buy *number*] - [back]";
            case SMITH_FORGING -> "[give smith weaponshard] - [back]";
            case BOSS_DEFEATED -> "[inv] - [gn, gs, gw ge to change floor]";
            case HEALING_ROOM -> "[merchant] - [smith] - [inv] - [use *number*] - [drop *number*] - [gn, gs, gw ge to change floor]";
            case WIN -> "[gn, gs, gw, ge to escape the dungeon and become a LEGEND!]";
            default -> "";
        };
    }

    /**
     * Changes the game music based on the filename and loop flag
     * @param filename the music file to be played
     * @param loop whether the music should loop or not
     */
    public void changeMusic(String filename, boolean loop) {
        this.musicPlayer.setNewFile(filename);
        this.musicPlayer.playMusic(loop);
    }

    /**
     * Changes the game music based on the filename, loop flag and callback function
     * @param filename the music file to be played
     * @param loop whether the music should loop or not
     * @param callback the function to be executed after the music is played
     */
    public void changeMusic(String filename, boolean loop, Runnable callback) {
        this.musicPlayer.stop();
        this.musicPlayer = new MusicPlayer(filename, callback);
        this.musicPlayer.playMusic(loop);
    }

    /**
     * Main game loop
     */
    public void run() {
        ConsoleHandler.setConsoleEncoding();
        ConsoleHandler.clearConsole();

        this.musicPlayer = new MusicPlayer(Constants.MAIN_MENU_WAV_FILE_PATH);
        this.musicPlayer.playMusic(true);

        GameIndex.loadGames();
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

        ConsoleHandler.resetConsoleEncoding();
    }
}
