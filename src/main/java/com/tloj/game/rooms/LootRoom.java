package com.tloj.game.rooms;

import com.tloj.game.utilities.Coordinates;
import com.tloj.game.abilities.BossAbility;
import com.tloj.game.collectables.Item;
import com.tloj.game.collectables.Weapon;
import com.tloj.game.game.PlayerRoomVisitor;

/**
 * Class that represents a loot room in the game<br>
 * Contains an item ({@link Weapon}, {@link ConsumableItem})that the player can pick up<br>
 * @see Room
 * @see BossRoom
 * @see HealingRoom
 * @see TrapRoom
 * @see HostileRoom
 * @see StartRoom
 */

public class LootRoom extends Room {
    private Item item;

    /**
     * Default constructor to allow Jackson to deserialize JSON.
     */
    public LootRoom() {}

    public LootRoom(Coordinates coordinates) {
        super(coordinates);
        this.item = Item.getRandomItem();
        this.locked = false;
    }

    public LootRoom(Coordinates coordinates, boolean locked) {
        super(coordinates);
        this.item = Item.getRandomItem();
        this.locked = locked;
    }

    public LootRoom(Coordinates coordinates, boolean locked, Item item) {
        super(coordinates);
        this.item = item;
        this.locked = locked;
    }

    @Override
    public RoomType getType() {
        return RoomType.LOOT_ROOM;
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
            return "\u255A" + " ";
        else
            return "\u00A0" + " ";
    }
  
    public void clear() {
        super.clear();
        this.item = null;
    }

    public Item getItem() {
        return this.item;
    }
}
