package com.tloj.game.abilities;

import com.tloj.game.entities.Boss;
import com.tloj.game.entities.bosses.EvenBoss;
import com.tloj.game.game.PlayerAttack;
import com.fasterxml.jackson.annotation.JsonCreator;

/**
 * An ability that allows a boss to disable the attacker damage bonus if the attacker's weapon dice roll is an even number. <br>
 * It is paired with the {@link EvenBoss}. <br>
 */
public class DodgeEvenRollAttack extends BossAbility {
    @JsonCreator
    public DodgeEvenRollAttack(Boss boss) {
        super(boss);
    }

    @Override
    public void use(PlayerAttack attack) {
        if (attack.getWeaponRoll() % 2 == 0) {
            System.out.println(this.boss + " used its mighty ability!\n"); // TODO: Maybe add ascii of the boss here with delay (no enter confirmation)
            attack.setWeaponRoll(0);
        }
    }
    
}
