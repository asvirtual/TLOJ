package com.tloj.game.abilities;

import com.tloj.game.utilities.ConsoleHandler;
import com.tloj.game.game.PlayerAttack;
import com.tloj.game.entities.Mob;
import com.tloj.game.entities.mobs.JetBat;

import com.fasterxml.jackson.annotation.JsonCreator;


/**
 * An ability that allows the mob to dodge an attack with a 25% probability. <br>
 * It is paired with the {@link JetBat}.
 */

public class JetBatAbility extends MobAbility {
    private final static String ACTIVATION_MESSAGE = ConsoleHandler.PURPLE + "JetBat was so quick it dodged the attack!" + ConsoleHandler.RESET;

    @JsonCreator
    public JetBatAbility(Mob user) {
        super(user, ACTIVATION_MESSAGE);
    }

    @Override
    public boolean use(PlayerAttack attack) {
        if (Math.random() > 0.25) return this.used = false;
        // Needed to tell the PlayerAttack to print the disabled dice roll
        attack.setWeaponRoll(0);
        attack.setTotalAttack(0);
        return this.used = true;
    }
}