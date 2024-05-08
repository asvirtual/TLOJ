package com.tloj.game.game;

import com.tloj.game.rooms.*;
import com.tloj.game.utilities.Dice;
import com.tloj.game.utilities.GameState;
import com.tloj.game.collectables.Item;
import com.tloj.game.entities.Character;


interface Visitor {
    void visit(StartRoom room);
    void visit(BossRoom room);
    void visit(HealingRoom room);
    void visit(HostileRoom room);
    void visit(LootRoom room);
    void visit(TrapRoom room);
}

/**
 * TODO
 */
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
    }

    @Override
    public void visit(BossRoom room) {
        room.visit();

        System.out.println("You've encountered the boss!");

        this.controller.setState(GameState.FIGHTING_BOSS);
    }

    @Override
    public void visit(HealingRoom room) {
        room.visit();
        this.player.setHp(this.player.getMaxHp());
    }

    @Override
    public void visit(HostileRoom room) {
        room.visit();

        System.out.println("You've encountered an enemy!");

        this.controller.setState(GameState.FIGHTING_MOB);
    }

    @Override
    public void visit(LootRoom room) {
        room.visit();
        Item item = room.getItem();
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
        if (room.isCleared()) return;
        
        room.visit();
        Dice dice = new Dice(6);
        int roll = dice.roll();
        if (roll < 3) {
            System.out.println("You've been hit by a trap!");
            room.triggerTrap(this.player);
        }
      
        room.clear();
    }
}