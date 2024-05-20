package com.tloj.game.collectables.items;

import com.tloj.game.entities.Character;
import com.tloj.game.entities.characters.BasePlayer;
import com.tloj.game.utilities.Dice;

import org.junit.jupiter.api.Test;
import static org.junit.jupiter.api.Assertions.*;


public class GreatManaPotionTest {

    @Test
    void testConsume() {
        Dice.setSeed(1);

        // Create BasePlayer, level up to 15 to have at least 30 max mana
        Character mockCharacter = new BasePlayer(null);
        for (int i = 0; i < 15; i++) mockCharacter.levelUp();
        mockCharacter.setMana(0);

        GreatManaPotion item = new GreatManaPotion();
        mockCharacter.addInventoryItem(item);

        item.consume(mockCharacter);
        assertEquals(30, mockCharacter.getMana());
        assertNull(mockCharacter.getInventoryItem(item));
    }

    @Test
    void testConsumeMaxMana() {
        Character mockCharacter = new BasePlayer(null);

        GreatManaPotion item = new GreatManaPotion();
        mockCharacter.addInventoryItem(item);

        item.consume(mockCharacter);
        assertEquals(mockCharacter.getMaxMana(), mockCharacter.getMana());
        assertNull(mockCharacter.getInventoryItem(item));
    }
    
}
