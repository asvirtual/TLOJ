package com.tloj.game.abilities;

import com.tloj.game.entities.Boss;
import com.tloj.game.entities.bosses.FlyingBoss;
import com.tloj.game.game.PlayerAttack;
import com.tloj.game.utilities.ConsoleColors;
import com.fasterxml.jackson.annotation.JsonCreator;


/**
 * An ability that allows a boss to dodge an attack if the attacker's weapon dice roll is greater than 4. <br>
 * It is paired with the {@link FlyingBoss}.
 */
public class DodgeSlowAttackFlying extends BossAbility {
    private final static String ACTIVATION_MESSAGE = ConsoleColors.PURPLE + "The boss used its mighty ability and dodged the attack!" + ConsoleColors.RESET;

    @JsonCreator
    public DodgeSlowAttackFlying(Boss boss) {
        super(boss, ACTIVATION_MESSAGE);
    }

    @Override
    public boolean use(PlayerAttack attack) {
        if (attack.getWeaponRoll() > 4) return this.used = false;
        attack.setTotalDamage(0);
        return this.used = true;
    }
}
