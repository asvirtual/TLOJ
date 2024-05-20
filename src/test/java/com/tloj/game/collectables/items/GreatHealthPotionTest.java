package com.tloj.game.collectables.items;

import com.tloj.game.entities.Character;
import com.tloj.game.entities.characters.BasePlayer;
import com.tloj.game.collectables.Item;
import com.tloj.game.collectables.weapons.LaserBlade;

import org.junit.jupiter.api.Test;
import static org.junit.jupiter.api.Assertions.*;

import java.util.ArrayList;

public class GreatHealthPotionTest {

    @Test
    void testConsume() {
        //max hp for BasePlayer is 100, set to 20 for testing
        Character mockCharacter = new BasePlayer(
            100, 4, 4, 10, 0, 1, 5, 10, null, null, new LaserBlade(), new ArrayList<Item>(), null
        );
        mockCharacter.setHp(20);
        mockCharacter.addInventoryItem(new GreatHealthPotion());
        GreatHealthPotion item = (GreatHealthPotion) mockCharacter.getInventoryItem(0);
        item.consume(mockCharacter);
        assertEquals(70, mockCharacter.getHp());
        assertNull(mockCharacter.searchInventoryItem(item));
    }

    @Test
    void testConsumeMaxHealth() {
        Character mockCharacter = new BasePlayer(
            100, 4, 4, 10, 0, 1, 5, 10, null, null, new LaserBlade(), new ArrayList<Item>(), null
        );
        mockCharacter.setHp(100);
        mockCharacter.addInventoryItem(new GreatHealthPotion());
        GreatHealthPotion item = (GreatHealthPotion) mockCharacter.getInventoryItem(0);
        item.consume(mockCharacter);
        assertEquals(100, mockCharacter.getHp());
        assertNull(mockCharacter.searchInventoryItem(item));
    }
}
