package com.tloj.game.rooms.roomeffects;

import com.tloj.game.entities.Character;
import com.tloj.game.game.Controller;
import com.tloj.game.game.Level;
import com.tloj.game.game.PlayerRoomVisitor;
import com.tloj.game.rooms.RoomType;
import com.tloj.game.rooms.LootRoom;
import com.tloj.game.rooms.Room;
import com.tloj.game.utilities.ConsoleHandler;
import com.tloj.game.utilities.Constants;
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
    /**
     * The visitor able to visit the new room after teleportation.
     */
    private PlayerRoomVisitor visitor;

    /**
     * The new room to teleport to.
     */
    private Room newRoom;

    public TpEffect() {
        this.sideEffect = new Runnable() {
            @Override
            public void run() {
                newRoom.accept(visitor);
            }
        };
    }

    /**
     * Applies the teleportation effect to the specified character.
     * Teleports the character to a random room in the current level, while printing relevant messages and updating the game state.
     *
     * @param character the character to apply the teleportation effect to
     * @return true if the effect was successfully applied, false otherwise
     */
    @Override
    public boolean applyEffect(Character character) {
        
        System.out.println(ConsoleHandler.PURPLE + "You've entered a Trap Room!" + ConsoleHandler.RESET + "\n");
        Controller.getInstance().printMapAndArt(this.getASCII());
        System.out.println("\n" + ConsoleHandler.PURPLE + "Oh no! Unexpected System call teleported you to a random room!" + ConsoleHandler.RESET);
        System.out.println("They hacked your GPS! You can't see the rooms you visited anymore!");

        Level level = character.getCurrentLevel();
        boolean validLocation = false;

        int rows = level.getRoomsRowCount();
        int cols = level.getRoomsColCount();

        // Loop until a valid location is found
        do {
            int row = (int) Math.floor(Math.random() * rows);
            int col = (int) Math.floor(Math.random() * cols);

            Coordinates newCoords = new Coordinates(row, col);
            this.newRoom = character.getCurrentLevel().getRoom(newCoords);

            // Skip if new location is the same as current location
            if (newCoords == character.getPosition()) continue;
            // Skip if new location is not valid on the level grid
            if (!level.areCoordinatesValid(newCoords)) continue;
            // Skip if new location is a boss room
            if (this.newRoom.getType() == RoomType.BOSS_ROOM) continue;
            // Skip if new location is a locked loot room
            if (this.newRoom.getType() == RoomType.LOOT_ROOM && ((LootRoom) this.newRoom).isLocked()) continue;

            // Forget all visited rooms
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

        Controller.awaitEnter();
        ConsoleHandler.clearConsole();  
        return true;
    }

    @Override
    public String getASCII() {
        return Constants.TP_EFFECT;
    }
}
