package com.tloj.game.collectables.items;

import com.tloj.game.entities.Character;
import com.tloj.game.entities.characters.BasePlayer;

import org.junit.jupiter.api.Test;
import static org.junit.jupiter.api.Assertions.*;

public class AttackElixirTest {
    @Test
    void testConsume() {
        Character mockCharacter = new BasePlayer(null);
        AttackElixir item = new AttackElixir();
        mockCharacter.addInventoryItem(item);
        item.consume(mockCharacter);
        assertEquals(7, mockCharacter.getCurrentFightAtk());
        assertNull(mockCharacter.getInventoryItem(item));
    }
}
