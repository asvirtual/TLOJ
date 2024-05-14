package com.tloj.game.entities.mobs;

import com.tloj.game.entities.Mob;
import com.tloj.game.entities.MovingEntity;
import com.tloj.game.game.Controller;
import com.tloj.game.game.Level;
import com.tloj.game.rooms.HostileRoom;
import com.tloj.game.rooms.RoomType;
import com.tloj.game.utilities.ConsoleHandler;
import com.tloj.game.utilities.Constants;
import com.tloj.game.utilities.Coordinates;

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
 * It has {@value #HP} health points, {@value #ATTACK} attack points, {@value #DEFENSE} defense points, {@value #DICE_FACES} dice faces, {@value #XP_DROP} experience points drop, {@value #MONEY_DROP} money drop.
 * @see JetBat
 * @see JunkSlime
 * @see MechaRat
 */
public class Glitched extends Mob implements MovingEntity {
    private static final int HP = 70;
    private static final int ATTACK = 5;
    private static final int DEFENSE = 1;
    private static final int DICE_FACES = 6;
    private static final int XP_DROP = 3;
    private static final int MONEY_DROP = 2;
    private static final int TURNS_BEFORE_DISAPPEARANCE = 5;

    private int turnsLeft = TURNS_BEFORE_DISAPPEARANCE;
    
    @JsonCreator
    public Glitched(
        @JsonProperty("position") Coordinates position,
        @JsonProperty("lvl") int lvl
    ) {
        super(HP, ATTACK, DEFENSE, DICE_FACES, lvl, XP_DROP, MONEY_DROP, position, new SpecialKey());
    }

    @Override
    public void attack(CombatEntity t) {
        super.attack(t);

        Character player = (Character) t;

        if (!this.isAlive()) return;
        Controller.awaitEnter();
        ConsoleHandler.clearConsole();

        this.turnsLeft--;

        Level level = player.getCurrentLevel();
        
        int rows = level.getRoomsRowCount();
        int cols = level.getRoomsColCount();
        boolean validLocation = false;

        do {
            int row = (int) Math.floor(Math.random() * rows); // Returns a random number between 0 (inclusive) and rows (exclusive)
            int col = (int) Math.floor(Math.random() * cols); // Returns a random number between 0 (inclusive) and cols (exclusive)
            Coordinates newCoords = new Coordinates(row, col);

            if (
                newCoords.equals(player.getPosition()) ||
                !level.areCoordinatesValid(newCoords) || 
                level.getRoom(newCoords).getType() != RoomType.HOSTILE_ROOM ||
                level.getRoom(newCoords).getType() == RoomType.BOSS_ROOM
            ) continue;
            
            HostileRoom currentRoom = (HostileRoom) player.getCurrentRoom();
            HostileRoom nextRoom = (HostileRoom) level.getRoom(newCoords);

            currentRoom.removeMob(this);

            Controller.printSideBySideText(
                this.getASCII(),
                ConsoleHandler.PURPLE + 
                    (this.turnsLeft != 0 ? 
                    "A bug in the system teleported the Glitched away!" + ConsoleHandler.RESET + "\n" + ConsoleHandler.PURPLE + "Will it come back?\n" :
                    "The Glitched has gone...") +
                ConsoleHandler.RESET
            );

            if (this.turnsLeft != 0) {
                this.move(newCoords);
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
    public void takeDamage(int damage) {
        super.takeDamage(damage);
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
