package com.tloj.game.abilities;

import com.tloj.game.entities.Boss;
import com.tloj.game.entities.bosses.FlyingBoss;
import com.tloj.game.game.PlayerAttack;
import com.tloj.game.utilities.ConsoleHandler;

import com.fasterxml.jackson.annotation.JsonCreator;
import com.fasterxml.jackson.annotation.JsonProperty;


/**
 * An ability that allows a boss to dodge player's attack and heal itself of player's weapon roll with a 33% probability. <br>
 * It is paired with the {@link FlyingBoss}.
 */
public class VampireDodge extends BossAbility {
    @JsonCreator
    public VampireDodge(@JsonProperty("user") Boss boss) {
        super(boss);
    }

    @Override
    public boolean use(PlayerAttack attack) {
        if (!this.user.evaluateProbability(0.333)) return this.used = false;

        this.user.heal(attack.getWeaponRoll());
        attack.setTotalAttack(0);

        this.activationMessage = ConsoleHandler.PURPLE + " the boss dodged the attack and healed for " + attack.getWeaponRoll() + " HP!" + ConsoleHandler.RESET;

        return this.used = true;
    }
}
