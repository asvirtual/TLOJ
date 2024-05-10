package com.tloj.game.rooms.roomeffects;

import com.tloj.game.entities.Character;
import com.tloj.game.game.Level;
import com.tloj.game.game.PlayerRoomVisitor;
import com.tloj.game.rooms.RoomType;
import com.tloj.game.rooms.LootRoom;
import com.tloj.game.rooms.Room;
import com.tloj.game.utilities.Coordinates;
import com.tloj.game.collectables.items.NorthStar;

/**
 * Class that implements a room effect that teleports the character to a random room<br>
 * Teleports the player to a random room in the same floor causing the player to forget the visited rooms(doesn't apply with {@link NorthStar} item)<br>
 * @see RoomEffect
 * @see InflictDamage
 * @see StealMoney
 */

public class TpEffect extends RoomEffect {
    private PlayerRoomVisitor visitor;
    private Room newRoom;

    /*
     * Default constructor for Jackson JSON deserialization
     */
    public TpEffect() {}

    public TpEffect(Runnable sideEffect) {
        this.sideEffect = new Runnable() {
            @Override
            public void run() {
                newRoom.accept(visitor);
            }
        };
    }

    @Override
    public boolean applyEffect(Character character) {
        Level level = character.getCurrentLevel();
        boolean validLocation = false;

        int rows = level.getRoomsRowCount();
        int cols = level.getRoomsColCount();

        do {
            int row = (int) Math.floor(Math.random() * rows);
            int col = (int) Math.floor(Math.random() * cols);

            Coordinates newCoords = new Coordinates(row, col);
            this.newRoom = character.getCurrentLevel().getRoom(newCoords);

            if (!level.areCoordinatesValid(newCoords)) continue;
            if (this.newRoom.getType() == RoomType.BOSS_ROOM) continue;
            if (this.newRoom.getType() == RoomType.LOOT_ROOM && ((LootRoom) this.newRoom).isLocked()) continue;

            level.getRoomStream().forEach(rowRooms -> {
                rowRooms.forEach(room -> {
                    if (room != null) room.forget();
                });
            });            

            character.move(newCoords);
            this.newRoom.visit();
            validLocation = true;
            
            this.visitor = new PlayerRoomVisitor(character);
        } while (!validLocation);

        return true;
    }
}
