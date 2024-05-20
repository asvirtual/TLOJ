package com.tloj.game.collectables.items;

import com.tloj.game.entities.Character;
import com.tloj.game.entities.characters.BasePlayer;

import org.junit.jupiter.api.Test;
import static org.junit.jupiter.api.Assertions.*;


public class RaguTest {
    @Test
    void testConsume() {
        // Max hp for BasePlayer is 20, set to 3 for testing
        Character mockCharacter = new BasePlayer(null);
        mockCharacter.setHp(3);
        mockCharacter.addInventoryItem(new Ragu());
        Ragu item = (Ragu) mockCharacter.getInventoryItem(0);
        item.consume(mockCharacter);
        assertEquals(20, mockCharacter.getHp());
        assertNull(mockCharacter.getInventoryItem(item));
    }

    @Test
    void testConsumeMaxHealth() {
        Character mockCharacter = new BasePlayer(null);
        mockCharacter.setHp(20);
        mockCharacter.addInventoryItem(new Ragu());
        Ragu item = (Ragu) mockCharacter.getInventoryItem(0);
        item.consume(mockCharacter);
        assertEquals(20, mockCharacter.getHp());
        assertNull(mockCharacter.getInventoryItem(item));
    }
}
