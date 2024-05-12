package com.tloj.game.game;

import com.tloj.game.rooms.*;
import com.tloj.game.utilities.ConsoleColors;
import com.tloj.game.utilities.Constants;
import com.tloj.game.utilities.Dice;
import com.tloj.game.utilities.GameState;
import com.tloj.game.collectables.Item;
import com.tloj.game.collectables.items.SpecialKey;
import com.tloj.game.entities.Character;


/**
 * Represents a visitor for different types of rooms in the game.
 * Each method in this class represents a visit to a specific type of room.
 */
interface Visitor {
    void visit(StartRoom room);
    void visit(BossRoom room);
    void visit(HealingRoom room);
    void visit(HostileRoom room);
    void visit(EndRoom room);
    void visit(LootRoom room);
    void visit(TrapRoom room);
}

public class PlayerRoomVisitor implements Visitor {
    // The player character
    private Character player;
    // The game controller
    private Controller controller;

    /**
     * Constructs a PlayerRoomVisitor object with the given player character.<br>
     * @param player The player character.
     * @param controller The game controller.
     */
    public PlayerRoomVisitor(Character player) {
        this.player = player;
        this.controller = Controller.getInstance();
    }

    /**
     * Visit method for a StartRoom.<br>
     * @param room The StartRoom to visit.
     * Visits a start room, clears the room, and sets the game state to MOVING
     */
    @Override
    public void visit(StartRoom room) {
        room.visit();
        room.clear(this.player);

        this.controller.setState(GameState.MOVING);
        // this.controller.printMap();
        this.controller.printMapAndStatus();
    }

    /**
     * Visit method for a BossRoom.<br>
     * @param room The BossRoom to visit.
     * Visits a boss room, triggers a fight with the boss, and sets the game state to FIGHTING_BOSS
     */
    @Override
    public void visit(BossRoom room) {
        room.visit();

        Controller.clearConsole();

        System.out.println(ConsoleColors.RED_BRIGHT + "You have to face " + room.getBoss() + ConsoleColors.RESET +  "\n");
        Controller.printSideBySideText(
            room.getBoss().getASCII(), 
            room.getBoss().getPrettifiedStatus() + "\n\n\n" + this.player.getPrettifiedStatus()
        );
        System.out.println();

        // System.out.println(
        //     ConsoleColors.RED_BRIGHT + "You have to face " + room.getBoss() + ConsoleColors.RESET + 
        //     "\n" + room.getBoss().getASCII() + "\n" + 
        //     room.getBoss().getPrettifiedStatus() + "\n"
        // );

        this.controller.setState(GameState.FIGHTING_BOSS);
    }

    /**
     * Visit method for a HealingRoom.<br>
     * @param room The HealingRoom to visit.
     * Visits a healing room, heals the player, and sets the game state to HEALING_ROOM
     */
    @Override
    public void visit(HealingRoom room) {
        room.visit();
        this.player.setHp(this.player.getMaxHp());
        this.player.setMana(this.player.getMaxMana());

        Controller.clearConsole();

        System.out.println(Constants.HEALING_ROOM + ConsoleColors.GREEN_BRIGHT + "\nWelcome to the healing rooom!" + ConsoleColors.RESET);
        this.controller.setState(GameState.HEALING_ROOM);
    }

    /**
     * Visit method for a HostileRoom.<br>
     * @param room The HostileRoom to visit.
     * Visits a hostile room, triggers a fight with the mob, and sets the game state to FIGHTING_MOB
     */
    @Override
    public void visit(HostileRoom room) {
        room.visit();
        if (room.isCleared()) {
            // this.controller.printMap();
            this.controller.printMapAndStatus();
            return;
        }

        Controller.clearConsole();

        System.out.println(ConsoleColors.PURPLE + "You've encountered " + room.getMob() + ConsoleColors.RESET + "\n");
        Controller.printSideBySideText(
            room.getMob().getASCII(), 
            room.getMob().getPrettifiedStatus() + "\n\n\n" + this.player.getPrettifiedStatus()
        );
        System.out.println();

        // System.out.println(
        //     ConsoleColors.PURPLE + "You've encountered " + room.getMob() + ConsoleColors.RESET + 
        //     "\n" + room.getMob().getASCII() + "\n" +
        //     room.getMob().getPrettifiedStatus() + "\n"
        // );

        this.controller.setState(GameState.FIGHTING_MOB);
    }

    /**
     * Visit method for a LootRoom.<br>
     * @param room The LootRoom to visit.
     * Visits a loot room, adds the item to the player's inventory, and clears the room
     */
    @Override
    public void visit(LootRoom room) {
        room.visit();
        if (room.isCleared()) {
            // this.controller.printMap();
            this.controller.printMapAndStatus();
            return;
        }
        
        Controller.clearConsole();

        if (room.isLocked()) {
            this.player.useItem(new SpecialKey());
            System.out.println(ConsoleColors.CYAN_BRIGHT + "You've used a special key to unlock the room!" + ConsoleColors.RESET);
        }
        
        Item item = room.getItem();
        if (item == null) return;

        this.controller.printMapAndArt(item.getASCII());
        System.out.println(ConsoleColors.YELLOW_BRIGHT + "You've found a " + item + "!" + ConsoleColors.RESET);
        
        if (this.player.addInventoryItem(item)) {
            this.controller.setState(GameState.MOVING);
            room.clear(this.player);
        } else {
            this.controller.setState(GameState.LOOTING_ROOM);
        }
    }

    /**
     * Visit method for a TrapRoom.<br>
     * @param room The TrapRoom to visit.
     * Visits a trap room, triggers the trap, and executes the side effect of the room
     */
    @Override
    public void visit(TrapRoom room) {
        room.visit();
        if (room.isCleared()) {
            // this.controller.printMap();
            this.controller.printMapAndStatus();
            return;
        }

        Controller.clearConsole();
        
        if (!room.triggerTrap(this.player)) {
            this.controller.printMapAndArt(Constants.TRAP_DEFENDER);
            System.out.println(ConsoleColors.BLUE_BRIGHT + "You've dodged the trap! Thanks Windows Defender!" + ConsoleColors.RESET);
        }
        
        room.clear(this.player);
        room.executeSideEffect();
    }

    /**
     * Visit method for a EndRoom.<br>
     * @param room The EndRoom to visit.
     * Visits an end room, sets the game state to WIN, and displays game win message
     */
    @Override
    public void visit(EndRoom room) {
        room.visit();
        this.controller.setState(GameState.WIN);

        Controller.clearConsole();
        System.out.println(ConsoleColors.YELLOW_BOLD_BRIGHT + "Congratulations! You won the game with " + this.controller.getScore() + " points!" + ConsoleColors.RESET);
        System.out.println(ConsoleColors.YELLOW_BOLD_BRIGHT + Constants.GAME_WIN + ConsoleColors.RESET);
    }
} 