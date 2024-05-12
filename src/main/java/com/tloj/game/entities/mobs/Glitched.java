package com.tloj.game.entities.mobs;

import com.tloj.game.entities.Mob;
import com.tloj.game.entities.MovingEntity;
import com.tloj.game.game.Attack;
import com.tloj.game.game.Controller;
import com.tloj.game.game.Level;
import com.tloj.game.game.PlayerAttack;
import com.tloj.game.game.PlayerRoomVisitor;
import com.tloj.game.rooms.HostileRoom;
import com.tloj.game.rooms.LootRoom;
import com.tloj.game.rooms.RoomType;
import com.tloj.game.utilities.Constants;
import com.tloj.game.utilities.Coordinates;
import com.fasterxml.jackson.annotation.JsonCreator;
import com.fasterxml.jackson.annotation.JsonProperty;
import com.tloj.game.collectables.Item;
import com.tloj.game.collectables.items.SpecialKey;
import com.tloj.game.entities.Character;


/**
 * Represents the Glithed Wandering Mob entity in the game.<br>
 * The Glithed Wandering is a special moving entyty in the game. It has average health and low defense, but inflicts some damage<br>
 * It has {@value #HP} health points, {@value #ATTACK} attack points, {@value #DEFENSE} defense points, {@value #DICE_FACES} dice faces, {@value #XP_DROP} experience points drop, {@value #MONEY_DROP} money drop.
 * @see JetBat
 * @see JunkSlime
 * @see MechaRat
 */
public class Glitched extends Mob implements MovingEntity {
    private static final int HP = 50;
    private static final int ATTACK = 3;
    private static final int DEFENSE = 0;
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
    public void defend(Attack attack) {
        super.defend(attack);
        PlayerAttack playerAttack = (PlayerAttack) attack;

        attack.setOnHit(new Runnable() {
            @Override
            public void run() {
                Glitched target = (Glitched) playerAttack.getTarget();
                if (!target.isAlive()) return;
                
                target.turnsLeft--;
        
                Character player = playerAttack.getAttacker();
                Level level = player.getCurrentLevel();
                
                int rows = level.getRoomsRowCount();
                int cols = level.getRoomsColCount();
                boolean validLocation = false;
        
                do {
                    int row = (int) Math.floor(Math.random() * rows); // Returns a random number between 0 (inclusive) and rows (exclusive)
                    int col = (int) Math.floor(Math.random() * cols); // Returns a random number between 0 (inclusive) and cols (exclusive)
                    Coordinates newCoords = new Coordinates(row, col);

                    if (
                        newCoords == player.getPosition() ||
                        !level.areCoordinatesValid(newCoords) || 
                        level.getRoom(newCoords).getType() != RoomType.HOSTILE_ROOM ||
                        level.getRoom(newCoords).getType() == RoomType.BOSS_ROOM
                    ) continue;
                    
                    HostileRoom currentRoom = (HostileRoom) player.getCurrentRoom();
                    HostileRoom nextRoom = (HostileRoom) level.getRoom(newCoords);

                    currentRoom.removeMob(target);

                    if (target.turnsLeft != 0) {
                        target.move(newCoords);
                        nextRoom.addMobToTop(target);
                        nextRoom.setCleared(false);
                        System.out.println("A bug in the system teleported the Glitched away! Will it come back?"); // TODO: "Animation" with ASCII arts
                    } else {
                        System.out.print("The Glitched has gone...");
                    }

                    validLocation = true;
                } while (!validLocation);
            }
        });
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
}
