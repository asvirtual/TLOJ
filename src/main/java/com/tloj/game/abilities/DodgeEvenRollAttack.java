package com.tloj.game.abilities;

import com.tloj.game.entities.Boss;
import com.tloj.game.game.PlayerAttack;


/**
 * An ability that allows a boss to disable the attacker damage bonus if the attacker's weapon dice roll is an even number. <br>
 * It is paired with the {@link EvenBoss}. <br>
 */
public class DodgeEvenRollAttack extends BossAbility {
    public DodgeEvenRollAttack(Boss boss) {
        super(boss);
    }

    @Override
    public void use(PlayerAttack attack) {
        if (attack.getWeaponRoll() % 2 == 0) 
            attack.setBaseDamage(0);
    }
    
}
