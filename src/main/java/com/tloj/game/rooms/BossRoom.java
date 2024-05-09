package com.tloj.game.rooms;

import java.util.ArrayList;

import com.tloj.game.utilities.Coordinates;
import com.tloj.game.abilities.BossAbility;
import com.tloj.game.entities.Boss;
import com.tloj.game.entities.Mob;
import com.tloj.game.game.PlayerRoomVisitor;

/**
 * Class that represents a boss room in the game<br>
 * Boss Room is an extension of the Hostile Room, as Boss'es are hostile mobs{@link BossAbility}<br>
 * Contains the boss of the floor and is always the last room of the floor<br>
 * @see Room
 * @see HealingRoom
 * @see LootRoom
 * @see TrapRoom
 * @see HostileRoom
 * @see StartRoom
 */

public class BossRoom extends HostileRoom {
    /**
     * Default constructor to allow Jackson to deserialize JSON.
     */
    public BossRoom() {}

    public BossRoom(Coordinates coordinates, ArrayList<Mob> bosses) {
        super(coordinates, bosses);
    }

    public BossRoom(Coordinates coordinates, Boss boss) {
        super(coordinates, boss);
    }

    public Boss getBoss() {
        return (Boss) this.mobs.get(0);
    }

    @Override
    public RoomType getType() {
        return RoomType.BOSS_ROOM;
    }

    @Override
    public void accept(PlayerRoomVisitor visitor) {
        visitor.visit(this);
    }

    @Override
    public void exit() {
        // TODO Auto-generated method stub
        throw new UnsupportedOperationException("Unimplemented method 'exit'");
    }

    @Override
    public String toString() {
        if(this.isVisited())
            return "\u00DF" + " ";
        else
            return "\u00A0" + " ";
    }

    @Override 
    public void clear() {
        super.clear();
        this.mobs.set(0, null);
    }
}
