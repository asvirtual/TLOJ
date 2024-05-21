package com.tloj.game.collectables.items;

import com.tloj.game.entities.Character;
import com.tloj.game.entities.characters.BasePlayer;

import org.junit.jupiter.api.Test;
import static org.junit.jupiter.api.Assertions.*;


public class HealthPotionTest {
    @Test
    void testConsume() {
        Character mockCharacter = new BasePlayer(null);
        mockCharacter.setHp(20);

        HealthPotion item = (HealthPotion) mockCharacter.getInventoryItem(0);
        item.consume(mockCharacter);

        assertEquals(25, mockCharacter.getHp());
        assertNull(mockCharacter.getInventoryItem(item));
    }

    @Test
    void testConsumeMaxHealth() {
        Character mockCharacter = new BasePlayer(null);

        HealthPotion item = (HealthPotion) mockCharacter.getInventoryItem(0);
        item.consume(mockCharacter);
        
        assertEquals(25, mockCharacter.getHp());
        assertNull(mockCharacter.getInventoryItem(item));
    }
}
