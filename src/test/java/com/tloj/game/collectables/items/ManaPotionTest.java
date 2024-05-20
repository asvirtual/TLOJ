package com.tloj.game.collectables.items;

import com.tloj.game.collectables.Item;
import com.tloj.game.collectables.weapons.LaserBlade;
import com.tloj.game.entities.Character;
import com.tloj.game.entities.characters.BasePlayer;

import org.junit.jupiter.api.Test;
import static org.junit.jupiter.api.Assertions.*;

import java.util.ArrayList;


public class ManaPotionTest {
    @Test
    void testConsume() {
        //max mana for BasePlayer is 15, set to 10 for testing
        Character mockCharacter = new BasePlayer(
            20, 4, 4, 15, 0, 1, 5, 10, null, null, new LaserBlade(), new ArrayList<Item>(), null
        );
        mockCharacter.setMana(10);
        mockCharacter.addInventoryItem(new ManaPotion());
        ManaPotion item = (ManaPotion) mockCharacter.getInventoryItem(0);
        item.consume(mockCharacter);
        assertEquals(15, mockCharacter.getMana());
        assertNull(mockCharacter.searchInventoryItem(item));
    }

    @Test
    void testConsumeMaxMana() {
        Character mockCharacter = new BasePlayer(
            20, 4, 4, 15, 0, 1, 5, 10, null, null, new LaserBlade(), new ArrayList<Item>(), null
        );
        mockCharacter.setMana(15);
        mockCharacter.addInventoryItem(new ManaPotion());
        ManaPotion item = (ManaPotion) mockCharacter.getInventoryItem(0);
        item.consume(mockCharacter);
        assertEquals(15, mockCharacter.getMana());
        assertNull(mockCharacter.searchInventoryItem(item));
    }
}
