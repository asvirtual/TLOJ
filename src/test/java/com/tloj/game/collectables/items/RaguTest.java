package com.tloj.game.collectables.items;

import com.tloj.game.entities.Character;
import com.tloj.game.entities.characters.BasePlayer;
import com.tloj.game.collectables.Item;
import com.tloj.game.collectables.weapons.LaserBlade;

import org.junit.jupiter.api.Test;
import static org.junit.jupiter.api.Assertions.*;

import java.util.ArrayList;

public class RaguTest {
    @Test
    void testConsume() {
        //max hp for BasePlayer is 20, set to 3 for testing
        Character mockCharacter = new BasePlayer(
            20, 4, 4, 10, 0, 1, 5, 10, null, null, new LaserBlade(), new ArrayList<Item>(), null
        );
        mockCharacter.setHp(3);
        mockCharacter.addInventoryItem(new Ragu());
        Ragu item = (Ragu) mockCharacter.getInventoryItem(0);
        item.consume(mockCharacter);
        assertEquals(20, mockCharacter.getHp());
        assertNull(mockCharacter.searchInventoryItem(item));
    }

    @Test
    void testConsumeMaxHealth() {
        Character mockCharacter = new BasePlayer(
            20, 4, 4, 10, 0, 1, 5, 10, null, null, new LaserBlade(), new ArrayList<Item>(), null
        );
        mockCharacter.setHp(20);
        mockCharacter.addInventoryItem(new Ragu());
        Ragu item = (Ragu) mockCharacter.getInventoryItem(0);
        item.consume(mockCharacter);
        assertEquals(20, mockCharacter.getHp());
        assertNull(mockCharacter.searchInventoryItem(item));
    }
}
