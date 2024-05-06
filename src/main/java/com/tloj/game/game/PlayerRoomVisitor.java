package com.tloj.game.game;

import com.tloj.game.rooms.*;
import com.tloj.game.utilities.Dice;
import com.tloj.game.utilities.GameState;
import com.tloj.game.entities.Character;


interface Visitor {
    void visit(StartRoom room);
    void visit(BossRoom room);
    void visit(HealingRoom room);
    void visit(HostileRoom room);
    void visit(LootRoom room);
    void visit(TrapRoom room);
}

public class PlayerRoomVisitor implements Visitor {
    private Character player;
    Controller controller;

    public PlayerRoomVisitor(Character player) {
        this.player = player;
        this.controller = Controller.getInstance();
    }

    @Override
    public void visit(StartRoom room) {
        room.visit();
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
        System.out.println("You've found a healing room! What do you want to do?");
        System.out.println("1. Heal");
        System.out.println("2. Leave");
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
        System.out.println("You've found some loot! What do you want to do?");
        System.out.println("1. Take it");
        System.out.println("2. Leave it");
        this.controller.setState(GameState.LOOTING_ROOM);
    }

    @Override
    public void visit(TrapRoom room) {
        room.visit();
        Dice dice = new Dice(6);
        int roll = dice.roll();
        if (roll < 3) {
            System.out.println("You've been hit by a trap!");
            this.player.takeDamage(roll);
        }
    }
}