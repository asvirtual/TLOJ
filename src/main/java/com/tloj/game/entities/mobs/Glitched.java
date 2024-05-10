package com.tloj.game.entities.mobs;

import com.tloj.game.entities.Mob;
import com.tloj.game.entities.MovingEntity;
import com.tloj.game.game.Attack;
import com.tloj.game.game.Level;
import com.tloj.game.game.PlayerAttack;
import com.tloj.game.rooms.HostileRoom;
import com.tloj.game.rooms.LootRoom;
import com.tloj.game.rooms.RoomType;
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
    private int turnsLeft = 5;
    
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
        this.turnsLeft--;

        Character player = playerAttack.getAttacker();
        Level level = player.getCurrentLevel();
        boolean validLocation = false;

        int rows = level.getRoomsRowCount();
        int cols = level.getRoomsColCount();

        do {
            int row = (int) Math.floor(Math.random() * rows);
            int col = (int) Math.floor(Math.random() * cols);

            Coordinates newCoords = new Coordinates(row, col);
            if (!level.areCoordinatesValid(newCoords)) continue;
            if (
                level.getRoom(newCoords).getType() == RoomType.BOSS_ROOM ||
                level.getRoom(newCoords).getType() == RoomType.TRAP_ROOM ||
                (level.getRoom(newCoords).getType() == RoomType.LOOT_ROOM && ((LootRoom) level.getRoom(newCoords)).isLocked())
            ) 
                continue;
            
            HostileRoom nextRoom = (HostileRoom) level.getRoom(newCoords);
            nextRoom.addMobToTop(this);
            this.move(newCoords);
            validLocation = true;
        } while (!validLocation);
    }

    @Override
    public void move(Coordinates to) {
        if (this.turnsLeft != 0) this.position = to;
        else System.out.print("The Glitched has gone...");
    }

    @Override
    public Item getDrop() {
        return this.drop;
    }
}
