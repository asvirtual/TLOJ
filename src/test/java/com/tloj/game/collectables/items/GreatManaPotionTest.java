package com.tloj.game.collectables.items;

import com.tloj.game.entities.Character;
import com.tloj.game.entities.characters.BasePlayer;

import org.junit.jupiter.api.Test;
import static org.junit.jupiter.api.Assertions.*;

public class GreatManaPotionTest {

    @Test
    void testConsume() {
        // Max mana for BasePlayer is 15
        Character mockCharacter = new BasePlayer(null);
        mockCharacter.setMana(9);
        mockCharacter.addInventoryItem(new GreatManaPotion());
        GreatManaPotion item = (GreatManaPotion) mockCharacter.getInventoryItem(0);
        item.consume(mockCharacter);
        assertEquals(39, mockCharacter.getMana());
        assertNull(mockCharacter.getInventoryItem(item));
    }

    @Test
    void testConsumeMaxMana() {
        Character mockCharacter = new BasePlayer(null);
        mockCharacter.setMana(40);
        mockCharacter.addInventoryItem(new GreatManaPotion());
        GreatManaPotion item = (GreatManaPotion) mockCharacter.getInventoryItem(0);
        item.consume(mockCharacter);
        assertEquals(40, mockCharacter.getMana());
        assertNull(mockCharacter.getInventoryItem(item));
    }
    
}
