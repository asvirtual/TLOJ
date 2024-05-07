package com.tloj.game.abilities;

import com.tloj.game.entities.Boss;
import com.tloj.game.game.PlayerAttack;


/**
 * An ability that allows a boss to dodge an attack if the attacker's weapon dice roll is greater than 4. <br>
 * It is paired with the {@link Boss}. <br> TODO: Link to the specific Boss class that has this ability<br>
 */
public class DodgeSlowAttackFlying extends BossAbility {
    public DodgeSlowAttackFlying(Boss boss) {
        super(boss);
    }

    @Override
    public void use(PlayerAttack attack) {
        if (attack.getWeaponRoll() < 4) return;
        attack.setTotalDamage(0);
    }
}
