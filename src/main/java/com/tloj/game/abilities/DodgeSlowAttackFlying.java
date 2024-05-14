package com.tloj.game.abilities;

import com.tloj.game.entities.Boss;
import com.tloj.game.entities.bosses.FlyingBoss;
import com.tloj.game.game.PlayerAttack;
import com.tloj.game.utilities.ConsoleHandler;
import com.fasterxml.jackson.annotation.JsonCreator;


/**
 * An ability that allows a boss to dodge an attack if the attacker's weapon dice roll is greater than 4. <br>
 * It is paired with the {@link FlyingBoss}.
 */
public class DodgeSlowAttackFlying extends BossAbility {
    private final static String ACTIVATION_MESSAGE = ConsoleHandler.PURPLE + "The boss used its mighty ability and dodged the attack!" + ConsoleHandler.RESET;

    @JsonCreator
    public DodgeSlowAttackFlying(Boss boss) {
        super(boss, ACTIVATION_MESSAGE);
    }

    @Override
    public boolean use(PlayerAttack attack) {
        if (Math.random() > 0.333) return this.used = false;
        attack.setTotalAttack(0);
        return this.used = true;
    }
}
