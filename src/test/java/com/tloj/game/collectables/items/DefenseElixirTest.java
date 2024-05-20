package com.tloj.game.collectables.items;

import com.tloj.game.entities.Character;
import com.tloj.game.entities.characters.BasePlayer;

import org.junit.jupiter.api.Test;
import static org.junit.jupiter.api.Assertions.*;


public class DefenseElixirTest {
    @Test
    void testConsume() {
        Character mockCharacter = new BasePlayer(null);
        DefenseElixir item = new DefenseElixir();
        mockCharacter.addInventoryItem(item);
        item.consume(mockCharacter);
        assertEquals(7, mockCharacter.getCurrentFightDef());
        assertNull(mockCharacter.getInventoryItem(item));
    }
}
