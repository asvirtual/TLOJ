package com.tloj.game.rooms.roomeffects;

import com.tloj.game.entities.Character;
import com.tloj.game.game.Level;
import com.tloj.game.rooms.RoomType;
import com.tloj.game.rooms.LootRoom;
import com.tloj.game.utilities.Coordinates;
import com.tloj.game.collectables.items.NorthStar;

/**
 * Class that implements a room effect that teleports the character to a random room<br>
 * Teleports the player to a random room in the same floor causing the player to forget the visited rooms(doesn't apply with {@link NorthStar} item)<br>
 * @see RoomEffect
 * @see InflictDamage
 * @see StealMoney
 */

public class TpEffect implements RoomEffect {
    /**
     * Default constructor to allow Jackson to deserialize JSON.
     */
    public TpEffect() {}

    @Override
    public void applyEffect(Character character) {
        Level level = character.getCurrentLevel();
        boolean validLocation = false;

        int rows = level.getRoomsRowCount();
        int cols = level.getRoomsColCount();

        do {
            int row = (int) Math.floor(Math.random() * rows);
            int col = (int) Math.floor(Math.random() * cols);

            Coordinates newCoords = new Coordinates(row, col);
            if (!level.areCoordinatesValid(newCoords)) continue;
            if (level.getRoom(newCoords).getType() == RoomType.BOSS_ROOM) continue;
            if (level.getRoom(newCoords).getType() == RoomType.LOOT_ROOM && ((LootRoom) level.getRoom(newCoords)).isLocked()) continue;

            level.getRoomStream().forEach(rowRooms -> {
                rowRooms.forEach(room -> room.forget());
            });            

            character.move(newCoords);
            validLocation = true;
        } while (!validLocation);
    }
}
