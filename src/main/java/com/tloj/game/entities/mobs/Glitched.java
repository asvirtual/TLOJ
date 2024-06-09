package com.tloj.game.entities.mobs;

import com.tloj.game.entities.Mob;
import com.tloj.game.entities.MovingEntity;
import com.tloj.game.game.Controller;
import com.tloj.game.game.Coordinates;
import com.tloj.game.game.Floor;
import com.tloj.game.rooms.HostileRoom;
import com.tloj.game.rooms.RoomType;
import com.tloj.game.utilities.ConsoleHandler;
import com.tloj.game.utilities.Constants;

import org.fusesource.jansi.Ansi;

import com.fasterxml.jackson.annotation.JsonCreator;
import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonProperty;
import com.tloj.game.collectables.Item;
import com.tloj.game.collectables.items.SpecialKey;
import com.tloj.game.entities.Character;
import com.tloj.game.entities.CombatEntity;


/**
 * Represents the Glithed Wandering Mob entity in the game.<br>
 * The Glithed Wandering is a special moving entyty in the game. It has average health and low defense, but inflicts some damage<br>
 * It has {@value #HP} health points <br>
 * {@value #ATTACK} attack points <br>
 * {@value #DEFENSE} defense points<br>
 * {@value #DICE_FACES} dice faces<br>
 * {@value #XP_DROP} experience points drop<br>
 * {@value #MONEY_DROP} money drop.<br>
 * It drops the {@link SpecialKey} item<br>
 * But disappears in {@value #TURNS_BEFORE_DISAPPEARANCE} turns<br>
 * @see JetBat
 * @see JunkSlime
 * @see MechaRat
 */

public class Glitched extends Mob implements MovingEntity {
    private static final int HP = 70;
    private static final int ATTACK = 10;
    private static final int DEFENSE = 6;
    private static final int DICE_FACES = 6;
    private static final int XP_DROP = 16;
    private static final int MONEY_DROP = 6;
    private static final int TURNS_BEFORE_DISAPPEARANCE = 4;

    @JsonProperty("turnsLeft")
    private int turnsLeft = TURNS_BEFORE_DISAPPEARANCE;
    
    @JsonCreator
    public Glitched(
        @JsonProperty("position") Coordinates position,
        @JsonProperty("lvl") int lvl
    ) {
        super(HP, ATTACK, DEFENSE, DICE_FACES, lvl, XP_DROP, MONEY_DROP, position, new SpecialKey());
    }

    public int getTurnsLeft() {
        return this.turnsLeft;
    }

    public void setTurnsLeft(int turnsLeft) {
        this.turnsLeft = turnsLeft;
    }

    @Override
    public void attack(CombatEntity t) {
        super.attack(t);

        Character player = (Character) t;

        if (!this.isAlive()) return;
        Controller.awaitEnter();
        ConsoleHandler.clearConsole();

        this.turnsLeft--;

        Floor floor = player.getCurrentFloor();
        
        int rows = floor.getRoomsRowCount();
        int cols = floor.getRoomsColCount();
        boolean validLocation = false;

        do {
            int y = (int) Math.floor(Math.random() * rows); // Returns a random number between 0 (inclusive) and rows (exclusive)
            int x = (int) Math.floor(Math.random() * cols); // Returns a random number between 0 (inclusive) and cols (exclusive)
            Coordinates newCoords = new Coordinates(x, y);

            if (
                newCoords.equals(this.getPosition()) ||
                newCoords.equals(player.getPosition()) ||
                !floor.areCoordinatesValid(newCoords) || 
                floor.getRoom(newCoords).getType() != RoomType.HOSTILE_ROOM ||
                floor.getRoom(newCoords).getType() == RoomType.BOSS_ROOM
            ) continue;
            
            HostileRoom currentRoom = (HostileRoom) player.getCurrentRoom();
            HostileRoom nextRoom = (HostileRoom) floor.getRoom(newCoords);

            currentRoom.removeMob(this);

            Controller.printSideBySideText(
                this.getASCII(),
                ConsoleHandler.PURPLE + 
                    (this.turnsLeft != 0 ? 
                    "A bug in the system teleported the Glitched away!" + ConsoleHandler.RESET + "\n" + ConsoleHandler.PURPLE + "Will it come back?" + ConsoleHandler.RESET + "\n" :
                    "The Glitched has gone...") +
                ConsoleHandler.RESET
            );

            this.move(newCoords);
            if (this.turnsLeft != 0) {
                nextRoom.addMobToTop(this);
                nextRoom.setCleared(false);
            }

            validLocation = true;
        } while (!validLocation);

        Controller.awaitEnter();
    }

    @Override
    public void move(Coordinates to) {
        this.position = to;
    }

    @Override
    public Item getDrop() {
        return this.drop;
    }
    
    @Override
    public String getASCII(){
        return Constants.GLITCHED_STATIC;
    }

    @Override
    public String getCombatASCII(){
        return Constants.GLITCHED_ATTACK;
    }

    @JsonIgnore
    @Override
    public String getPrettifiedStatus() {
        return 
            this + " - " + ConsoleHandler.GREEN + "Lvl ???" + ConsoleHandler.RESET + ":\n\n" +
            " ⸭ HP: " + Ansi.ansi().fg(Ansi.Color.RED).a(this.getHpBar() + " " + this.hp + "/" + this.maxHp).reset() + "\n";
    }
}
