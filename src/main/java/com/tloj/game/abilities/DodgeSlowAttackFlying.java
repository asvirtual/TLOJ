package com.tloj.game.abilities;

import com.tloj.game.entities.Boss;
import com.tloj.game.entities.bosses.FlyingBoss;
import com.tloj.game.game.PlayerAttack;
import com.fasterxml.jackson.annotation.JsonCreator;
import com.fasterxml.jackson.annotation.JsonProperty;


/**
 * An ability that allows a boss to dodge an attack if the attacker's weapon dice roll is greater than 4. <br>
 * It is paired with the {@link FlyingBoss}.
 */
public class DodgeSlowAttackFlying extends BossAbility {
    @JsonCreator
    public DodgeSlowAttackFlying(Boss boss) {
        super(boss);
    }

    @Override
    public void use(PlayerAttack attack) {
        if (attack.getWeaponRoll() > 4) return;
        attack.setTotalDamage(0);
    }
}
