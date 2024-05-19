package com.tloj.game.entities.bosses;

import static org.junit.jupiter.api.Assertions.*;

import org.junit.jupiter.api.Test;

import com.tloj.game.entities.characters.BasePlayer;
import com.tloj.game.entities.Character;
import com.tloj.game.game.PlayerAttack;
import com.tloj.game.utilities.Coordinates;





public class HalverBossTest {
    @Test
    void testAbility() {
        HalverBoss halverBoss = new HalverBoss(new Coordinates(0, 0));
        Character mockCharacter = new BasePlayer(20, 4, 4, 10, 0, 1, 5, 10, null, null, null, null, null);
        PlayerAttack mockPlayerAttack = new PlayerAttack(mockCharacter, halverBoss);
        
        while (!halverBoss.getAbility().wasUsed()) {
            halverBoss.defend(mockPlayerAttack);

            if (halverBoss.getAbility().wasUsed()) assertTrue((mockPlayerAttack.getWeaponRoll()) / 2 == 0 );
        }
    }
}