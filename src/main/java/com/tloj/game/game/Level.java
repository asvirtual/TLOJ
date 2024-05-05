package com.tloj.game.game;

import java.util.ArrayList;

import com.tloj.game.rooms.BossRoom;
import com.tloj.game.rooms.HealingRoom;
import com.tloj.game.rooms.HostileRoom;
import com.tloj.game.rooms.LootRoom;
import com.tloj.game.rooms.Room;
import com.tloj.game.rooms.RoomType;
import com.tloj.game.rooms.StartRoom;
import com.tloj.game.rooms.TrapRoom;
import com.tloj.game.utilities.Coordinates;


public class Level {
    private int levelNumber;
    private ArrayList<ArrayList<Room>> rooms;
    private StartRoom startRoom;

    public Level(int levelNumber, RoomType[][] roomsTypes) {
        this.levelNumber = levelNumber;
        this.rooms = new ArrayList<ArrayList<Room>>(roomsTypes.length);

        for (int i = 0; i < roomsTypes.length; i++) {
            for (int j = 0; j < roomsTypes[i].length; j++) {
                switch (roomsTypes[i][j]) {
                    case START_ROOM:
                        this.startRoom = new StartRoom(new Coordinates(i, j));
                        this.rooms.get(i).add(this.startRoom);
                        break;
                    case BOSS_ROOM:
                        this.rooms.get(i).add(new BossRoom(new Coordinates(i, j), null));
                        break;
                    case HEALING_ROOM:
                        this.rooms.get(i).add(new HealingRoom(new Coordinates(i, j)));
                        break;
                    case HOSTILE_ROOM:
                        this.rooms.get(i).add(new HostileRoom(new Coordinates(i, j), null));
                        break;
                    case LOOT_ROOM:
                        this.rooms.get(i).add(new LootRoom(new Coordinates(i, j), null, false));
                        break;
                    case TRAP_ROOM:
                        this.rooms.get(i).add(new TrapRoom(new Coordinates(i, j)));
                        break;
                    default:
                        break;
                }
            }

        }
    }

    public StartRoom getStartRoom() {
        return this.startRoom;
    }

    public int getRoomsRowCount() {
        return this.rooms.size();
    }

    public int getRoomsColCount() {
        return this.rooms.get(0).size();
    }

    public Room getRoom(Coordinates coordinates) {
        return this.rooms.get(coordinates.getX()).get(coordinates.getY());
    }

    public int getLevelNumber() {
        return this.levelNumber;
    }
}
