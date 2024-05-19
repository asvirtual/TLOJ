package com.tloj.game.collectables.items;

import java.util.ArrayList;

import com.tloj.game.collectables.Item;
import com.tloj.game.collectables.weapons.LaserBlade;
import com.tloj.game.entities.Character;
import com.tloj.game.entities.characters.BasePlayer;

import org.junit.jupiter.api.Test;
import static org.junit.jupiter.api.Assertions.*;


public class HealthPotionTest {
    @Test
    void testConsume() {
        Character mockCharacter = new BasePlayer(25, 4, 4, 10, 0, 1, 5, 10, null, null, new LaserBlade(), new ArrayList<Item>(), null);
        mockCharacter.setHp(20);

        mockCharacter.addInventoryItem(new HealthPotion());
        HealthPotion item = (HealthPotion) mockCharacter.getInventoryItem(0);
        item.consume(mockCharacter);
        assertEquals(25, mockCharacter.getHp());
        assertNull(mockCharacter.searchInventoryItem(item));
    }

    @Test
    void testConsumeMaxHealth() {
        Character mockCharacter = new BasePlayer(25, 4, 4, 10, 0, 1, 5, 10, null, null, new LaserBlade(), new ArrayList<Item>(), null);

        mockCharacter.addInventoryItem(new HealthPotion());
        HealthPotion item = (HealthPotion) mockCharacter.getInventoryItem(0);
        item.consume(mockCharacter);
        assertEquals(25, mockCharacter.getHp());
        assertNull(mockCharacter.searchInventoryItem(item));
    }
}
