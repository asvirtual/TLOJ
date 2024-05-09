package com.tloj.game.entities.mobs;

import com.tloj.game.entities.Mob;
import com.tloj.game.entities.MovingEntity;
import com.tloj.game.game.Attack;
import com.tloj.game.game.Level;
import com.tloj.game.game.PlayerAttack;
import com.tloj.game.rooms.LootRoom;
import com.tloj.game.rooms.RoomType;
import com.tloj.game.utilities.Coordinates;

import java.lang.invoke.ClassSpecializer.SpeciesData;

import com.tloj.game.collectables.Item;
import com.tloj.game.collectables.items.SpecialKey;
import com.tloj.game.collectables.items.WeaponShard;
import com.tloj.game.entities.Character;


/**
 * Represents the Glithed Wandering Mob entity in the game.<br>
 * The Glithed Wandering is a special moving entyty in the game. It has average health and low defense, but inflicts some damage<br>
 * It has {@value #HP} health points, {@value #ATTACK} attack points, {@value #DEFENSE} defense points, {@value #DICE_FACES} dice faces, {@value #XP_DROP} experience points drop, {@value #MONEY_DROP} money drop.
 * @see Mob
 * @see JetBat
 * @see JunkSlime
 * @see MechaRat
 */

public class Glitched extends Mob implements MovingEntity{

    private static final int HP = 50;
    private static final int ATTACK = 3;
    private static final int DEFENSE = 0;
    private static final int DICE_FACES = 6;
    private static final int XP_DROP = 3;
    private static final int MONEY_DROP = 2;
    private int TurnLeft = 5;
    

    public Glitched(Coordinates position, int lvl) {
        super(HP, ATTACK, DEFENSE, DICE_FACES, lvl, XP_DROP, MONEY_DROP, position, new SpecialKey());
    }

    @Override
    public void defend(Attack attack) {
        PlayerAttack playerAttack = (PlayerAttack) attack;
        TurnLeft -= TurnLeft;
        super.defend(attack);

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
            if (level.getRoom(newCoords).getType() == RoomType.BOSS_ROOM) continue;
            if (level.getRoom(newCoords).getType() == RoomType.TRAP_ROOM) continue;
            if (level.getRoom(newCoords).getType() == RoomType.LOOT_ROOM && ((LootRoom) level.getRoom(newCoords)).isLocked()) continue;         

            move(newCoords);
            validLocation = true;
        } while (!validLocation);
    }

    @Override
    public void move(Coordinates to) {
        if(TurnLeft != 0){
            System.out.print("The Glitched has changed room");
            this.position = to;
        }
            else{
            System.out.print("The Glitched has gone...");
            this.die();
        }
    }

    @Override
    public String toString() {
        return "The Glitched";
    }

    @Override
    public Item getDrop() {
        return this.drop;
    }
}
