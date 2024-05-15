package com.tloj.game.abilities;

import com.tloj.game.entities.Boss;
import com.tloj.game.game.PlayerAttack;
import com.tloj.game.utilities.ConsoleHandler;
import com.tloj.game.entities.bosses.HalverBoss;
import com.fasterxml.jackson.annotation.JsonCreator;


/**
 * An ability that allows a boss to take half damage (base dmg + weapon dice roll). <br>
 * It is paired with the {@link HalverBoss}.<br>
 * @see Boss 
 */
public class TakeHalfDamage extends BossAbility {
    private final static String ACTIVATION_MESSAGE = ConsoleHandler.PURPLE + "The boss used its mighty ability and deflected half of the attack!" + ConsoleHandler.RESET;

    @JsonCreator
    public TakeHalfDamage(Boss boss){
        super(boss, ACTIVATION_MESSAGE);
    }

    @Override
    public boolean use(PlayerAttack attack) {
        // attack.setTotalAttack(attack.getTotalDamage() / 2);
        attack.setWeaponRoll(attack.getWeaponRoll() / 2);
        return this.used = true;
    }
}
