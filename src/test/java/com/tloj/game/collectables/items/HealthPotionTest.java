package com.tloj.game.collectables.items;

import com.tloj.game.entities.Character;
import com.tloj.game.entities.characters.BasePlayer;

import org.junit.jupiter.api.Test;
import static org.junit.jupiter.api.Assertions.*;


public class HealthPotionTest {
    @Test
    void testConsume() {
        Character mockCharacter = new BasePlayer(
            20, 4, 4, 10, 0, 1, 5, 10, null, null, null, null, null
        );

        mockCharacter.addInventoryItem(new HealthPotion());
        HealthPotion item = (HealthPotion) mockCharacter.getInventoryItem(0);
        item.consume(mockCharacter);
        assertEquals(25, mockCharacter.getHp());
        assertEquals("", mockCharacter.getInventory());
    }

    @Test
    void testConsumeMaxHealth() {
        Character mockCharacter = new BasePlayer(
            25, 4, 4, 10, 0, 1, 5, 10, null, null, null, null, null
        );

        mockCharacter.addInventoryItem(new HealthPotion());
        HealthPotion item = (HealthPotion) mockCharacter.getInventoryItem(0);
        item.consume(mockCharacter);
        assertEquals(25, mockCharacter.getHp());
        assertEquals("", mockCharacter.getInventory());
    }
}
