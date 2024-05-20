package com.tloj.game.collectables.items;

import com.tloj.game.entities.Character;
import com.tloj.game.entities.characters.BasePlayer;

import org.junit.jupiter.api.Test;
import static org.junit.jupiter.api.Assertions.*;


public class ManaPotionTest {
    @Test
    void testConsume() {
        // Max mana for BasePlayer is 15, set to 10 for testing
        Character mockCharacter = new BasePlayer(null);
        mockCharacter.setMana(10);
        mockCharacter.addInventoryItem(new ManaPotion());
        ManaPotion item = (ManaPotion) mockCharacter.getInventoryItem(0);
        item.consume(mockCharacter);
        assertEquals(15, mockCharacter.getMana());
        assertNull(mockCharacter.getInventoryItem(item));
    }

    @Test
    void testConsumeMaxMana() {
        Character mockCharacter = new BasePlayer(null);
        mockCharacter.setMana(15);
        mockCharacter.addInventoryItem(new ManaPotion());
        ManaPotion item = (ManaPotion) mockCharacter.getInventoryItem(0);
        item.consume(mockCharacter);
        assertEquals(15, mockCharacter.getMana());
        assertNull(mockCharacter.getInventoryItem(item));
    }
}
