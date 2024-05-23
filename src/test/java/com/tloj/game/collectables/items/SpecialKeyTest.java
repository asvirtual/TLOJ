package com.tloj.game.collectables.items;

import com.tloj.game.entities.Character;
import com.tloj.game.entities.characters.BasePlayer;

import org.junit.jupiter.api.Test;
import static org.junit.jupiter.api.Assertions.*;


public class SpecialKeyTest {
    
    void testConsume() {
        Character mockCharacter = new BasePlayer(null);
        SpecialKey item = new SpecialKey();
        mockCharacter.addInventoryItem(item);

    }
}
