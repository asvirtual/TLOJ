package com.tloj.game.collectables.items;

import com.tloj.game.entities.Character;
import com.tloj.game.entities.characters.BasePlayer;

import org.junit.jupiter.api.Test;
import static org.junit.jupiter.api.Assertions.*;


public class RaguTest {
    @Test
    void testConsume() {
        // Max hp for BasePlayer is 25, set to 3 for testing
        Character mockCharacter = new BasePlayer(null);
        mockCharacter.setHp(3);
        Ragu item = new Ragu();
        mockCharacter.addInventoryItem(item);
        item.consume(mockCharacter);
        assertEquals(25, mockCharacter.getHp());
        assertNull(mockCharacter.getInventoryItem(item));
    }

    @Test
    void testConsumeMaxHealth() {
        Character mockCharacter = new BasePlayer(null);
        Ragu item = new Ragu();
        mockCharacter.addInventoryItem(item);
        item.consume(mockCharacter);
        assertEquals(25, mockCharacter.getHp());
        assertNull(mockCharacter.getInventoryItem(item));
    }
}
