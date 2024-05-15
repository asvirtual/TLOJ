package com.tloj.game.abilities;

import com.tloj.game.entities.Boss;
import com.tloj.game.entities.bosses.EvenBoss;
import com.tloj.game.game.PlayerAttack;
import com.tloj.game.utilities.ConsoleHandler;
import com.fasterxml.jackson.annotation.JsonCreator;

/**
 * An ability that allows a boss to disable the attacker damage bonus if the attacker's weapon dice roll is an even number. <br>
 * It is paired with the {@link EvenBoss}. <br>
 */
public class DodgeEvenRollAttack extends BossAbility {
    private final static String ACTIVATION_MESSAGE = ConsoleHandler.PURPLE + "The boss used its mighty ability and dodged the attack!" + ConsoleHandler.RESET;

    @JsonCreator
    public DodgeEvenRollAttack(Boss boss) {
        super(boss, ACTIVATION_MESSAGE);
    }

    @Override
    public boolean use(PlayerAttack attack) {
        if (attack.getWeaponRoll() % 2 == 1) return this.used = false;
        attack.setWeaponRoll(0);
        return this.used = true;
    }
    
}
