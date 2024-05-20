package com.tloj.game.collectables.items;

import com.tloj.game.collectables.Item;
import com.tloj.game.collectables.weapons.LaserBlade;
import com.tloj.game.entities.Character;
import com.tloj.game.entities.characters.BasePlayer;

import org.junit.jupiter.api.Test;
import static org.junit.jupiter.api.Assertions.*;

import java.util.ArrayList;

public class GreatManaPotionTest {

    @Test
    void testConsume() {
        //max mana for BasePlayer is 15
        Character mockCharacter = new BasePlayer(
            20, 4, 4, 40, 0, 1, 5, 10, null, null, new LaserBlade(), new ArrayList<Item>(), null
        );
        mockCharacter.setMana(9);
        mockCharacter.addInventoryItem(new GreatManaPotion());
        GreatManaPotion item = (GreatManaPotion) mockCharacter.getInventoryItem(0);
        item.consume(mockCharacter);
        assertEquals(39, mockCharacter.getMana());
        assertNull(mockCharacter.searchInventoryItem(item));
    }

    @Test
    void testConsumeMaxMana() {
        Character mockCharacter = new BasePlayer(
            20, 4, 4, 40, 0, 1, 5, 10, null, null, new LaserBlade(), new ArrayList<Item>(), null
        );
        mockCharacter.setMana(40);
        mockCharacter.addInventoryItem(new GreatManaPotion());
        GreatManaPotion item = (GreatManaPotion) mockCharacter.getInventoryItem(0);
        item.consume(mockCharacter);
        assertEquals(40, mockCharacter.getMana());
        assertNull(mockCharacter.searchInventoryItem(item));
    }
    
}
