package com.tloj.game.collectables.items;

import com.tloj.game.entities.Character;
import com.tloj.game.entities.characters.BasePlayer;
import com.tloj.game.game.Dice;

import org.junit.jupiter.api.Test;
import static org.junit.jupiter.api.Assertions.*;


public class GreatHealthPotionTest {

    @Test
    void testConsume() {
        Dice.setSeed(1);

        // Create BasePlayer, level up to 30 to have at least 55 max health
        Character mockCharacter = new BasePlayer(null);
        for (int i = 0; i < 30; i++) mockCharacter.levelUp();
        mockCharacter.setHp(5);

        GreatHealthPotion item = new GreatHealthPotion();
        mockCharacter.addInventoryItem(item);
        item.consume(mockCharacter);

        assertEquals(55, mockCharacter.getHp());
        assertNull(mockCharacter.getInventoryItem(item));
    }

    @Test
    void testConsumeMaxHealth() {
        Character mockCharacter = new BasePlayer(null);
        GreatHealthPotion item = new GreatHealthPotion();
        mockCharacter.addInventoryItem(item);
        item.consume(mockCharacter);
        
        assertEquals(mockCharacter.getMaxHp(), mockCharacter.getHp());
        assertNull(mockCharacter.getInventoryItem(item));
    }
}
