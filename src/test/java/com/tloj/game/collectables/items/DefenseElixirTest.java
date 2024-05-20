package com.tloj.game.collectables.items;

import com.tloj.game.entities.Character;
import com.tloj.game.entities.characters.BasePlayer;
import com.tloj.game.entities.mobs.CyberGoblin;
import com.tloj.game.entities.Mob;

import com.tloj.game.game.PlayerAttack;
import com.tloj.game.collectables.Item;
import com.tloj.game.collectables.weapons.LaserBlade;

import org.junit.jupiter.api.Test;
import static org.junit.jupiter.api.Assertions.*;

import java.util.ArrayList;

public class DefenseElixirTest {
    @Test
    void testConsume() {
        Character mockCharacter = new BasePlayer(
            20, 4, 10, 10, 0, 1, 5, 5, null, null, new LaserBlade(), new ArrayList<Item>(), null
        );
        CyberGoblin mockMob = new CyberGoblin(null, 1);
        mockCharacter.addInventoryItem(new DefenseElixir());
        DefenseElixir item = (DefenseElixir) mockCharacter.getInventoryItem(0);
        PlayerAttack mockPlayerAttack = new PlayerAttack(mockCharacter, mockMob);
        item.consume(mockCharacter);
        assertEquals(14, mockCharacter.getCurrentFightDef());
        assertNull(mockCharacter.searchInventoryItem(item));
    }
}
