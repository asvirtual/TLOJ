package com.tloj.game.game;

import com.tloj.game.rooms.*;
import com.tloj.game.utilities.Constants;
import com.tloj.game.utilities.Dice;
import com.tloj.game.utilities.GameState;
import com.tloj.game.collectables.Item;
import com.tloj.game.collectables.items.SpecialKey;
import com.tloj.game.entities.Character;


interface Visitor {
    void visit(StartRoom room);
    void visit(BossRoom room);
    void visit(HealingRoom room);
    void visit(HostileRoom room);
    void visit(EndRoom room);
    void visit(LootRoom room);
    void visit(TrapRoom room);
}

// TODO: What to do with statements here?

public class PlayerRoomVisitor implements Visitor {
    private Character player;
    private Controller controller;

    public PlayerRoomVisitor(Character player) {
        this.player = player;
        this.controller = Controller.getInstance();
    }

    @Override
    public void visit(StartRoom room) {
        room.visit();
        room.clear();

        this.controller.printMap();
    }

    @Override
    public void visit(BossRoom room) {
        room.visit();

        Controller.clearConsole(100);
        System.out.println("You've encountered " + room.getBoss() + "\n" + room.getBoss().getASCII() + "\n");
        this.controller.setState(GameState.FIGHTING_BOSS);
    }

    @Override
    public void visit(HealingRoom room) {
        room.visit();
        this.player.setHp(this.player.getMaxHp());
        this.player.setMana(this.player.getMana());

        System.out.println(Constants.HEALING_ROOM + "\nWelcome to the healing rooom!");

        this.controller.setState(GameState.HEALING_ROOM);
    }

    @Override
    public void visit(HostileRoom room) {
        room.visit();
        this.controller.printMap();
        if (room.isCleared()) return;

        Controller.clearConsole(100);
        System.out.println("You've encountered " + room.getMob() + room.getMob().getASCII() + "\n");
        this.controller.setState(GameState.FIGHTING_MOB);
        
        this.player.heal(1);
        this.player.restoreMana(1);
    }

    @Override
    public void visit(LootRoom room) {
        room.visit();
    
        if (room.isCleared()) {
            this.controller.printMap();
            return;
        }
        
        Controller.clearConsole(100);

        if (room.isLocked()) {
            this.player.useItem(new SpecialKey());
            System.out.println("You've used a special key to unlock the room!");
        }
        
        Item item = room.getItem();
        if (item == null) return;

        this.controller.printMapAndArt(item.getASCII());
        System.out.println("You've found a " + item + "!");
        
        if (this.player.addInventoryItem(item)) {
            this.controller.setState(GameState.MOVING);
            room.clear();
        } else {
            this.controller.setState(GameState.LOOTING_ROOM);
        }
    }

    @Override
    public void visit(TrapRoom room) {
        room.visit();

        if (room.isCleared()) {
            this.controller.printMap();
            return;
        }

        Controller.clearConsole(100);
        
        if (!room.triggerTrap(this.player)) {
            this.controller.printMapAndArt(Constants.TRAP_DEFENDER);
            System.out.println("You've dodged the trap! Thanks Windows Defender!");
        }
      
        room.clear();
        
        this.player.heal(1);
        this.player.restoreMana(1);

        room.executeSideEffect();
    }

    @Override
    public void visit(EndRoom room) {
        room.visit();
        this.controller.setState(GameState.WIN);
        System.out.println(Constants.GAME_WIN);
    }
} 